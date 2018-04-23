package i.interrupt;

import java.util.concurrent.CountDownLatch;

/**
 * �����������ӳ��߳̽���ֱ����ﵽ��ֹ״̬�������������൱��һ���ţ��ڱ����������״̬֮ǰ��������һֱ�ǹرյģ�
 * ����û���κ��߳���ͨ�����ڱ����������״̬�󣬽������ٸı�״̬����������ȷ��ĳЩ�߳�ֱ�������̶߳���ɺ������
 * ���õı�����CountDownLatch
 * @author yyl-pc
 *
 */
public class MyTest2 {
	public static void main(String[] args) throws Exception {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(10);
		for(int i=0;i<10;i++) {
			new Thread() {
				public void run() {
					try {
						//��Щ�߳�һֱ������ֱ��startGate���캯���Ĳ���ֵ���0��û����һ��countDown��ֵ��ؼ�1��
						//�����0�󣬸�CountDownLatch�е������̻߳�ͬʱ�����ѣ�ͬʱ����
						startGate.await();
						System.out.println(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					endGate.countDown();
				};
			}.start();
		}
		long start = System.currentTimeMillis();
		startGate.countDown();
		endGate.await();//endGate�е������̱߳�������ֱ��10���߳�ȫ������endGate.countDown()����������ִ��
		long end = System.currentTimeMillis();
		System.out.println("��ʱ��"+(end-start));
	}
}
