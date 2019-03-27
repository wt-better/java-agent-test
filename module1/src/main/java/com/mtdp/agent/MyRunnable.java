package com.mtdp.agent;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/25
 */
public class MyRunnable implements Runnable {

    private Runnable runnable;

    private MyRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public void run() {
        System.out.println("before...");

        runnable.run();

        System.out.println("end...");
    }

    public static MyRunnable getInstance(Runnable runnable) {
        return new MyRunnable(runnable);
    }
}
