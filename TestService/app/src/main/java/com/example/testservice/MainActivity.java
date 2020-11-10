package com.example.testservice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    //定义浮动窗口布局
    LinearLayout mFloatLayout;
    //创建浮动窗口设置布局参数的对象
    WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
    WindowManager mWindowManager;
    //** Called when the activity is first created.
    private static final String TAG = "MainActivity";
    private static final int GET_PERMISSION = 2214;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FloatingWindowActivity的布局视图按钮
        Button start = (Button)findViewById(R.id.start_id);

        Button remove = (Button)findViewById(R.id.remove_id);

        start.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (!Settings.canDrawOverlays(MainActivity.this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, GET_PERMISSION);;
                }else{
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(MainActivity.this, FxService.class);
                    startService(intent);
                    finish();
                }
            }
        });

        remove.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                //uninstallApp("com.phicomm.hu");
                Intent intent = new Intent(MainActivity.this, FxService.class);
                stopService(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case GET_PERMISSION:
                if(resultCode == RESULT_OK)
                {
                    Intent intent = new Intent(MainActivity.this, FxService.class);
                    startService(intent);
                    finish();
                    break;
                }
            default:
                break;
        }
    }

    private void uninstallApp(String packageName)
    {
        Uri packageURI = Uri.parse("package:"+packageName);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(uninstallIntent);
        //setIntentAndFinish(true, true);
    }



   /* private void forceStopApp(String packageName)
    {
    	 ActivityManager am = (ActivityManager)getSystemService(
                 Context.ACTIVITY_SERVICE);
    		 am.forceStopPackage(packageName);

    	Class c = Class.forName("com.android.settings.applications.ApplicationsState");
    	Method m = c.getDeclaredMethod("getInstance", Application.class);

    	  //mState = ApplicationsState.getInstance(this.getApplication());
    }*/

}