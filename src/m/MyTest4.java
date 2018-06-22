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
		pool.shutdown();//�����ʾ�᲻�ٽ��������񣬲�ִ�����̳߳������е�������˳�
		pool.awaitTermination(10, TimeUnit.SECONDS);  //�̳߳عرղ������ȴ�10��
		long l2 = System.currentTimeMillis();
		System.out.println(l2-l1);
		System.out.println(executeCount);//executeCountΪ10000 ˵�������̶߳���ִ��
	}
}
