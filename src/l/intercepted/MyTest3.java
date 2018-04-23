package l.intercepted;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import l.intercepted.MyTest2.BrokenPrimeProducer;

/**
 * 每个线程都有一个boolean类型的中断状态，当中断线程时，这个线程的中断状态将被置为true，
 * 对中断操作的正确理解是它并不会真正的去中断一个正在运行的线程，而只是发出中断请求，然后由线程在下一个合适的时刻中断自己
 * public void interrupt() 
 * 
 * 在使用这个方法时，要小心因为他会清除当前线程的中断状态，如果在调用interrupted方法时返回true，
 * 那么除非你想屏蔽这个中断，否则必须对它进行处理
 * public static boolean interrupted() 
 * public boolean isInterrupted()
 * 使用interrupt解决myTest2中存在的问题
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
