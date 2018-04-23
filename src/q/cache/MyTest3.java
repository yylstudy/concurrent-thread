package q.cache;

/**
 * �������У�ʹ�� wait��notify��notifyAll��ʵ���н��������У��ȽϺõ�����
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
