package test_package.src.OtherTest;

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
            Thread.sleep(1);//让HelloThread执行1ms
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t4.running = false;





        //创建守护线程
        //守护线程是为其他线程服务的线程。在JVM中，所有非守护线程都执行完毕后，无论有无守护线程，虚拟机都会自动退出
        //守护线程不能持有任何需要关闭的资源，如打开文件。
        //虚拟机退出时，守护线程没有机会来关闭文件
        t2.setDaemon(true);//将t2设定为守护线程


        //进程同步
        //通过synchronized来保证临界区只能有一个线程访问
        //基本类型赋值和引用类型赋值都是原子操作，不需要synchronized操作


        //线程安全类
        //如果一个类被设计为允许多线程正确访问，则该类是线程安全的
        //不变类如String、Integer、LocalDate，所有成员变量都是final，也是线程安全的
        //类似Math这些只提供静态方法，没有成员变量的类，也是线程安全的。

        //当锁住的是this实例时，那么
        // public void add(int n){
        //     synchronized(this){
        //         //...
        //     }
        // }
        //可以改写为
        // public synchronized void add(int n){
        //     //...
        // }


        //而对于static方法没有this实例。因此对static方法添加synchronized，锁住的是该类的Class实例


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
        //给访问加锁
        synchronized(Counter.lock){
            Counter.count += 1;
        }
    }
}

class HelloThread extends Thread {
    public volatile boolean running = true;//线程间共享变量需要用volatile标记，确保每个线程都能读取到更新后的变量值
    @Override
    public void run(){
        int n = 0;
        while(running){
            ++n;
            System.out.println(n + "Hello!");
        }
        System.out.println("end!");
    }
}

//加锁
class Counter {
    public static final Object lock = new Object();
    public static int count = 0;
}