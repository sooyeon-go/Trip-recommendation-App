package com.example.ssadola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

public class TitleStudioActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    //private Button mButton;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private boolean mShowingFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_studio);

        //mButton = (Button) findViewById(R.id.cardTypeBtn);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        CardFragmentPagerAdapter pagerAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(), dpToPixels(2, this));

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem("R.string.title_1", "R.string.text_1"));
        mCardAdapter.addCardItem(new CardItem("R.string.title_2", "R.string.text_2"));
        mCardAdapter.addCardItem(new CardItem("R.string.title_3"," R.string.text_3"));
        mCardAdapter.addCardItem(new CardItem("R.string.title_4", "R.string.text_4"));
        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));
        ShadowTransformer fragmentCardShadowTransformer = new ShadowTransformer(mViewPager, pagerAdapter);
        fragmentCardShadowTransformer.enableScaling(true);

        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setPageTransformer(false, fragmentCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    /**
     * Change value in dp to pixels
     * @param dp
     * @param context
     * @return
     */
    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

}
