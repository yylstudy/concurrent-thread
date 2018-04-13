package a.synchronized1;

import java.math.BigInteger;

/**
 * 该方法模拟一个Servlet ，统计命中率
 * 这里是使用synchronized long类型参数来实现，这里使用AtomicLong原子递增类是没有必要的，如果我们使用同步代码块来实现线程安全
 * 那么再使用原子递增类性能上是不会有任何提升
 * @author yyl-pc
 *
 */
public class MyTest3{
	private BigInteger lastNumber;
	private BigInteger[] lastFactors;
	private long hits;
	private long cacheHits;
	
	public long getHits() {
		return cacheHits;
	}
	public double getCacheHitRatio() {
		return (double)cacheHits/hits;
	}
	public void service() {
		BigInteger i = new BigInteger("1");
		BigInteger[] factors = null;
		synchronized(this){
			hits++;
			if(i==lastNumber) {
				cacheHits++;
				factors = lastFactors.clone();
			}
		}
		if(factors==null) {
			factors = new BigInteger[] {i};
			synchronized(this) {
				lastNumber = i;
				lastFactors = factors.clone();
			}
		}
	}
	
}
