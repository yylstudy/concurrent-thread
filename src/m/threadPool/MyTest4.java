package m.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTest4 {
	public static void main(String[] args) throws Exception{
		long l1 = System.currentTimeMillis();
		ExecutorService pool = Executors.newCachedThreadPool();
		for(int i=0;i<1000000;i++) {
			pool.execute(new Thread() {
				@Override
				public void run() {
					
				}
			});
		}
		Thread.sleep(1);
		pool.shutdown();
		long l2 = System.currentTimeMillis();
		System.out.println(l2-l1);
	}
}
