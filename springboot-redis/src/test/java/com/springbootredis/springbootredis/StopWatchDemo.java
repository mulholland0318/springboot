package com.springbootredis.springbootredis;

import org.springframework.util.StopWatch;

public class StopWatchDemo {

    public static void main(String[] args) {

        StopWatch stopWatch = new StopWatch("stopWatchTest");
        stopWatch.start("one");
        for(int i =0;i<10000;i++){
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println("--------------");
        System.out.println(stopWatch.shortSummary());
        System.out.println("---------------");
        System.out.println(stopWatch.getLastTaskInfo());
    }
}
