package q.cache;

/**
 * �������У�ʹ�� wait��notify��notifyAll��ʵ���н��������У��ȽϺõ�����
 * ���������£�Ӧ������ʹ��notifyAll������ʹ��notify
 * ��Ϊnotify��������������źŶ�ʧ�����⣺
 * �����߳�A������1Ϊ��ʱ ����ִ�з����������߳�B������2Ϊ��ʱ����ִ�з����������߳�C����notify
 * �����߳�A�����߳�B����������1������2ֻ��һ��Ϊ�棬��ô�߳�Cֻ������ȷ��ѡ������Ϊ����Ǹ�
 * ��������ִ�У����������������ԣ�ͨ������½���ʹ��notifyAll������notify
 * ��ȻnotifyAll��notify����Ч������ȴ�����ױ�֤����Ϊ����ȷ��
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
