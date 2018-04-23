package q.cache;
/**
 * ������һ�ֺܺõĽ�����������ַ������ܲ��ã�
 * @author yyl-pc
 *
 */
public class MyTest1 {
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
	public static void main(String[] args) {
		GrumpyBoundedBuffer<String> gbb = new GrumpyBoundedBuffer<String>(10);
		while(true) {
			gbb.take();
		}
	}
}
