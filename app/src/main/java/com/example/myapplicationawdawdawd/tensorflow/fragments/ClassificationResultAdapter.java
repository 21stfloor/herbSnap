/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplicationawdawdawd.tensorflow.fragments;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationawdawdawd.databinding.ItemClassificationResultBinding;

import org.tensorflow.lite.support.label.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/** Adapter for displaying the list of classifications for the image */
public class ClassificationResultAdapter
        extends RecyclerView.Adapter<ClassificationResultAdapter.ViewHolder> {
    private static final String NO_VALUE = "--";
    private List<Category> categories = new ArrayList<>();
    private int adapterSize = 0;


    @SuppressLint("NotifyDataSetChanged")
    public void updateResults(List<Category> categories) {
        List<Category> sortedCategories = new ArrayList<>(categories);
        Collections.sort(sortedCategories, new Comparator<Category>() {
            @Override
            public int compare(Category category1, Category category2) {
                return category1.getIndex() - category2.getIndex();
            }
        });
        this.categories = new ArrayList<>(Collections.nCopies(adapterSize, null));
        int min = Math.min(sortedCategories.size(), adapterSize);
        for (int i = 0; i < min; i++) {
            this.categories.set(i, sortedCategories.get(i));
        }
        notifyDataSetChanged();
    }

    public void updateAdapterSize(int size) {
        adapterSize = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemClassificationResultBinding binding = ItemClassificationResultBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    /** Data structure for items in list */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private String labels[] = {
                "Apot",
                "Lagundi",
                "Sampalok",
                "Tanglad",
                "Ampalaya",
                "Dalairo",
                "Dita",
                "Jackfruit",
                "Mahogany",
                "Tipolo",
        };
        private final TextView tvLabel;
        private final TextView tvScore;

        public ViewHolder(@NonNull ItemClassificationResultBinding binding) {
            super(binding.getRoot());
            tvLabel = binding.tvLabel;
            tvScore = binding.tvScore;
        }

        public void bind(Category category) {
            if (category != null) {
                if(category.getScore() <= 0.6){
                    tvLabel.setText("Others...");
                    return;
                }
                int index = Integer.parseInt(category.getLabel());
                tvLabel.setText(labels[index]);
                Log.d(TAG, category.getDisplayName());
                Log.d(TAG, category.getLabel());
                Log.d(TAG, "index:"+category.getIndex());
                Log.d(TAG, "score:"+category.getScore());
//                tvLabel.setText(category.getLabel());
                tvScore.setText(String.format(Locale.US, "%.2f", category.getScore()));
            }
            else {
                tvLabel.setText(NO_VALUE);
                tvScore.setText(NO_VALUE);
            }
        }
    }
}
