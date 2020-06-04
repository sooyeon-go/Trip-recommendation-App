package com.example.ssadola;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

public class ThemeFragment  extends Fragment implements ScreenShotable {
    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;
    public static ThemeFragment newInstance(int resId) {
        ThemeFragment themefragment = new ThemeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        themefragment.setArguments(bundle);
        return themefragment;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        res = getArguments().getInt(Integer.class.getName());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_theme, container, false);

        Button studio = rootView.findViewById(R.id.btn_studoi_set);
        studio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studio_activity = new Intent(getActivity(), StudioActivity.class);
                startActivity(studio_activity);
            }
            }

            );
        Button season = rootView.findViewById(R.id.btn_season);
        season.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent season_activity = new Intent(getActivity(), seasonactivity.class);
                startActivity(season_activity);
            }
        }

        );




        return rootView;
    }












    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }
}
