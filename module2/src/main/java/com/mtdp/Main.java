package com.mtdp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/25
 */
public class Main {

    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> System.out.println("run...");

        executorService.submit(runnable);

        Thread.sleep(2000);

        executorService.shutdown();
    }
}
