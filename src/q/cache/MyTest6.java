package q.cache;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 一个简单闭锁的实现（没懂）
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
				//如果闭锁是开的（state==1）那么这个操作将成功
				return (getState()==1)?1:-1;
			}
			protected boolean tryReleaseShared(int ignored) {
				setState(1);
				return true;
			}
		}
	}
}
