package edu.ucsb.cs.cs184.omercohen.omercohendrawingmultitouch;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.SeekBar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // byte[] byteArray = savedInstanceState.getByteArray("bmp");
       // Bitmap drawing = BitmapFactory.decodeByteArray(byteArray)


        if (savedInstanceState != null) {
            CanvasView reload_canv = (CanvasView) findViewById(R.id.canvasView);
            reload_canv.painting = savedInstanceState.getParcelable("myBitMap");

            //reload_canv.paintingCanvas.drawBitmap(reload_canv.painting, 0, 0, null);

           int width = reload_canv.painting.getWidth();
           int height = reload_canv.painting.getHeight();

            // createa matrix for the manipulation
            Matrix matrix = new Matrix();
            // resize the bit map
           // matrix.postScale(width, height);
            // rotate the Bitmap
            //matrix.postRotate(45);
            //reload_canv.surfaceExists = true;
            Log.d("painting", "the bundle is NOT empty");

            //Paint paint = new Paint();
            //paint.setColor(Color.WHITE);
            //reload_canv.paintingCanvas = new Canvas(reload_canv.painting);
            //reload_canv.paintingCanvas.drawBitmap(reload_canv.painting, matrix,  paint);
            //reload_canv.paintingCanvas.drawColor(Color.WHITE);
            //reload_canv.refreshView();
        }
        else {

            Log.d("omer", "the bundle is empty");
        }

//        #1abc9c, #2ecc71, #3498db, #9b59b6, #34495e
//        CanvasView canvas = (CanvasView)findViewById();

        Button clearButton = (Button) findViewById(R.id.clearButton);
        SeekBar radiusSeek =(SeekBar)findViewById(R.id.radiusSeekBar);
        // perform seek bar change listener event used for getting the progress value
        radiusSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CanvasView canv = (CanvasView) findViewById(R.id.canvasView);
                canv.radius =  progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                //stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                //stub
            }
        });

        CanvasView canv = (CanvasView) findViewById(R.id.canvasView);

        //This block is courtesy of: https://stackoverflow.com/questions/9114587/how-can-i-save-colors-in-array-xml-and-get-its-back-to-color-array
        //*******************
        TypedArray ta = this.getResources().obtainTypedArray(R.array.colors);
        canv.colors = new int[ta.length()];
        for (int j = 0; j < ta.length(); j++) {
            canv.colors[j] = ta.getColor(j, 0);
        }
        ta.recycle();
        //*******************

        canv.color_map.put(0, canv.colors[0]);

    }

    public void clearScreen(View view) {
        CanvasView canv = (CanvasView) findViewById(R.id.canvasView);
       // Canvas painting = new Canvas();
        canv.paintingCanvas.drawColor(Color.WHITE);
        //canv.painting = null;
        Log.d("clearing", "currently clearing the screen in this method");
        canv.refreshView();
        return;
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("omer", "in on saveinstancestate method");
        //outState.putCharSequence(KEY_TEXT_VALUE, mTextView.getText());
        //outState.p
        CanvasView canv = (CanvasView) findViewById(R.id.canvasView);
        outState.putParcelable("myBitMap", canv.painting);
       // Bitmap painting = canv.painting;

        return;
    }




}
