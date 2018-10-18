package edu.ucsb.cs.cs184.omercohen.omercohendrawingmultitouch;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Donghao Ren on 10/18/2017.
 */

public class CanvasView extends SurfaceView implements SurfaceHolder.Callback {
    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Add the class itself as the SurfaceView's SurfaceHolder's callback
        // This will allow the surfaceCreated, Changed, and Destroyed methods to be called
        getHolder().addCallback(this);
    }

    // The bitmap to store our painting
    Bitmap painting;
    Bitmap prePaint;
    // The canvas to draw to our painting
    Canvas paintingCanvas;

    int[] colors;


    Map<Integer,Integer> color_map = new HashMap<Integer,Integer>();

    // Keep track if the surface exists or not
    boolean surfaceExists = false;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        surfaceExists = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Create our bitmap
        //if (painting == null)
        paintingCanvas = new Canvas();

        if(painting != null) {
            //painting = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            //Canvas temp_canv = new Canvas();
            //temp_canv.setBitmap(painting);
            //painting.setWidth(painting.getWidth()*2);
            Matrix matrix = new Matrix();
            matrix.postRotate(0);
            Bitmap omer = Bitmap.createBitmap(painting, 0, 0, painting.getWidth(), painting.getHeight(), matrix, true);

           



            paintingCanvas.setBitmap(painting);
            //paintingCanvas.rotate(90);
        }
        else {
            Log.d("pre", "pre seg fault");
            painting = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Log.d("post", "post seg");
            //Canvas can = getHolder().lockCanvas();
            //Canvas can = new Canvas();
            //can.drawColor(Color.WHITE);
            //can.drawBitmap(painting, 0, 0, null);
            // getHolder().unlockCanvasAndPost(can);

            //THIS IS THE ORIGINAL BOILER CODE:
            // Let painting canvas draw to the bitma
            paintingCanvas = new Canvas(painting);
            // Fill the canvas with white
            paintingCanvas.drawColor(Color.WHITE);
        }
            // Draw a circle on the bitmap
            //Paint paint = new Paint();
            //paint.setColor(Color.BLACK);
            //paint.setStyle(Paint.Style.FILL_AND_STROKE);
            //paintingCanvas.drawCircle(100, 100, 50, paint);

        refreshView();
    }

    public void refreshView() {
        if(!surfaceExists) return;



        // Draw the painting bitmap to the SurfaceView
        Canvas surfaceCanvas = getHolder().lockCanvas();
        // surfaceCanvas is only valid between lockCanvas and unlockCanvasAndPost
        //if(painting == null) {
        surfaceCanvas.drawBitmap(painting, 0, 0, null);



        getHolder().unlockCanvasAndPost(surfaceCanvas);
        // unlockCanvasAndPost will show the SurfaceView. invalidate() is not necessary here.
        //invalidate();
    }



    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        surfaceExists = false;
    }

    int radius = 0;
    int i = 0;
    int r,g,b;



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int r = (int)(Math.random() * 255);
        int g = (int)(Math.random() * 255);
        int b = (int)(Math.random() * 255);
//        paint.setARGB(255, r, g, b);

        int num_points = event.getPointerCount();

        Paint paint = new Paint();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            getColorIndex(colors);
               // paint.setColor(colors[i]);
                //paint.setStyle(Paint.Style.FILL_AND_STROKE);

                //color_map.put(event.getPointerId(0), colors[i]);
                //paintingCanvas.drawCircle(event.getX(), event.getY(), radius + 15, paint);
            color_map.remove(0);
            color_map.put(0, colors[i]);
            Log.d("actionDown", "currently in the action down method");
                //refreshView();
//            if (event.getPointerCount()==1) {
//                //getColorIndex(colors);
//                paint.setColor(colors[i]);
//                paint.setStyle(Paint.Style.FILL_AND_STROKE);
//                paintingCanvas.drawCircle(event.getX(), event.getY(), radius + 15, paint);
//                refreshView();
//            }
//            else {
//                for (int k = 0; k < event.getPointerCount(); k++) {
//                    color_map.put(event.getPointerId(k), colors[k]);
//                    paint.setColor(colors[k]);
//                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
//                    paintingCanvas.drawCircle(event.getX(color_map.get(k)), event.getY(color_map.get(k)), radius + 15, paint);
//                    refreshView();
//                }
           // }


        }

        else if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_MOVE) {
//                if (event.getPointerCount()==1) {
//                    //getColorIndex(colors);
//                    paint.setColor(colors[i]);
//                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
//                    paintingCanvas.drawCircle(event.getX(), event.getY(), radius + 15, paint);
//                    refreshView();
//                }
//                else {


                    for (int k = 0; k < event.getPointerCount(); k++) {
                        //color_map.put(event.getPointerId(k), colors[k]);
                        paint.setColor(color_map.get(event.getPointerId(k)));
                        paint.setStyle(Paint.Style.FILL_AND_STROKE);
                        paintingCanvas.drawCircle(event.getX(k), event.getY(k), radius + 15, paint);

                        refreshView();
                    }
                //}

        }

        else if ((event.getAction() & MotionEvent.ACTION_MASK)== MotionEvent.ACTION_UP) {
            //Paint paint = new Paint();
            //getColorIndex(colors);
        }
        else if ((event.getActionMasked()) == MotionEvent.ACTION_POINTER_DOWN) {
            getColorIndex(colors);
//            paint.setColor(colors[i]);
//            paint.setStyle(Paint.Style.FILL_AND_STROKE); //event.getPointerId(event.getActionIndex())
//            //for (int k = 0; k < event.getPointerCount(); k++) {
//                getColorIndex(colors);
//                color_map.put(event.getPointerId(event.getActionIndex()), colors[i]);
//                paintingCanvas.drawCircle(event.getX(event.getPointerId(event.getActionIndex())), event.getY(event.getPointerId(event.getActionIndex())), radius + 15, paint);
//            //}
//            refreshView();
            color_map.put(event.getPointerId(event.getPointerCount()-1), colors[i]);
//                for (int k = 0; k < event.getPointerCount(); k++) {
//                    color_map.put(event.getPointerId(k), colors[k]);
//                    paint.setColor(colors[k]);
//                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
//                    paintingCanvas.drawCircle(event.getX(color_map.get(k)), event.getY(color_map.get(k)), radius + 15, paint);
//                    refreshView();
//                }


        }
        else if  ((event.getActionMasked()) == MotionEvent.ACTION_POINTER_UP) {

            //color_map.remove(event.getPointerId(event.getActionIndex()));
//            //Paint paint = new Paint();
//            if (i == colors.length - 1)
//                i = 0;
//            else
//                i++;
         //   getColorIndex(colors);
        }
        return true;
    }

    public void getColorIndex(int[] colors) {
        if (i == colors.length - 1)
            i = 0;
        else
            i++;

    }
}
