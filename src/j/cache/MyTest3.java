package j.cache;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

/**
 * ʹ��FutureTask�����ConcurrentHash������ĵڶ������⣬˼·��ConcurrentHashMap�е�key��FutureTask,���������FutureTask��
 * �����߳���compute���keyʱ���Ͳ�ִ��compute����������ֱ�ӵȴ����FutureTask�ķ��أ�
 * ����������Ľ���˸����⣬���ǻ������̰߳�ȫ������
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
			//������ʱ��ļ����
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
