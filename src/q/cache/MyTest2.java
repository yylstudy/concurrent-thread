package q.cache;

/**
 * MyTest1的简化版本，但是仍然不是一个好的方案
 * @author yyl-pc
 *
 */
public class MyTest2 {
	static class SleepBoundedBuffer<V> extends BaseBoundedBuffer<V>{
		protected SleepBoundedBuffer(int capacaty) {
			super(capacaty);
		}
		public void put(V v) throws Exception {
			while(true) {
				synchronized(this) {
					if(!isFull()) {
						doPut(v);
						return ;
					}
				}
				Thread.sleep(1000);
			}
		}
		public synchronized V take() throws Exception {
			while(true) {
				synchronized(this) {
					if(!isEmpty()) {
						return doTake();
					}
				}
				Thread.sleep(1000);
			}
		}
	}
	
}
