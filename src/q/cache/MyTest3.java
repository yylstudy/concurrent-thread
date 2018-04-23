package q.cache;

/**
 * 条件队列，使用 wait、notify、notifyAll来实现有界阻塞队列，比较好的做法
 * @author yyl-pc
 *
 */
public class MyTest3 {
	static class BoundedBuffer<V> extends BaseBoundedBuffer<V>{
		protected BoundedBuffer(int capacaty) {
			super(capacaty);
		}
		public synchronized void put(V v) throws InterruptedException {
			while(isFull())
				wait();
			doPut(v);
			notifyAll();
		}
		public synchronized V take() throws InterruptedException {
			while(isEmpty()) {
				wait();
			}
			V v = doTake();
			notifyAll();
			return v;
		}
	}
}
