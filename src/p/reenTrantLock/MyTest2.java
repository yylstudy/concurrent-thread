package p.reenTrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * �ڹ涨ʱ���ڳ��Ի�ȡ��
 * @author yyl-pc
 *
 */
public class MyTest2 {
	private Lock lock = new ReentrantLock();
	public boolean trySendOnSharedLine(String message,long timeout,TimeUnit unit) throws InterruptedException {
		long nanosToLock = unit.toNanos(timeout)-estimatedNanosToSend(message);
		if(!lock.tryLock(nanosToLock,NANOSECONDS))
			return false;//�����ٹ涨ʱ���ȡ����ֱ�ӷ���false
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
