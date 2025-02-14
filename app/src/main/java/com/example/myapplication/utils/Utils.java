package com.example.myapplication.utils;

import android.view.View;

public class Utils {
    public static int visibleOrGone(boolean isVisible) {
        return isVisible ? View.VISIBLE : View.GONE;
    }

    public static String convertDistance(float distance) {
        if (distance > 1000) return String.format("%.1f", distance / 1000) + " км";
        else return String.format("%.1f", distance) + " м";
    }
}
