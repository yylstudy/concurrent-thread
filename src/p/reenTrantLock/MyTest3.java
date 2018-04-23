package p.reenTrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ���жϵ�����ȡ����������Ҫһ�����жϵ���ʱ
 * lockInterruptibly����Ӧ��Щ�жϲ���
 * @author yyl-pc
 *
 */
public class MyTest3 {
	private Lock lock = new ReentrantLock();

	public boolean sendOnSharedLine(String message) throws InterruptedException {
		lock.lockInterruptibly();// �ܹ��ڻ�ȡ����ͬʱ�����ֶ��жϵ���Ӧ
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
