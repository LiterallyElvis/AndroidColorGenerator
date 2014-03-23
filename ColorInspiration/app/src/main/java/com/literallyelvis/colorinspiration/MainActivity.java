package com.literallyelvis.colorinspiration;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.literallyelvis.colorinspiration.ColorGen;

import java.nio.charset.Charset;
import java.text.DecimalFormat;

public class MainActivity extends Activity
{
    Color background = new Color();
    private int bgColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        newColor();

        final Button againButton = (Button) findViewById(R.id.againButton);     //    ~*~ set ~*~
        final Button copyHSVbutton = (Button) findViewById(R.id.copyHSVbutton); //  {♥} button {♥}
        final Button copyRGBbutton = (Button) findViewById(R.id.copyRGBbutton); // ^v^ variables ^v^

        againButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                newColor(); //simple function that generates and displays a new color.
            }});

        copyHSVbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Log.d("MainActivity.OnClick.copyHSVbutton", "copyHSVbutton pressed");
                float[] hsv = {0, 0, 0};               //initialize array for colorToHSV
                Color.colorToHSV(bgColor, hsv);        //retrieve HSV values from bgColor
                DecimalFormat sat = new DecimalFormat("@@@##%");  //  Formats value and sat values
                DecimalFormat val = new DecimalFormat("@@@##%");  //  for display as XXX.XX%.

                ClipData clip = ClipData.newPlainText("HSV Values",
                                                      "Hue = " + hsv[0] +
                                                      "\nSaturation = " + sat.format(hsv[1]) +
                                                      "\nValue = " + val.format(hsv[2]));

                ClipboardManager copy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                copy.setPrimaryClip(clip); //assigns newly copied data as super important.

                Toast toast = Toast.makeText(getApplicationContext(),
                        "HSV values copied to clipboard.", Toast.LENGTH_SHORT);
                toast.show();
            }});

        copyRGBbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Log.d("MainActivity.OnClick.copyRGBbutton", "copyRGBbutton pressed");
                ClipData clip = ClipData.newPlainText("RGB Values",
                                                      "Red = " + Color.red(bgColor) +
                                                    "\nGreen = " + Color.green(bgColor) +
                                                    "\nBlue = " + Color.blue(bgColor));

                ClipboardManager copy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                copy.setPrimaryClip(clip); //assigns newly copied data as super important.

                Toast toast = Toast.makeText(getApplicationContext(),
                                 "RGB values copied to clipboard.", Toast.LENGTH_SHORT);
                toast.show();
            }});
    }

    public void newColor()
    {
        //simple function that generates and displays a new color.
        Log.d("MainActivity.newColor", "newColor called.");

        ColorGen newColor = new ColorGen(); // calling ColorGen constructor
        int red = newColor.red,
            green = newColor.green,
            blue = newColor.blue;

        bgColor = background.rgb(red, green, blue); // this is entirely for HSV and RGB copy buttons

        ImageView colorBox = (ImageView) findViewById(R.id.colorBox);
        colorBox.setBackgroundColor(Color.rgb(red, green, blue));     //assigns color just generated
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
