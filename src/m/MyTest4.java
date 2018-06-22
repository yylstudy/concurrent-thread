package m;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyTest4 {
	static Integer executeCount = 0;
	public static void main(String[] args) throws Exception{
		long l1 = System.currentTimeMillis();
		String ss = "11";
		ExecutorService pool = Executors.newCachedThreadPool();
		for(int i=0;i<1000000;i++) {
			pool.execute(new Thread() {
				@Override
				public void run() {
					synchronized(ss) {
						executeCount++;
					}
				}
			});
		}
		pool.shutdown();//这个表示会不再接受新任务，并执行完线程池中所有的任务才退出
		pool.awaitTermination(10, TimeUnit.SECONDS);  //线程池关闭并且最多等待10秒
		long l2 = System.currentTimeMillis();
		System.out.println(l2-l1);
		System.out.println(executeCount);//executeCount为10000 说明所有线程都会执行
	}
}
