package com.lyming.distribute;

/**
 * @ClassName UnitTest
 * @Description TODO
 * @Author lyming
 * @Date 2020/4/13 2:04 下午
 **/
public class UnitTest extends Thread {

    @Override
    public void run() {
        while (true) {
            DistributeLock distributeLock = new DistributeLock();
            String identifier = distributeLock.acquireLock("updateOrder", 2000, 5000);
            if (identifier != null) {
                System.out.println(Thread.currentThread().getName() + "-->获得锁成功:" + identifier);
                try {
                    //假设处理业务逻辑时间
                    Thread.sleep(1000);
                    distributeLock.releaseLock("updateOrder", identifier);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static void main(String[] args) {
        UnitTest unitTest = new UnitTest();
        for (int i = 0; i < 10; i++) {
            new Thread(unitTest, "tName:" + i).start();
        }
    }
}
