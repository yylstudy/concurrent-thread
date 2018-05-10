package o.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import o.test.MyTest3.BoundedBuffer;

/**
 * CyclicBarrier两道栅栏的使用：栅栏数设置为21，初始化10个生产者线程和10个消费者线程，
 * 在进行put和take操作之前，await住，知道所有的生产生产者线程和消费者线程都提交到线程池
 * 开始执行，
 * 使用两道栅栏的原因在于，创建Runnable是需要时间的，所以第一个Runnable相对于其它Runnbale仍具有“领先优势”，
 * 使用两道栅栏就是使10个生产者线程和10个消费者线程真正的同时运行，可以看到在线程内的首先操作都是 await
 * 这能确保所有线程在开始执行任何操作之前都执行到同一个位置，
 * @author yyl-pc
 *
 */
public class MyTest4 {
	private static final ExecutorService pool = Executors.newCachedThreadPool();
	public static void main(String[] args) throws Exception {
		new PutTakeTest(10,10,100000).test();
		pool.shutdown();
	}
	static class PutTakeTest{
		private final AtomicInteger putSum = new AtomicInteger(0);
		private final AtomicInteger takeSum = new AtomicInteger(0);
		private final CyclicBarrier barrier;
		private final BoundedBuffer<Integer> bb;
		private final int nTrials;
		private final int nPairs;
		public PutTakeTest(int capacity,int nPairs,int nTrials) {
			this.nTrials = nTrials;
			this.nPairs = nPairs;
			bb = new BoundedBuffer<Integer>(capacity);
			barrier = new CyclicBarrier(nPairs*2+1);//21
		}
		public void test() {
			try {
				for(int i=0;i<nPairs;i++) {//10
					pool.execute(new Producer());
					pool.execute(new Consumer());
				}
				barrier.await();//使用第一道栅栏（进行put和take操作）
				barrier.await();//使用第二道栅栏（等待所有的put和take操作都执行完毕）
				assertEquals(putSum.get(), takeSum.get());
			}catch(Exception e) {
			}
		}
	   class Producer implements Runnable{
			@Override
			public void run() {
				int seed = this.hashCode() ^ (int)System.nanoTime();
				int sum = 0;
				try {
					barrier.await();
					for(int i=nTrials;i>0;--i) {
						bb.put(seed);
						sum+=seed;
						seed = xorShift(seed);
					}
					putSum.addAndGet(sum);
					barrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	   static int xorShift(int y) {
	        y ^= (y << 6);
	        y ^= (y >>> 21);
	        y ^= (y << 7);
	        return y;
	    }
	   class Consumer implements Runnable{
			@Override
			public void run() {
				try {
					barrier.await();
					int sum = 0;
					for(int i=nTrials;i>0;--i) {
						sum+=bb.take();
					}
					takeSum.addAndGet(sum);
					barrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
