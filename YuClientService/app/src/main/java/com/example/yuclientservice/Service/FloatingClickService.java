package com.example.yuclientservice.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.yuclientservice.MyApplication;
import com.example.yuclientservice.R;
import com.example.yuclientservice.Utils.Extentions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class FloatingClickService extends Service {
    private WindowManager mWindowManager;
    private View mFloatingView;
    private AutoClickService mAutoClickService = null;
    private List<Point> listPoint = new ArrayList<Point>();
    private Timer timer = null;
    private boolean isRepeate = false;

    public FloatingClickService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Inflate the floating view layout we created
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_widget, null);
        mAutoClickService = ((MyApplication)this.getApplication()).getClickService();
        //Add the view to the window.
        int overlayParam = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)?
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_PHONE;

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                overlayParam,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
        params.x = 0;
        params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        //Drag and move floating view using user's touch action.
        mFloatingView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //remember the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //get the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);

                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mFloatingView, params);
                        return true;
                }
                return false;
            }
        });

        //Run button
        mFloatingView.findViewById(R.id.btnPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open youtube view
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=VWLGzTS2goo"));
                intent.setPackage("com.google.android.youtube");
                startActivity(intent);

                if(listPoint.size() > 0) {
                    for(Point point : listPoint)
                    {
                        delayToClick(point, 30000);
                    }
                }
            }
        });

        //Button record
        mFloatingView.findViewById(R.id.btnRecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int location[] = new int[2];
                mFloatingView.getLocationOnScreen(location);
                Point point = new Point(location[0], location[1]);
                Toast.makeText(FloatingClickService.this,"x: " + point.x + "--y: " + point.y,Toast.LENGTH_SHORT).show();
                listPoint.add(point);
            }
        });

        //Button clear
//        mFloatingView.findViewById(R.id.btcClear).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                listPoint.clear();
//                Toast.makeText(FloatingClickService.this,"Clear record ",Toast.LENGTH_LONG).show();
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }

    private void delayToClick(Point point, long delayTime)
    {

        Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAutoClickService.click(point.x, point.y);
            }
        },delayTime);
    }
}