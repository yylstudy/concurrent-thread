package m;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 比较MyTest4 MyTest5速度会慢些，说明线程池Runtime.getRuntime().availableProcessors()+1的配置会比
 * newCachedThreadPool这个性能好些
 * @author yyl-pc
 *
 */
public class MyTest5 {
	static Integer executeCount = 0;
	public static void main(String[] args) throws Exception{
		long l1 = System.currentTimeMillis();
		String ss = "11";
		ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1);
//		ExecutorService pool = Executors.newFixedThreadPool(10);
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
