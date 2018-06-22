package m;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 配置ThreadPoolExecutor
 * ThreadPoolExecutor是 AbstractExecutorService的子类，而AbstractExecutorService是ExecutorSerivce的子类
 * ExecutorSerivce是Executor的子类，这就是ThreadPoolExecutor的类级目录
 * @author yyl-pc
 */
public class MyTest3 {
	public static void main(String[] args) {
		/**
		 * ThreadPoolExecutor主要参数
		 * corePoolSize 核心池大小，也就是在没有任务执行时线程池的大小，并且只有在工作队列满了之后，才会创建超出这个数量的线程
		 * 超出核心池大小的线程+核心池大小构成了最大线程数
		 * maximumPoolSize 最大线程数，
		 * keepAliveTime 存活时间，如果某个线程的空闲时间超过了这个时间，那么该线程就会被回收，
		 * unit  存活时间的单位
		 * workQueue，任务队列 
		 */
//		new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		
		/**
		 * newFixedThreadPool这种线程池是将 corePoolSize和maximumPoolSize都设置为该参数，并且存活时间为0
		 * 表示这种线程池的空余线程不会被回收
		 * 
		 * newFixedThreadPool和newSingleThreadExecutor,默认情况下是使用一个无界的LinkedBlockingQueue作为阻塞队列
		 */
		ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.
				newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1);//大多数情况下最优线程池个数的配置
		for(int i=0;i<6;i++) {
			Runnable my = new MyTest3.MyRunnable();
			executorService.submit(my);
		}
		System.out.println(executorService.getActiveCount());
		
	}
	public static class MyRunnable implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
