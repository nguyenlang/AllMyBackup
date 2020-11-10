package com.example.yuclientservice.Service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.example.yuclientservice.Activity.MainActivity;
import com.example.yuclientservice.MyApplication;
import com.example.yuclientservice.Utils.Event;

import java.util.ArrayList;
import java.util.List;

public class AutoClickService extends AccessibilityService {
    List<Event> events = new ArrayList<Event>();
    String TAG = "CLICK_SERVICE";
    public AutoClickService() {
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //TODO:
    }

    @Override
    public void onInterrupt() {
        //TODO
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "onServiceConnected:");
        //((MyApplication)this.getApplication()).setClickService(this);
        //startActivity(new Intent(this, MainActivity.class));
    }
}