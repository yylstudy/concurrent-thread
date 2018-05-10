package r.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author yyl-pc
 *
 */
public class MyTest3 {
	public static void main(String[] args) throws InterruptedException {
		long t1 = System.currentTimeMillis();
		SynchronizedCounter syn = new SynchronizedCounter();
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for(int i=0;i<1000000;i++) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					syn.increment();
				}
			});
		}
		pool.shutdown();
		pool.awaitTermination(10, TimeUnit.SECONDS);  
		System.out.println(syn.getValue());
		System.out.println(System.currentTimeMillis()-t1);
	}
	static class SynchronizedCounter{
		private long value;
		public synchronized long getValue() {
			return value;
		}
		public synchronized void increment() {
			value++;
		}
	}
}
