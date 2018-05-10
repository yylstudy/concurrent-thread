package q.cache;
/**
 * 并不是一种很好的解决方案，将重试机制交给调用者去实现不是一个很好的选择
 * 而且这种方法性能不好，会消耗大量CPU的时间，属于忙等
 * @author yyl-pc
 *
 */
public class MyTest1 {
	public static void main(String[] args) {
		GrumpyBoundedBuffer<String> gbb = new GrumpyBoundedBuffer<String>(10);
		while(true) {
			gbb.take();
		}
	}
	static class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V>{
		protected GrumpyBoundedBuffer(int capacaty) {
			super(capacaty);
		}
		public synchronized void put(V v) {
			if(!isFull())
				doPut(v);
		}
		public synchronized V take() {
			if(!isEmpty())
				return doTake();
			return null;
		}
	}
}
