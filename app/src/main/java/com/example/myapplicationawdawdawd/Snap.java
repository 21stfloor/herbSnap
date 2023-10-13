package com.example.myapplicationawdawdawd;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.AspectRatio;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationawdawdawd.tensorflow.ImageClassifierHelper;
import com.example.myapplicationawdawdawd.tensorflow.fragments.ClassificationResultAdapter;
import com.google.common.util.concurrent.ListenableFuture;

import org.tensorflow.lite.task.vision.classifier.Classifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Snap extends AppCompatActivity
    implements ImageClassifierHelper.ClassifierListener {
    private static final String TAG = "Image Classifier";

    private ImageClassifierHelper imageClassifierHelper;
    private Bitmap bitmapBuffer;
    private ClassificationResultAdapter classificationResultsAdapter;
    private ImageAnalysis imageAnalyzer;
    private ProcessCameraProvider cameraProvider;
    private final Object task = new Object();

    /**
     * Blocking camera operations are performed using this executor
     */
    private ExecutorService cameraExecutor;
    private RecyclerView recyclerviewResults;
    private PreviewView viewFinder;
    private NestedScrollView bottomSheetLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_camera);
        recyclerviewResults = findViewById(R.id.recyclerview_results);
        viewFinder = findViewById(R.id.view_finder);
        bottomSheetLayout = findViewById(R.id.bottom_sheet_layout);
    }



    public static boolean hasPermission(Context context) {
        return ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher
            = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(getApplicationContext(),
                                    "Permission request granted",
                                    Toast.LENGTH_LONG)
                            .show();
                    startCamera();
                } else {
                    Toast.makeText(getApplicationContext(),
                                    "Permission request denied",
                                    Toast.LENGTH_LONG)
                            .show();
                }
            });

    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void startCamera() {
        if (!hasPermission(getApplicationContext())) {
            finish();
//            Navigation.findNavController(this, R.id.fragmentContainerView)
//                    .navigate(
//                            CameraFragmentDirections.actionCameraToPermissions()
//                    );
        }

        cameraExecutor = Executors.newSingleThreadExecutor();
        imageClassifierHelper = ImageClassifierHelper.create(getApplicationContext(), this);

        // setup result adapter
        classificationResultsAdapter = new ClassificationResultAdapter();
        classificationResultsAdapter
                .updateAdapterSize(imageClassifierHelper.getMaxResults());
        recyclerviewResults
                .setAdapter(classificationResultsAdapter);
        recyclerviewResults
                .setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // Set up the camera and its use cases
        viewFinder.post(this::setUpCamera);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
        synchronized (task) {
            imageClassifierHelper.clearImageClassifier();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        imageAnalyzer.setTargetRotation(
                viewFinder.getDisplay().getRotation()
        );
    }


    // Initialize CameraX, and prepare to bind the camera use cases
    private void setUpCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();

                // Build and bind the camera use cases
                bindCameraUseCases();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // Declare and bind preview, capture and analysis use cases
    private void bindCameraUseCases() {
        // CameraSelector - makes assumption that we're only using the back
        // camera
        CameraSelector.Builder cameraSelectorBuilder = new CameraSelector.Builder();
        CameraSelector cameraSelector = cameraSelectorBuilder
                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

        // Preview. Only using the 4:3 ratio because this is the closest to
        // our model
        Preview preview = new Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(
                        viewFinder
                                .getDisplay().getRotation()
                )
                .build();

        // ImageAnalysis. Using RGBA 8888 to match how our models work
        imageAnalyzer = new ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(viewFinder.getDisplay().getRotation())
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                .build();

        // The analyzer can then be assigned to the instance
        imageAnalyzer.setAnalyzer(cameraExecutor, image -> {
            if (bitmapBuffer == null) {
                bitmapBuffer = Bitmap.createBitmap(
                        image.getWidth(),
                        image.getHeight(),
                        Bitmap.Config.ARGB_8888);
            }
            classifyImage(image);
        });

        // Must unbind the use-cases before rebinding them
        cameraProvider.unbindAll();

        try {
            // A variable number of use-cases can be passed here -
            // camera provides access to CameraControl & CameraInfo
            cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageAnalyzer
            );

            // Attach the viewfinder's surface provider to preview use case
            preview.setSurfaceProvider(
                    viewFinder.getSurfaceProvider()
            );
        } catch (Exception exc) {
            Log.e(TAG, "Use case binding failed", exc);
        }
    }

    private void classifyImage(@NonNull ImageProxy image) {
        // Copy out RGB bits to the shared bitmap buffer
        bitmapBuffer.copyPixelsFromBuffer(image.getPlanes()[0].getBuffer());

        int imageRotation = image.getImageInfo().getRotationDegrees();
        image.close();
        synchronized (task) {
            // Pass Bitmap and rotation to the image classifier helper for
            // processing and classification
            imageClassifierHelper.classify(bitmapBuffer, imageRotation);
        }
    }

    @Override
    public void onError(String error) {
        runOnUiThread(() -> {
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
            classificationResultsAdapter.updateResults(new ArrayList<>());
        });
    }

    @Override
    public void onResults(List<Classifications> results, long inferenceTime) {
        runOnUiThread(() -> {
            classificationResultsAdapter.updateResults(results.get(0).getCategories());

        });
    }
}