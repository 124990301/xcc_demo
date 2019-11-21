package xyf.xcc_demo;

import java.util.concurrent.*;

public class XccDemo {

    public static void main(String[] agrs){
        /**
         * 创建线程池对象
         * ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
         * BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)
         * 参数
         * corePoolSize - 即使空闲时仍保留在池中的线程数，除非设置 allowCoreThreadTimeOut
         * maximumPoolSize - 池中允许的最大线程数
         * keepAliveTime - 当线程数大于内核时，这是多余的空闲线程在终止前等待新任务的最大时间。
         * unit - keepAliveTime参数的时间单位
         * workQueue - 用于在执行任务之前使用的队列。 这个队列将仅保存execute方法提交的Runnable任务。
         * threadFactory - 执行程序创建新线程时使用的工厂
         * handler - 执行被阻止时使用的处理程序，因为达到线程限制和队列容量
         *
         * 创建一个线程池，正常情况有5个线程，最大10个（5个闲置销毁的线程），闲置销毁时间5分钟，任务队列可以存放9个任务，用默认的线程工厂创建线程，当线程超过10个（maximumPoolSize），再需要创建新线程时，会抛异常
         * 当同一时间任务数大于maximumPoolSize+队列允许的最大线程数之和时，就会报错
         */
        ExecutorService pool = new ThreadPoolExecutor(5,10,5L, TimeUnit.MINUTES,new ArrayBlockingQueue(9),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy() );
        //固定大小的线程池,等待的任务队列无限大，因此maximumPoolSize永远等不到新建线程，所以没有用
        ExecutorService pool2 = Executors.newFixedThreadPool(5);
        //无线容量线程池，所有任务都会临时创建线程，并在默认的时间后销毁
        ExecutorService pool3 = Executors.newCachedThreadPool();
        //创建任务
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("id:"+Thread.currentThread().getId()+"    name:"+Thread.currentThread().getName());
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        //将任务交给线程池对象，线程池对象会取出线程执行任务
        for(int i=0;i<20;i++){
            pool.execute(runnable);
        }

    }
}
