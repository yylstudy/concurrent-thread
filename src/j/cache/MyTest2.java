package j.cache;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用ConcurrentHashMap作缓存的问题在于
 * （1）若不加锁，compute方法并不是线程安全的，存在“先检查后执行”
 * （2）如果某个线程启动了一个开销很大的计算，二而其他线程不知道这个计算正在进行
 * 那么可能会重复计算，我们通过某个方法来表示线程X正在计算 computer("27")这种情况，当另一个线程的查找 computer("27")
 * 它能够知道最高效的做法是等待线程X运行结束，再去查询缓存computer("27")的结果是多少
 * @author yyl-pc
 *
 */
public class MyTest2 {
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
		private final Map<A,V> cache = new ConcurrentHashMap<A,V>();
		private Computable<A,V> computable;
		public Memoizer(Computable<A,V> computable) {
			this.computable = computable;
		}
		@Override
		public V compute(A arg) throws Exception {
			V result = cache.get(arg);
			if(result==null) {
				result = computable.compute(arg);
				System.out.println(result);
				cache.put(arg, result);
			}
			return result;
		}
	}
	public static void main(String[] args) throws Exception{
		ExpensiveFunction ef = new ExpensiveFunction();
		Memoizer<String,BigInteger> m = new Memoizer<String,BigInteger>(ef);
//		for(int i=0;i<10;i++) {
//			m.compute("11");
//		}
		for(int i=0;i<5;i++) {
			Thread.sleep(10);
			new Thread(new Worker(m)).start();
		}
	}
	
	static class Worker implements Runnable{
		private Memoizer<String,BigInteger> m;
		public Worker(Memoizer<String,BigInteger> m) {
			this.m = m;
		}
		@Override
		public void run() {
			try {
				m.compute("11");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
