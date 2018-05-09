package q.cache;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition���������������ǣ�������ֻ��ӵ��һ���������У�wait��notify��
 * ��ʹ��condition����ӵ�ж��������Conditionʵ�ֵ��н绺��
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
