/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ssadola;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.ssadola.R;
import com.example.ssadola.ImageFetcher;
import com.example.ssadola.ImageWorker;

/**
 * This fragment will populate the children of the ViewPager from {@link ImageStudioActivity}.
 */
public class ImageStudioFragment extends Fragment implements ImageWorker.OnImageLoadedListener {
    private static final String IMAGE_DATA_EXTRA = "extra_image_data";
    private String mImageUrl;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private TextView mScene;
    /**
     * Factory method to generate a new instance of the fragment given an image number.
     *
     * @param imageUrl The image url to load
     * @return A new instance of ImageDetailFragment with imageNum extras
     */
    static ImageStudioFragment newInstance(String imageUrl) {
        final ImageStudioFragment f = new ImageStudioFragment();

        final Bundle args = new Bundle();
        args.putString(IMAGE_DATA_EXTRA, imageUrl);
        f.setArguments(args);

        return f;
    }

    /**
     * Empty constructor as per the Fragment documentation
     */
    public ImageStudioFragment() {
    }

    /**
     * Populate image using a url from extras, use the convenience factory method
     * {@link ImageStudioFragment#newInstance(String)} to create this fragment.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString(IMAGE_DATA_EXTRA) : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate and locate the main ImageView
        final View v = inflater.inflate(R.layout.fragment_image_studio, container, false);
        mImageView = v.findViewById(R.id.imageView);
        mProgressBar = v.findViewById(R.id.progressbar);
        mScene = v.findViewById(R.id.tv_scene);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();

        // Use the parent activity to load the image asynchronously into the ImageView (so a single
        // cache can be used over all pages in the ViewPager
        if (activity instanceof ImageStudioActivity) {
            ImageFetcher mImageFetcher = ((ImageStudioActivity) getActivity()).getImageFetcher();
            mImageFetcher.loadImage(mImageUrl, mImageView, this);
        }

        // Pass clicks on the ImageView to the parent activity to handle
        if (activity instanceof View.OnClickListener) {
            mImageView.setOnClickListener((View.OnClickListener) getActivity());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mImageView != null) {
            // Cancel any pending image work
            ImageWorker.cancelWork(mImageView);
            mImageView.setImageDrawable(null);
        }
    }

    @Override
    public void onImageLoaded(boolean success) {
        // Set loading spinner to gone once image has loaded. Cloud also show
        // an error view here if needed.
        mProgressBar.setVisibility(View.GONE);
    }
}
