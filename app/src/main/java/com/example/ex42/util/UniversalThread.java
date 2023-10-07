package com.example.ex42.util;

///////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////通用Thread类，使用时创建ThreadData对象，重写onExecute函数，并自定义延时时间即可/////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////
public class UniversalThread {
    long delay = -1;
    TreadData treadData = null;

    public void SetDelay(long delay){
        this.delay = delay;
    }

    public void SetFunction(TreadData treadData){
        this.treadData = treadData;
    }

    public void StartThread(){
        new Thread(new Runnable() {
            public void run() {
                //sleep设置的是时长
                try {
                    Thread.sleep(delay);
                    treadData.onExecute();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();
    }

    public UniversalThread(TreadData data, long delay){
        this.delay = delay;
        this.treadData = data;
    }
}
