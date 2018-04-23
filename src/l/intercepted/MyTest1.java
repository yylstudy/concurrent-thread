package l.intercepted;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import annotation.ThreadSafe;
/**
 * 使用volatile修饰线程变量，来达到终止线程的作用
 * @author yyl-pc
 *
 */
@ThreadSafe
public class MyTest1 {
	public static class PrimeGenerator implements Runnable{
		private final List<BigInteger> primes = new ArrayList<BigInteger>();
		private volatile boolean cancelled;
		@Override
		public void run() {
			BigInteger bi = BigInteger.ONE;
			while(!cancelled) {
				bi = bi.nextProbablePrime();
				synchronized (this) {
					primes.add(bi);
				}
			}
		}
		public void cancel() {
			cancelled = true;
		}
		public synchronized List<BigInteger> get(){
			return new ArrayList<BigInteger>(primes);
		}
	}
	public static void main(String[] args) throws Exception {
		PrimeGenerator pg = new PrimeGenerator();
		new Thread(pg).start();
		Thread.sleep(1000);
		pg.cancel();
		System.out.println(pg.get().size());
	}
}
