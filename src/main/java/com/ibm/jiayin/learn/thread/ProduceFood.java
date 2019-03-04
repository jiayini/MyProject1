package com.ibm.jiayin.learn.thread;

import com.alibaba.fastjson.JSON;
import com.ibm.jiayin.learn.food.Food;
import com.ibm.jiayin.learn.utils.Mylog4j;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

public class ProduceFood implements Runnable {

    private PriorityBlockingQueue priorityBlockingQueue;

    public ProduceFood(PriorityBlockingQueue priorityBlockingQueue) {
        this.priorityBlockingQueue = priorityBlockingQueue;

    }

    public List readFile() {
        BufferedReader reader = null;
        String laststr = "";

        try {
            FileInputStream fileInputStream = new FileInputStream("src/resources/Courses.json");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);

            for (String tempString = null; (tempString = reader.readLine()) != null; laststr = laststr + tempString) {

            }

            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }

        }

        return JSON.parseArray(laststr, Food.class);
    }

    public void run() {
        try {
            List<Food> list = this.readFile();
            for (Food li : list) {
                priorityBlockingQueue.offer(li);
                Mylog4j.printInfoLog("厨师" + Thread.currentThread().getName() + "做好了一道" + li.getName());
                Thread.sleep(2000);
            }

        } catch (InterruptedException e) {
            Mylog4j.printErrorLog("中断异常");
            e.printStackTrace();
        }

    }
}

