package i.interrupt;

import java.util.concurrent.CountDownLatch;

/**
 * ʹ��CountDownLatchģ��10���߳���������
 * @author yyl-pc
 *
 */
public class MyTest3 {
	public static void main(String[] args) throws Exception {
		System.out.println("������ʼ");
		final CountDownLatch cdl = new CountDownLatch(11);
		for(int i=0;i<10;i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						System.out.println(Thread.currentThread().getName()+"׼������");
						cdl.countDown();
						cdl.await();
						System.out.println(Thread.currentThread().getName()+"�����յ�");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		cdl.countDown();
		cdl.await();
		Thread.sleep(1000);
		System.out.println("��������");
	}
}
