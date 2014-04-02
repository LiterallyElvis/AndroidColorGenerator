package com.literallyelvis.randomcolorgenerator;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends Activity
{
    Color background = new Color();
    int bgColor = 0,
            red = 0,
            green = 0,
            blue = 0,
            current = -1;

    String dialogList[] = {"RGB", "Hexadecimal", "HSV"};

    int colorHistory[] = {0, 0, 0, 0, 0,
                          0, 0, 0, 0, 0,
                          0, 0, 0, 0, 0,
                          0, 0, 0, 0, 0};

    final int COLOR_MAX = 100;
    final int HISTORY_MAX = 19;
    final int MAX = 256;

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        labelsInvisible();
        newColor();

        ImageView colorDisplay = (ImageView) findViewById(R.id.colorBox);

        colorDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                newColor(); //simple function that generates and displays a new color.
            }});
    }

    private void newColor()
    {
        //simple function that generates and displays a new color.
        Log.d("MainActivity.newColor", "newColor called.");

        red = random.nextInt(MAX);
        green = random.nextInt(MAX);
        blue = random.nextInt(MAX);

        bgColor = background.rgb(red, green, blue);

        ImageView colorDisplay = (ImageView) findViewById(R.id.colorBox);
        colorDisplay.setBackgroundColor(Color.rgb(red, green, blue));     //assigns color just generated
        labelUpdate(bgColor);
        appendHistory(bgColor);
    }

    private void appendHistory(int bgColor)
    {

        if( current < HISTORY_MAX )
        {
            current++;
            Log.d("History Transfer", "current = " + current);
            colorHistory[current] = bgColor;
        }
        else
        {
            current = HISTORY_MAX;
            for( int i = 0; i < HISTORY_MAX; i++)
            {
                Log.d("History Transfer", "colorHistory[" + i + "] = " + colorHistory[i]);
                colorHistory[i] = colorHistory[i+1];
            }
            colorHistory[current] = bgColor;
        }
    }

    private void labelsVisible()
    {

        TextView RGBinfo = (TextView) findViewById(R.id.RGBinfo);
        TextView HexInfo = (TextView) findViewById(R.id.HexInfo);
        TextView HSVinfo = (TextView) findViewById(R.id.HSVinfo);

        if( (red <= COLOR_MAX && green <= COLOR_MAX) ||
                (red <= COLOR_MAX && blue <= COLOR_MAX)  ||
                (blue <= COLOR_MAX && green <= COLOR_MAX) )
        {
            RGBinfo.setTextColor(Color.WHITE);
            HexInfo.setTextColor(Color.WHITE);
            HSVinfo.setTextColor(Color.WHITE);
        }
        else
        {
            RGBinfo.setTextColor(Color.BLACK);
            HexInfo.setTextColor(Color.BLACK);
            HSVinfo.setTextColor(Color.BLACK);
        }

        RGBinfo.setVisibility(View.VISIBLE);
        HexInfo.setVisibility(View.VISIBLE);
        HSVinfo.setVisibility(View.VISIBLE);
    }

    private void labelsInvisible()
    {
        TextView RGBinfo = (TextView) findViewById(R.id.RGBinfo);
        TextView HexInfo = (TextView) findViewById(R.id.HexInfo);
        TextView HSVinfo = (TextView) findViewById(R.id.HSVinfo);

        RGBinfo.setVisibility(View.INVISIBLE);
        HexInfo.setVisibility(View.INVISIBLE);
        HSVinfo.setVisibility(View.INVISIBLE);
    }

    private void labelUpdate(int bgColor)
    {
        TextView RGBinfo = (TextView) findViewById(R.id.RGBinfo);
        TextView HexInfo = (TextView) findViewById(R.id.HexInfo);
        TextView HSVinfo = (TextView) findViewById(R.id.HSVinfo);

        if( (red <= COLOR_MAX && green <= COLOR_MAX) ||
                (red <= COLOR_MAX && blue <= COLOR_MAX)  ||
                (blue <= COLOR_MAX && green <= COLOR_MAX) )
        {
            RGBinfo.setTextColor(Color.WHITE);
            HexInfo.setTextColor(Color.WHITE);
            HSVinfo.setTextColor(Color.WHITE);
        }
        else
        {
            RGBinfo.setTextColor(Color.BLACK);
            HexInfo.setTextColor(Color.BLACK);
            HSVinfo.setTextColor(Color.BLACK);
        }
        RGBinfo.setText("Red = " + Color.red(bgColor) +
                "\nGreen = " + Color.green(bgColor) +
                "\nBlue = " + Color.blue(bgColor));

        String hexColor = String.format("0x%08X", (0xFFFFFFFF & bgColor));
        HexInfo.setText(hexColor);

        float[] hsv = {0, 0, 0};               //initialize array for colorToHSV
        Color.colorToHSV(bgColor, hsv);        //retrieve HSV values from bgColor
        DecimalFormat hue = new DecimalFormat("###");  //  Formats value and sat values
        DecimalFormat sat = new DecimalFormat("@@##%");  //  Formats value and sat values
        DecimalFormat val = new DecimalFormat("@@##%");  //  for display as XXX.XX%.
        HSVinfo.setText("Hue = " + hue.format(hsv[0]) +
                "\nSat = " + sat.format(hsv[1]) +
                "\nValue = " + val.format(hsv[2]));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void copyHex()
    {
        String hexColor = String.format("0x%08X", (0xFFFFFFFF & bgColor));
        ClipData clip = ClipData.newPlainText("RGB Values", hexColor);
        ClipboardManager copy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        copy.setPrimaryClip(clip); //assigns newly copied data as super important.

        Toast toast = Toast.makeText(getApplicationContext(),
                "Hexadecimal value copied to clipboard.", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void copyRGB()
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

    }

    private void copyHSV()
    {
        float[] hsv = {0, 0, 0};               //initialize array for colorToHSV
        Color.colorToHSV(bgColor, hsv);        //retrieve HSV values from bgColor
        DecimalFormat sat = new DecimalFormat("@@@##%");  //  Formats value and sat values
        DecimalFormat val = new DecimalFormat("@@@##%");  //  for display as XXX.XX%.

        ClipData clip = ClipData.newPlainText("HSV Values",
                "Hue = " + hsv[0] +
                        "\nSaturation = " + sat.format(hsv[1]) +
                        "\nValue = " + val.format(hsv[2])
        );

        ClipboardManager copy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        copy.setPrimaryClip(clip); //assigns newly copied data as super important.

        Toast toast = Toast.makeText(getApplicationContext(),
                "HSV values copied to clipboard.", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch( id )
        {
            case R.id.Copy:
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.Copy).setItems(dialogList, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // The 'which' argument contains the index position
                        // of the selected item
                        switch( which )
                        {
                            case 0:
                                copyRGB();
                                break;
                            case 1:
                                copyHex();
                                break;
                            case 2:
                                copyHSV();
                                break;
                            default:
                                Log.wtf("DialogBox click", "a nonexistent button was pressed.");
                                break;
                        }
                    }
                });
                builder.show();
                break;
            }
            case R.id.showValues:
            {
                if( item.isChecked() )
                {
                    item.setChecked(false);
                    labelsInvisible();
                }
                else
                {
                    item.setChecked(true);
                    labelsVisible();
                }
                break;
            }
            default:
                Log.wtf("Menu Switch", "somehow a button that doesn't exist was selected?");
                break;
        }
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