package test_package.src.OtherTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {
    public static void main(String[] args) {

        // 方法一：自定义线程的继承类，覆写run方法
        Thread t1 = new MyThread();
        t1.start();// 只有start()方法能启动新线程

        // 方法二：实现Runnable接口，传入Thread构造函数
        Thread t2 = new Thread(new MyRunnable());
        t2.start();

        // 方法二另一种实现，使用lambda语法简写
        Thread t3 = new Thread(() -> {
            System.out.println("this is another new Thread by using lambda!");
        });
        t3.start();

        // 设置线程优先级
        t1.setPriority(6);// 默认值5

        try {
            t3.join();// 命令当前main线程等待t3线程直到其运行结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end...");

        // 线程的中断，可以在其他线程中调用Thread.interrupt()，让另外的线程终止
        // 而interrupt()方法只是发送中断请求，是否能立即响应，要看具体代码

        // 若线程处于等到状态（如join()方法导致的），则join()方法会立即抛出InterruptedException
        // 另外一种方法是设置标志位
        HelloThread t4 = new HelloThread();
        t4.start();
        try {
            Thread.sleep(1);// 让HelloThread执行1ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t4.running = false;

        // 创建守护线程
        // 守护线程是为其他线程服务的线程。在JVM中，所有非守护线程都执行完毕后，无论有无守护线程，虚拟机都会自动退出
        // 守护线程不能持有任何需要关闭的资源，如打开文件。
        // 虚拟机退出时，守护线程没有机会来关闭文件
        t2.setDaemon(true);// 将t2设定为守护线程

        // 进程同步
        // 通过synchronized来保证临界区只能有一个线程访问
        // 基本类型赋值和引用类型赋值都是原子操作，不需要synchronized操作

        // 线程安全类
        // 如果一个类被设计为允许多线程正确访问，则该类是线程安全的
        // 不变类如String、Integer、LocalDate，所有成员变量都是final，也是线程安全的
        // 类似Math这些只提供静态方法，没有成员变量的类，也是线程安全的。

        // 当锁住的是this实例时，那么
        // public void add(int n){
        // synchronized(this){
        // //...
        // }
        // }
        // 可以改写为
        // public synchronized void add(int n){
        // //...
        // }

        // 而对于static方法没有this实例。因此对static方法添加synchronized，锁住的是该类的Class实例

        // 线程池
        ExecutorService es = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 6; ++i) {
            es.submit(new ThreadTask("" + i));
        }
        // 关闭线程池
        //
        es.shutdown();




        //从这里可以看出，如果线程池已经被shutdown，那么就无法继续使用，否则会报错


        ExecutorService es1 = Executors.newFixedThreadPool(1);
        // 创建有返回值的线程任务Callable
        // 定义任务
        Callable<String> task = new CallableTask();
        // 提交任务 并获得Future
        // Future类型的实例代表一个未来能获取结果的对象，即返回值存到Future中
        Future<String> future = es1.submit(task);
        // 从Future获得异步执行返回的结果 若异步任务还未完成，那么get()会阻塞，直到任务完成才返回结果
        // 可以使用isDone()来判断任务是否已经完成
        try {
            String result = future.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }



        //线程内传递状态
        //在任务中定义静态变量：传入User类
        //static ThreadLocal<User> threadLocalUser = new ThreadLocal<>();
        
        //在任务开始时，通过threadLocalUser.set(user)指定实例，在最后通过threadLocalUser.remove()移除
        //在移除之前，所有方法都可以使用get()方法获取该user实例
        //如果最后没有移除ThreadLocal，那么该线程执行其他代码时，会把上一次状态带进去
    }

}

class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("this is a new MyThread!");
    }
}

class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("this is another new Thread!");
        // 给访问加锁
        synchronized (Counter.lock) {
            Counter.count += 1;
        }
    }
}

class HelloThread extends Thread {
    public volatile boolean running = true;// 线程间共享变量需要用volatile标记，确保每个线程都能读取到更新后的变量值

    @Override
    public void run() {
        int n = 0;
        while (running) {
            ++n;
            System.out.println(n + "Hello!");
        }
        System.out.println("end!");
    }
}

// 加锁
class Counter {
    public static final Object lock = new Object();
    public static int count = 0;
}

// 可重入锁ReentranLock，更安全，可以藏石获取锁
class ReentrantLockCounter {
    private final Lock lock = new ReentrantLock();
    private int count;
    //private final Condition condition = lock.newCondition();//使用condition来实现synchronized的wait和notify
    //condition.signalAll()通知所有   condition.await()等待

    public void add(int n) {
        lock.lock();
        try {
            count += n;
        } finally {
            lock.unlock();
        }
    }

    // 尝试获取锁
    public void tryadd(int n) {
        try {
            if (lock.tryLock(1, TimeUnit.SECONDS)) {//最多等待1s，如果1s后仍未获取到锁，则tryLock()返回false
                try {
                    count += n;
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


//线程池中的线程需要实现Runnable
//而实现Runnable需要覆写其方法 public void run()
class ThreadTask implements Runnable{
    private final String name;

    public ThreadTask(String name){
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("start task " + name);
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("end tast " + name);
    }


}


//Runnable接口没有返回值，如果需要返回结果，只能保存到变量
//Callable接口多了一个返回值，并且是一个泛型接口
class CallableTask implements Callable<String>{
    public String call() throws Exception {
        return "I am executed!";
    }
}