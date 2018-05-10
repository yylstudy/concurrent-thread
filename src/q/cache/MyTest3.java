package q.cache;

/**
 * 条件队列，使用 wait、notify、notifyAll来实现有界阻塞队列，比较好的做法
 * 大多数情况下，应该优先使用notifyAll而不是使用notify
 * 因为notify可能造成类似于信号丢失的问题：
 * 假设线程A在条件1为真时 向下执行否则阻塞，线程B在条件2为真时向下执行否则阻塞，线程C调用notify
 * 唤醒线程A或者线程B，但是条件1和条件2只有一个为真，那么线程C只有在正确的选择条件为真的那个
 * 才能向下执行，否则还是阻塞，所以，通常情况下建议使用notifyAll而不是notify
 * 虽然notifyAll比notify更低效，但是却更容易保证类行为的正确性
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
