package m.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �Ƚ�MyTest4 MyTest5�ٶȻ��Щ��˵���̳߳�Runtime.getRuntime().availableProcessors()+1�����û��
 * newCachedThreadPool������ܺ�Щ
 * @author yyl-pc
 *
 */
public class MyTest5 {
//	static Integer executeCount = 0;
	public static void main(String[] args) throws Exception{
		long l1 = System.currentTimeMillis();
//		String ss = "11";
		ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1);
		for(int i=0;i<1000000;i++) {
			pool.execute(new Thread() {
				@Override
				public void run() {
//					synchronized(ss) {
//						executeCount++;
//					}
				}
			});
		}
		
		Thread.sleep(1);
		pool.shutdown();//�����ʾ�᲻�ٽ��������񣬲�ִ�����̳߳������е�������˳�
		long l2 = System.currentTimeMillis();
		System.out.println(l2-l1);
//		System.out.println(executeCount);//executeCountΪ10000 ˵�������̶߳���ִ��
	}
}
