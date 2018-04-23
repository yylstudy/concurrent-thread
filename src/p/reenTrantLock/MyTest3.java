package p.reenTrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可中断的锁获取操作：当需要一个可中断的锁时
 * lockInterruptibly可响应那些中断操作
 * @author yyl-pc
 *
 */
public class MyTest3 {
	private Lock lock = new ReentrantLock();

	public boolean sendOnSharedLine(String message) throws InterruptedException {
		lock.lockInterruptibly();// 能够在获取锁的同时，保持对中断的响应
		try {
			return cancellableSendOnSharedLine(message);
		} finally {
			lock.unlock();
		}
	}

	private boolean cancellableSendOnSharedLine(String message) throws InterruptedException {
		/* send something */
		return true;
	}
}
