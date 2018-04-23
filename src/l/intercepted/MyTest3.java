package l.intercepted;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import l.intercepted.MyTest2.BrokenPrimeProducer;

/**
 * ÿ���̶߳���һ��boolean���͵��ж�״̬�����ж��߳�ʱ������̵߳��ж�״̬������Ϊtrue��
 * ���жϲ�������ȷ�������������������ȥ�ж�һ���������е��̣߳���ֻ�Ƿ����ж�����Ȼ�����߳�����һ�����ʵ�ʱ���ж��Լ�
 * public void interrupt() 
 * 
 * ��ʹ���������ʱ��ҪС����Ϊ���������ǰ�̵߳��ж�״̬������ڵ���interrupted����ʱ����true��
 * ��ô����������������жϣ��������������д���
 * public static boolean interrupted() 
 * public boolean isInterrupted()
 * ʹ��interrupt���myTest2�д��ڵ�����
 * @author yyl-pc
 *
 */
public class MyTest3 {
	public static class BrokenPrimeProducer extends Thread{
		private final BlockingQueue<BigInteger> queue;
		private volatile boolean cancelled;
		public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
			this.queue = queue;
		}
		@Override
		public void run() {
			try {
				BigInteger bi = BigInteger.ONE;
				while(!Thread.currentThread().isInterrupted()) {
					queue.put(bi = bi.nextProbablePrime());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		public void cancel() {
			interrupt();
		}
	}
	public static void main(String[] args) throws Exception{
		BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<BigInteger>(1000);
		BrokenPrimeProducer bpp = new BrokenPrimeProducer(queue);
		bpp.start();
		Thread.sleep(1000);
		bpp.cancel();
		System.out.println(queue.take());
	}
}
