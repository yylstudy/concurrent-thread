package j.cache;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

/**
 * 使用FutureTask来解决ConcurrentHash做缓存的第二个问题，思路是ConcurrentHashMap中的key是FutureTask,若存在这个FutureTask，
 * 其他线程在compute这个key时，就不执行compute方法，而是直接等待这个FutureTask的返回，
 * 这里就完美的解决了该问题，但是还存在线程安全的问题
 * @author yyl-pc
 *
 */
public class MyTest3 {
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
		private final Map<A,FutureTask<V>> cache = new ConcurrentHashMap<A,FutureTask<V>>();
		private Computable<A,V> computable;
		public Memoizer(Computable<A,V> computable) {
			this.computable = computable;
		}
		@Override
		public V compute(A arg) throws Exception {
			FutureTask<V> future = cache.get(arg);
			if(future==null) {
				future = new FutureTask<V>(new Callable<V>() {
					@Override
					public V call() throws Exception {
						return computable.compute(arg);
					}
				});
				cache.put(arg, future);
				new Thread(future).start();
			}
			return future.get();
		}
	}
}
