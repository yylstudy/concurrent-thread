package p.reenTrantLock;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 实际情况中，对数据结构的大多数操作都是读，所以读读不互斥很与必要、、
 * ReadWrteLock是读读不互斥，读写互斥，写写互斥
 * 读写锁，ReadWriteLock，在实际情况中，对于在多处理器上被频繁读取的数据结构，读-写锁能提高性能，其它情况下，
 * 独占锁性能比ReadWriteLock好，所以在使用ReadWriteLock时，最好通过测试，是否性能优于独占锁，否则可能
 * ReadWriterLock的性能可能比独占锁性能还要低
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
