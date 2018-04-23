package i.interrupt;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier�ĸ߼����ã�CyclicBarrier��һ�����߼��Ĺ��캯����CyclicBarrier(int parties, Runnable barrierAction)
 * �������̵߳�������ʱ������ִ��barrierAction
 * 1�������
 * CyclicBarrier��CountDownLatch������
 * CountDownLatch������ֻ��ʹ��һ�Σ���CyclicBarrier������������ʹ��reset()�������ã�����CyclicBarrier�ܴ�������ӵ�ҵ��
 * @author yyl-pc
 *
 */
public class MyTest7 {
	public static void main(String[] args) throws Exception{
		CyclicBarrier cb = new CyclicBarrier(3,new Runnable() {
			@Override
			public void run() {
				System.out.println("1����Ӧ��������ִ�еģ���Ȼ��û��ִ��CyclicBarrier��await����");
			}
		});
		for(int i=0;i<2;i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						cb.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("����1֮��������");
				}
			}.start();
		}
		cb.await();
	}
}
