package com.example.kolkokrzyzyk.ui.home;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;


public class Timer extends Thread {
    private long timeToGet;
    private Handler handler;
    private CountDownTimer currentTime;
    Timer(long timeToGet, Handler handler){
        this.timeToGet = timeToGet*1000;
        this.handler = handler;
    }
    @Override
    public void run() {

        currentTime = new CountDownTimer(timeToGet, 1000){
            @Override
            public void onTick(long millisUntilFinished){

                timeToGet = millisUntilFinished;

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

}
