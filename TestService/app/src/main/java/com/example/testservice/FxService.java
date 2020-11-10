package com.example.testservice;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.OutputStream;

public class FxService extends Service {
    LinearLayout mFloatLayout;
    WindowManager.LayoutParams wmParams;
    //创建浮动窗口设置布局参数的对象
    WindowManager mWindowManager;

    Button mFloatStart;
    Button mFloatDrag;
    Button mFloatSetting;
    Button mFloatStop;
    Button mFloatCap;
    Boolean click = false;

    private static final String TAG = "FxService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate");
        createFloatView();
    }

    private void createFloatView() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        wmParams = new WindowManager.LayoutParams();
        mWindowManager = (WindowManager)getApplication().getSystemService(getApplication().WINDOW_SERVICE);

        wmParams.type = LAYOUT_FLAG;
        wmParams.format = PixelFormat.OPAQUE;
        wmParams.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        ;

        wmParams.gravity = Gravity.LEFT | Gravity.TOP;

        wmParams.x = 0;
        wmParams.y = 0;

        /*// 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 80;*/

        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        mFloatLayout = (LinearLayout) inflater.inflate(R.layout.float_layout, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);

        Log.i(TAG, "mFloatLayout-->left" + mFloatLayout.getLeft());
        Log.i(TAG, "mFloatLayout-->right" + mFloatLayout.getRight());
        Log.i(TAG, "mFloatLayout-->top" + mFloatLayout.getTop());
        Log.i(TAG, "mFloatLayout-->bottom" + mFloatLayout.getBottom());

        //浮动窗口按钮
        mFloatStart = (Button)mFloatLayout.findViewById(R.id.start_id);
        mFloatDrag = (Button) mFloatLayout.findViewById(R.id.drag_id);
//        mFloatSetting = (Button) mFloatLayout.findViewById(R.id.setting_id);
//        mFloatStop = (Button) mFloatLayout.findViewById(R.id.stop_id);
        mFloatCap = (Button) mFloatLayout.findViewById(R.id.cap_id);

        mFloatLayout.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        Log.i(TAG, "Width/2--->" + mFloatStart.getMeasuredWidth()/2);
        Log.i(TAG, "Height/2--->" + mFloatStart.getMeasuredHeight() / 2);

        //设置监听浮动窗口的触摸移动
        mFloatDrag.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                wmParams.x = (int) event.getRawX() - mFloatLayout.getMeasuredWidth() / 2;
                //Log.i(TAG, "Width/2--->" + mFloatStart.getMeasuredWidth()/2);
//				Log.i(TAG, "RawX" + event.getRawX());
//				Log.i(TAG, "X" + event.getX());
                //25为状态栏的高度
                wmParams.y = (int) event.getRawY() - mFloatLayout.getMeasuredHeight() -25;
                // Log.i(TAG, "Width/2--->" + mFloatStart.getMeasuredHeight()/2);
//	            Log.i(TAG, "RawY" + event.getRawY());
//	            Log.i(TAG, "Y" + event.getY());
                //刷新
                mWindowManager.updateViewLayout(mFloatLayout, wmParams);
                return false;
            }
        });

        mFloatStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.i(TAG, "clicked on 'Start'");
//                float x = mFloatLayout.getMeasuredWidth() / 2;
//                float y = mFloatLayout.getMeasuredHeight() / 2;
                float x = 10;
                float y = 10;
                for (int i = 0; i <  10; i ++) {
                    autoClick2(x, y);
                }
            }
        });

//        mFloatStop.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                click = false;
//            }
//        });

        mFloatCap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FxService.this, "Setting clicked", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "clicked on 'Setting'");
                screenCap();
            }
        });
    }

    public FxService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
    private void autoClick2(float x, float y) {
//        StopWatch sw = new StopWatch();
//        sw.start();
//        double startTime = sw.getTime();
        try {
            Process process = Runtime.getRuntime().exec("su", null, null);
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            String cmd = "/system/bin/input tap " + x + " " + y + "\n";
            os.writeBytes(cmd);
            os.writeBytes("exit\n");
            os.flush();
            os.close();
            process.waitFor();
        } catch(Exception e) {
            Log.i(TAG, e.toString());

        }
//        double endTime = sw.getTime();
//        double runTime = endTime - startTime;
//        Log.i(TAG, Double.toString(runTime));
    }

    private void screenCap() {
//        StopWatch sw = new StopWatch();
//        sw.start();
//        double startTime = sw.getTime();
        try {
            Process sh = Runtime.getRuntime().exec("su", null, null);

            OutputStream os = sh.getOutputStream();
            os.write(("/system/bin/screencap -p " + "/sdcard/autoClicker/img.png").getBytes("ASCII"));
            os.flush();

            os.close();
            sh.waitFor();
        } catch (Exception e) {
            Log.i(TAG, e.toString());
        }
//        double endTime = sw.getTime();
//        double runTime = endTime - startTime;
//        Log.i(TAG, Double.toString(runTime));
    }
}