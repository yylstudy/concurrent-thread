package q.cache;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * һ���򵥱�����ʵ�֣�û����
 * @author yyl-pc
 *
 */
public class MyTest6 {
	static class OneShotLatch{
		private final Sync sync = new Sync();
		public void signal() {
			sync.releaseShared(0);
		}
		public void await() throws InterruptedException {
			sync.acquireInterruptibly(0);
		}
		private static class Sync extends AbstractQueuedSynchronizer{
			protected int tryAcquireShared(int ignored) {
				//��������ǿ��ģ�state==1����ô����������ɹ�
				return (getState()==1)?1:-1;
			}
			protected boolean tryReleaseShared(int ignored) {
				setState(1);
				return true;
			}
		}
	}
}
