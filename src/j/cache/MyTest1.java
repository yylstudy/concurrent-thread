package j.cache;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用HashMap作缓存工具
 * 使用HashMap保存之前的计算结果，来达到缓存的作用
 * 这里主要的问题在于HashMap不是线程安全的，那么在compute 整个方法加上锁，效率低
 * @author yyl-pc
 *
 */
public class MyTest1 {
	private static interface Computable<A,V>{
		V compute(A arg) throws Exception;
	}
	public static class ExpensiveFunction implements Computable<String,BigInteger>{
		@Override
		public BigInteger compute(String arg) throws Exception {
			//经过长时间的计算后
			return new BigInteger(arg);
		}
	}
	public static class Memoizer<A,V> implements Computable<A,V>{
		private final Map<A,V> cache = new HashMap<A,V>();
		private Computable<A,V> computable;
		public Memoizer(Computable<A,V> computable) {
			this.computable = computable;
		}
		@Override
		public synchronized V compute(A arg) throws Exception {
			V result = cache.get(arg);
			if(result==null) {
				result = computable.compute(arg);
				cache.put(arg, result);
			}
			return result;
		}
	}
}
