package p.reenTrantLock;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ʵ������У������ݽṹ�Ĵ�����������Ƕ������Զ�������������Ҫ����
 * ReadWrteLock�Ƕ��������⣬��д���⣬дд����
 * ��д����ReadWriteLock����ʵ������У������ڶദ�����ϱ�Ƶ����ȡ�����ݽṹ����-д����������ܣ���������£�
 * ��ռ�����ܱ�ReadWriteLock�ã�������ʹ��ReadWriteLockʱ�����ͨ�����ԣ��Ƿ��������ڶ�ռ�����������
 * ReadWriterLock�����ܿ��ܱȶ�ռ�����ܻ�Ҫ��
 * @author yyl-pc
 *
 */
public class MyTest5 {
	static class ReadWriteMap<K,V>{
		private final Map<K,V> map;
		private final ReadWriteLock lock = new ReentrantReadWriteLock();
		private Lock r = lock.readLock();
		private Lock w = lock.writeLock();
		public ReadWriteMap(Map<K,V> map) {
			this.map = map;
		}
		public V put(K k,V v) {
			try {
				w.lock();
				return map.put(k, v);
			}finally {
				w.unlock();
			}
		}
		public V get(K k) {
			try {
				r.lock();
				return map.get(k);
			}finally {
				r.unlock();
			}
		}
	}
	
	
}
