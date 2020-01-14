package com.example.kolkokrzyzyk.ui.home;



import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;


import android.util.Log;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.kolkokrzyzyk.R;


public class Timer extends Thread {
    private long timeToGet;
    private Handler handler;
    private Handler handler2;
    private CountDownTimer currentTime;
    private ViewGroup container;
    private View newTextView;
    LayoutInflater inflater;

    private View root;

    Timer(long timeToGet, Handler handler, Handler handler2){
        this.timeToGet = timeToGet*1000;
        this.handler = handler;
        this.container = container;
        this.inflater = inflater;
        this.handler2 = handler2;
    }
    @Override
    public void run() {

        currentTime = new CountDownTimer(timeToGet, 1000){
            @Override
            public void onTick(long millisUntilFinished){

                timeToGet = millisUntilFinished;
                sendMessage2(timeToGet);


            }
            @Override
            public void onFinish(){


                sendMessage();
            }
        };

        currentTime.start();
    }
    public void sendMessage(){
        final Message msg = new Message();
        final Bundle b = new Bundle();
        Integer value = 1332;
        b.putInt("KEY", value);
        msg.setData(b);
        handler.sendMessage(msg);

    }
    public void sendMessage2(long timeToGet){
        final Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("KEY2", (int)(timeToGet/1000));
        msg.setData(bundle);
        handler2.sendMessage(msg);
    }

}
