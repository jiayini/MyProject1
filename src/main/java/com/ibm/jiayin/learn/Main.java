package com.ibm.jiayin.learn;

import com.ibm.jiayin.learn.food.Food;
import com.ibm.jiayin.learn.thread.ProduceFood;
import com.ibm.jiayin.learn.thread.ServeFood;

import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;


public class Main {
    public static void main(String[] args) {
        //阻塞队列，里面是食物对象
        PriorityBlockingQueue<Food> priorityBlockingQueue = new PriorityBlockingQueue(15, new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return o1.style-o2.style;
            }
        });
        //线程池
        ExecutorService exec1 = Executors.newFixedThreadPool(5);
        ExecutorService exec2 = Executors.newFixedThreadPool(15);

        ProduceFood pf = new ProduceFood(priorityBlockingQueue);
        ServeFood sf = new ServeFood(priorityBlockingQueue);


        for(int i = 0; i < 3; ++i) {
            exec1.submit(pf);
            exec2.submit(sf);
        }
        exec1.shutdown();
        exec2.shutdown();

    }
}
