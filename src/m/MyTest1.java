package m;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ThreadDeadLock线程饥饿死锁:
 * 如果所有正在执行任务的线程都由于等待其他仍处于工作队列中的任务而阻塞，这种现象被称为“饥饿线程死锁”
 * 
 * @author yyl-pc
 *
 */
public class MyTest1 {
	public static void main(String[] args) throws Exception{
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Thread thread = new Thread() {
			@Override
			public void run(){
				Callable<Integer> callable = new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						return 11;
					}
				};
				Future<Integer> future = executor.submit(callable);
				try {
					//这里一定要get方法获取，因为这样才会造成阻塞，这个线程阻塞才会造成死锁
					//若future.get()执行了则 ---------不会打印，因为死锁了
					System.out.println(future.get());
					System.out.println("--------------");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		executor.submit(thread);
		Thread.sleep(1000);//这个很关键，睡眠一秒，导致死锁，无法关闭线程池
		executor.shutdown();
	}
}
