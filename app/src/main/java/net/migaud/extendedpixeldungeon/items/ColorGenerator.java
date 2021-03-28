package net.migaud.extendedpixeldungeon.items;

import android.graphics.Color;

import net.migaud.extendedpixeldungeon.utils.Random;

public class ColorGenerator {

    public static int randomEyeColor() {
        //Approximative worldwide stats
        int[] eyeColors = { Color.argb(255, 149, 69, 27),//Brown
                Color.argb(255, 10, 145, 241),//Blue
                Color.argb(255, 52, 187, 37),//Green
                Color.argb(255, 133, 132, 59),//Hazel
                Color.argb(255, 255, 187, 0),//Amber
                Color.argb(255, 77, 77, 77)//Gray
        };

        float[] eyeColorProbability = new float[]{68, 10, 3, 6, 6, 7};

        // Compute the total weight of all items together.
        // This can be skipped of course if sum is already 1.
        double totalWeight = 0.0;
        for (Float i : eyeColorProbability) {
            totalWeight += i;
        }

        // Now choose a random item.
        int idx = 0;
        for (double r = Math.random() * totalWeight; idx < eyeColors.length - 1; ++idx) {
            r -= eyeColorProbability[idx];
            if (r <= 0.0) break;
        }

        return eyeColors[idx];
    }
}
