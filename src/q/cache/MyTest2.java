package q.cache;

/**
 * MyTest1�ļ򻯰汾�������Ի���ֱ��д��put��take������������Ȼ����һ���õķ�����
 * ��Ϊ����ʧһ���ֵ���Ӧʱ��
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
