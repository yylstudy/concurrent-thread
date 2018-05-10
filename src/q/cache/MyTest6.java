package q.cache;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ͨ������ʵ�ּ����ź�����ʹ��Condition��ģ��Semaphore��ʵ�֣�����˵�൱��
 * @author yyl-pc
 *
 */
public class MyTest6 {
	static class SemaphoreOnLock{
		private final Lock lock = new ReentrantLock();
		private final Condition permitsAvaiable = lock.newCondition();
		private int permits;
		SemaphoreOnLock(int permits){
			lock.lock();
			try {
				this.permits = permits;
			}finally {
				lock.unlock();
			}
		}
		public void acquire() throws InterruptedException {
			lock.lock();
			try {
				while(permits<=0) 
					permitsAvaiable.await();
				permits--;	
			}finally {
				lock.unlock();
			}
		}
		public void release() {
			lock.lock();
			try {
				permits++;
				permitsAvaiable.signal();
			}finally {
				lock.unlock();
			}
		}
	}
}
