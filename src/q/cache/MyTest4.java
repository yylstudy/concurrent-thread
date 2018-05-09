package q.cache;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition与内置锁的区别是：内置锁只能拥有一个条件队列（wait、notify）
 * 而使用condition可以拥有多个，基于Condition实现的有界缓存
 * @author yyl-pc
 *
 */
@SuppressWarnings("unchecked")
public class MyTest4 {
	static class ConditionBoundedBuffer<V>{
		private final int BUFFER_SIZE = 10;
		protected final Lock lock = new ReentrantLock();
		private final Condition notFull = lock.newCondition();
		private final Condition notEmpty = lock.newCondition();
		private final V[] items = (V[]) new Object[BUFFER_SIZE];
		private int tail,head,count;
		public void put(V v) throws InterruptedException {
			lock.lock();
			try {
				while(count==items.length)
					notFull.await();
				items[tail] = v;
				if(++tail==items.length)
					tail = 0;
				++count;
				notEmpty.signal();
			}finally {
				lock.unlock();
			}
		}
		public V take() throws InterruptedException {
			lock.lock();
			try {
				while(count==0)
					notEmpty.await();
				V v = items[head];
				items[head] = null;
				if(++head==items.length)
					head=0;
				--count;
				notFull.signal();
				return v;
			}finally {
				lock.unlock();
			}
		}
	}
}
