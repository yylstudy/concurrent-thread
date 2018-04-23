package l.intercepted;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ���ɿ���ȡ�������������������������Ĳ�����:
 * ����������Ǹ�BlockingQueue�Ǹ��н��������У�1000���������߳�����1���ӻ�������������д�볬��1000�����¸ö���
 * �������ˣ���ʹ�����ٵ���cancel������ȥ�����жϱ�ʶ������ʱ��������Զ���ܼ�������ʶ����Ϊ�������߳��޷���������put������
 * �ָ�����
 * @author yyl-pc
 *
 */
public class MyTest2 {
	public static class BrokenPrimeProducer extends Thread{
		private final BlockingQueue<BigInteger> queue;
		private volatile boolean cancelled;
		public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
			this.queue = queue;
		}
		@Override
		public void run() {
			BigInteger bi = BigInteger.ONE;
			while(!cancelled) {
				try {
					queue.put(bi = bi.nextProbablePrime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		public void cancel() {
			cancelled = true;
		}
	}
	public static void main(String[] args) throws Exception{
		BlockingQueue<BigInteger> queue = new ArrayBlockingQueue<BigInteger>(1000);
		BrokenPrimeProducer bpp = new BrokenPrimeProducer(queue);
		bpp.start();
		Thread.sleep(1000);
		bpp.cancel();
	}
	
	
	
	
}
