package r.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 非阻塞的计数器（使用cas实现一个线程安全的计数器）
 * 可看到increment并没有加锁，但是increment是线程安全的原子操作，原因是：采用了重试操作
 * 由于没有加锁，所以increment方法不是阻塞的，只是CAS（比较、替换）若失败就重试，知道重新赋值
 * 所以CasCounter是线程安全的，
 * 初看起来，CAS的计数器似乎比基于锁的计数器性能差一些，但实际上，当竞争程度不高时，基于CAS的计数器性能远远超过基于锁的
 * 计数器，
 * @author yyl-pc
 *
 */
public class MyTest2 {
	public static void main(String[] args) throws InterruptedException {
		long t1 = System.currentTimeMillis();
		CasCounter cas = new CasCounter(new SimulateCas());
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for(int i=0;i<1000000;i++) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					cas.increment();
				}
			});
		}
		pool.shutdown();
		pool.awaitTermination(10, TimeUnit.SECONDS);  
		System.out.println(cas.getValue());
		System.out.println(System.currentTimeMillis()-t1);
	}
	static class CasCounter{
		private SimulateCas value;
		public CasCounter(SimulateCas value) {
			this.value = value;
		}
		public int getValue() {
			return value.get();
		}
		public int increment() {
			int v;
			do {
				v = value.get();
			}while(v!=value.compareAndSwap(v, v+1));
			return v+1;
		}
	}
	static class SimulateCas{
		private int value;
		public synchronized int get() {
			return value;
		}
		public synchronized int compareAndSwap(int expectedValue,int newValue) {
			int oldValue = value;
			if(oldValue==expectedValue)
				value = newValue;
			return oldValue;
		}
		public synchronized boolean compareAndSet(int exectedValur,int newValue) {
			return compareAndSwap(exectedValur,newValue)==exectedValur;
		}
	}
}
