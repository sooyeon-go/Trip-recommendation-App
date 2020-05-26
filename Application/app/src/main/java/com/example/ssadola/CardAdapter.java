package com.example.ssadola;

import androidx.cardview.widget.CardView;

public interface CardAdapter {
    final int MAX_ELEVATION_FACTOR =8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
