package com.literallyelvis.colorinspiration;

import java.util.Random;
import android.util.Log;

/**
 * Created by Jeffrey on 3/15/14.
 */
public class ColorGen
{
    final int MAX = 256;
    Random random = new Random();

    public int red,
               green,
               blue;

    ColorGen()
    {
        Log.d("ColorGen", "Constructor called.");
        red = random.nextInt(MAX);
        green = random.nextInt(MAX);
        blue = random.nextInt(MAX);

        Log.d("ColorGen constructor", "R value: " + red);
        Log.d("ColorGen constructor", "G value: " + green);
        Log.d("ColorGen constructor", "B value: " + blue);
    }
}
