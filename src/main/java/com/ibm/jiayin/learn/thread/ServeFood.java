package com.ibm.jiayin.learn.thread;

import com.ibm.jiayin.learn.food.*;
import com.ibm.jiayin.learn.utils.Mylog4j;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.Semaphore;


public class ServeFood implements Runnable {
    private PriorityBlockingQueue priorityBlockingQueue;
    private Map<Integer, Class<? extends TakeFood>> takeFoodMap = new HashMap<>();
    Semaphore semaphore = new Semaphore(2);

    public ServeFood(PriorityBlockingQueue priorityBlockingQueue) {

        this.priorityBlockingQueue = priorityBlockingQueue;
        takeFoodMap.put(0, ChopSticks.class);
        takeFoodMap.put(1, ForkKnife.class);
    }

    public void run() {
        try {
            while (true) {
                semaphore.acquire();
                Food food = (Food) this.priorityBlockingQueue.take();
                Random random = new Random();
                int rand = random.nextInt(2);
                Class<? extends TakeFood> cls = takeFoodMap.get(rand);
                TakeFood takeFood = cls.newInstance();
                takeFood.wayToTake(food);
                if(this.priorityBlockingQueue.size()==0){
                    Mylog4j.printInfoLog("菜上完了");
                    break;
                }
                semaphore.release();
            }
            semaphore.release();

        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
