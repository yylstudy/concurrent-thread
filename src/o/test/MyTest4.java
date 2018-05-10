package o.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import o.test.MyTest3.BoundedBuffer;

/**
 * CyclicBarrier����դ����ʹ�ã�դ��������Ϊ21����ʼ��10���������̺߳�10���������̣߳�
 * �ڽ���put��take����֮ǰ��awaitס��֪�����е������������̺߳��������̶߳��ύ���̳߳�
 * ��ʼִ�У�
 * ʹ������դ����ԭ�����ڣ�����Runnable����Ҫʱ��ģ����Ե�һ��Runnable���������Runnbale�Ծ��С��������ơ���
 * ʹ������դ������ʹ10���������̺߳�10���������߳�������ͬʱ���У����Կ������߳��ڵ����Ȳ������� await
 * ����ȷ�������߳��ڿ�ʼִ���κβ���֮ǰ��ִ�е�ͬһ��λ�ã�
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
				barrier.await();//ʹ�õ�һ��դ��������put��take������
				barrier.await();//ʹ�õڶ���դ�����ȴ����е�put��take������ִ����ϣ�
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
