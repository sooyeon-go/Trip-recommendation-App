package com.example.ssadola;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Konstantin on 22.12.2014.
 */
public class ContentFragment extends Fragment implements ScreenShotable {
    public static final String CLOSE = "Close";
    public static final String CUSTOM = "Custom";
    public static final String THEME = "Theme";
    public static final String BOOKMARK = "Bookmark";
    public static final String LOGIN = "Login";
    public static final String PROFILE = "Profile";
    public static final String REVIEW_INPUT = "ReviewInput";
    public static final String REVIEW_RESULT ="ReviewResult";
    private View containerView;
    protected ImageView mImageView;
    protected int res;
    private Bitmap bitmap;

    public static ContentFragment newInstance(int resId) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Integer.class.getName(), resId);
        contentFragment.setArguments(bundle);
        return contentFragment;
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
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        /*mImageView = (ImageView) rootView.findViewById(R.id.image_content);
        mImageView.setClickable(true);
        mImageView.setFocusable(true);
        mImageView.setImageResource(res);*/
        final int primaryColorCanvas = R.color.colorPrimary;
        Button main_reco = (Button) rootView.findViewById(R.id.mainbutton1_reco);
        main_reco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"btn CLIKED!",Toast.LENGTH_SHORT).show();
                //getSupportFragmentManager().findFragmentById(R.id.fragment_custom);
                CustomFragment custom = CustomFragment.newInstance(R.id.fragment_custom);
                replaceFragment(custom);
            }

        });

        Button main_theme = (Button) rootView.findViewById(R.id.mainbutton2_theme);
        main_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"btn CLIKED!",Toast.LENGTH_SHORT).show();
                //getSupportFragmentManager().findFragmentById(R.id.fragment_custom);
                ThemeFragment theme = ThemeFragment.newInstance(R.id.fragment_theme);
                replaceFragment(theme);
            }

        });

        Button main_bookmark = (Button) rootView.findViewById(R.id.mainbutton3_bookmark);
        main_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(),"btn CLIKED!",Toast.LENGTH_SHORT).show();
                //getSupportFragmentManager().findFragmentById(R.id.fragment_custom);
                BookmarkFragment bookmark = BookmarkFragment.newInstance(R.id.fragment_bookmark);
                replaceFragment(bookmark);
            }

        });
        Button bookmark = rootView.findViewById(R.id.mainbutton3_bookmark);
        bookmark.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent bookmark = new Intent(getActivity(), BookmarkActivity.class);
                                                startActivity(bookmark);
                                            }
                                        }

        );


        Button review_input = rootView.findViewById(R.id.mainbutton4_review);
        review_input.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Intent review_input_act = new Intent(getActivity(), ReviewInput.class);
                                          startActivity(review_input_act);
                                      }
                                  }

        );

        Button review_result = rootView.findViewById(R.id.mainbutton5_judge);
        review_result.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent review_result_act = new Intent(getActivity(), ReviewResult.class);
                                                startActivity(review_result_act);
                                            }
                                        }

        );

        Button profile = rootView.findViewById(R.id.mainbutton6_profile);
        profile.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {
                                                 Intent profile = new Intent(getActivity(), ProfileActivity.class);
                                                 startActivity(profile);
                                             }
                                         }

        );




        return rootView;
    }

    @Override
    public void takeScreenShot() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(),
                        containerView.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                containerView.draw(canvas);
                ContentFragment.this.bitmap = bitmap;
            }
        };

        thread.start();

    }

    @Override
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.container_frame,fragment);
        ft.commit();
    }
}