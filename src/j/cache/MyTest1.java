package j.cache;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * ʹ��HashMap�����湤��
 * ʹ��HashMap����֮ǰ�ļ����������ﵽ���������
 * ������Ҫ����������HashMap�����̰߳�ȫ�ģ���ô��compute ����������������Ч�ʵ�
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
			//������ʱ��ļ����
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
