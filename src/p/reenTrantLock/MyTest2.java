package p.reenTrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * 在规定时间内尝试获取锁
 * @author yyl-pc
 *
 */
public class MyTest2 {
	private Lock lock = new ReentrantLock();
	public boolean trySendOnSharedLine(String message,long timeout,TimeUnit unit) throws InterruptedException {
		long nanosToLock = unit.toNanos(timeout)-estimatedNanosToSend(message);
		if(!lock.tryLock(nanosToLock,NANOSECONDS))
			return false;//不能再规定时间获取锁，直接返回false
		try {
			return sendOnSharedLine(message);
		}finally {
			lock.unlock();
		}
	}
	private boolean sendOnSharedLine(String message) {
		return true;
	}
	long estimatedNanosToSend(String message) {
		return message.length();
	}
}
