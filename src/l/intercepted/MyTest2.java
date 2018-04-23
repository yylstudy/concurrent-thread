package l.intercepted;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 不可靠的取消操作，把生产者置于阻塞的操作中:
 * 这里的问题是该BlockingQueue是个有界阻塞队列，1000，而在主线程阻塞1秒钟会往该阻塞队列写入超过1000，导致该队列
 * 被填满了，即使后面再调用cancel方法，去重置中断标识，但此时生产者永远不能检查这个标识，因为生产者线程无法从阻塞的put方法中
 * 恢复过来
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
