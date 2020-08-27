package com.daddyno1.hellookhttp3.okio;

import java.util.concurrent.TimeUnit;

public class MultiThreadTest {
    public static void main(String[] args) {
        Some som = new Some();

        Runnable run = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            som.stop();
        };
        new Thread(run).start();

        som.doSome();
        System.out.println("end");
    }

    static class Some{
        private volatile boolean isStop = false;        // 保证多线程可见性
        void doSome(){
            System.out.println("start");
            while (true){
                if(isStop){
                    break;
                }
            }
            System.out.println("done");
        }
        void stop(){
            isStop = true;
        }
    }
}
