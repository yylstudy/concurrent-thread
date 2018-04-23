package o.test;

/**
 * ���ֶμ�����ConcurrentHashMap����ʹ�������ּ���
 * @author yyl-pc
 *
 */
public class MyTest2 {
	static class StripedMap {
		private static final int N_LOCKS = 16;
		private final Node[] buckets;
		private final Object[] locks;
		public StripedMap(int numBuckets) {
			buckets = new Node[numBuckets];
			locks = new Object[N_LOCKS];
			for (int i = 0; i < N_LOCKS; i++) {
				locks[i] = new Object();
			}
		}
		private final int hash(Object key) {// hashֵ�ǶԽڵ�ȡ��
			return Math.abs(key.hashCode() % buckets.length);
		}
		public Object get(Object key) {
			int hash = hash(key);
			synchronized(locks[hash%N_LOCKS]) {//�ٶ�16ȡ�࣬����ͬ���ĸ���
				for(Node m = buckets[hash];m!=null;m = m.next) {
					if(m.key.equals(key)) {
						return m.value;
					}
				}
			}
			return null;
		}
		public void clear() {
			for(int i=0;i<buckets.length;i++) {
				synchronized (locks[i % N_LOCKS]) {
					 buckets[i] = null;
				}
			}
		}
	}
	static class Node {
		Node next;
		Object key;
		Object value;
	}
}
