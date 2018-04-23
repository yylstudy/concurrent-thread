package j.cache;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;


/**
 * MyTest3的实现几乎是完美的，但是还有一个缺陷，由于compute的if代码块是非原子操作，即“先检查，后执行”
 * 因此两个线程扔有可能在同一时间调用compute来计算相同的值
 * @author yyl-pc
 *
 */
public class MyTest4 {
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
				//若不存在则添加
				future = cache.putIfAbsent(arg, future);
				new Thread(future).start();
			}
			return future.get();
		}
	}
}
