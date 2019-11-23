package dev.foodie.cq.util;

import android.graphics.Color;

public class Colors {

    private static int[] colors = {
            Color.BLUE, Color.BLACK, Color.RED, Color.MAGENTA, Color.DKGRAY, Color.GREEN
    };

    public static int get() {
        return colors[(int) Math.floor(Math.random() * colors.length)];
    }

}
