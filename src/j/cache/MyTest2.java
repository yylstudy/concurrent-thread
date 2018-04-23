package j.cache;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ʹ��ConcurrentHashMap���������������
 * ��1������������compute�����������̰߳�ȫ�ģ����ڡ��ȼ���ִ�С�
 * ��2�����ĳ���߳�������һ�������ܴ�ļ��㣬���������̲߳�֪������������ڽ���
 * ��ô���ܻ��ظ����㣬����ͨ��ĳ����������ʾ�߳�X���ڼ��� computer("27")�������������һ���̵߳Ĳ��� computer("27")
 * ���ܹ�֪�����Ч�������ǵȴ��߳�X���н�������ȥ��ѯ����computer("27")�Ľ���Ƕ���
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
			//������ʱ��ļ����
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
