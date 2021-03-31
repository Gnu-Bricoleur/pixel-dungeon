package net.migaud.extendedpixeldungeon.items;

import android.graphics.Color;
import android.support.annotation.ColorInt;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;

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

        return randomColorFromArray(eyeColors, eyeColorProbability);
    }

    public static int randomHairColor() {
        //Approximative worldwide stats
        int[] hairColors = { Color.argb(255, 149, 69, 27),//Brown
                Color.argb(255, 10, 145, 241),//Blond
                Color.argb(255, 52, 187, 37),//Redhead
                Color.argb(255, 133, 132, 59),//Black
                Color.argb(255, 255, 187, 0),//White
                Color.argb(255, 77, 77, 77)//Gray
        };

        float[] hairColorProbability = new float[]{68, 10, 3, 6, 6, 7};

        return randomColorFromArray(hairColors, hairColorProbability);
    }

    public static int randomColorFromArray(int[] colorArray, float[] colorProba) {
        // Compute the total weight of all items together.
        // This can be skipped of course if sum is already 1.
        double totalWeight = 0.0;
        for (Float i : colorProba) {
            totalWeight += i;
        }

        // Now choose a random item.
        int idx = 0;
        for (double r = Math.random() * totalWeight; idx < colorArray.length - 1; ++idx) {
            r -= colorProba[idx];
            if (r <= 0.0) break;
        }

        return colorArray[idx];
    }

    public static int hairColorShade(@ColorInt int currentColor, @ColorInt int desiredMainColor) {
        //from current color determine distance to base color for exemple Color.argb(255, 226, 155, 55)
        int dr = 226 - red(currentColor);
        int dg = 155 - green(currentColor);
        int db = 55 - blue(currentColor);
        //apply this distance to the desiredMainColor
        int shadedColor = Color.argb(255, red(desiredMainColor) + dr, green(desiredMainColor) + dg, blue(desiredMainColor) + db);
        //return the result
        return shadedColor;
    }

}
