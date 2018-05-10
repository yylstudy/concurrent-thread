package q.cache;

/**
 * CountDownLatch��������������и�ȱ�ݣ���������򿪺��޷��ٹر�
 * @author yyl-pc
 *
 */
public class MyTest4 {
	public static void main(String[] args) {
		
	}
	static class ThreadGate{
		private boolean isOpen;
		private int generation;
		public synchronized void close(){
			isOpen = false;
		}
		public synchronized void open() {
			++generation;
			isOpen = true;
			notifyAll();
		}
		public synchronized void await() throws InterruptedException {
			int arrivalGeneration = generation;
			while(!isOpen&&arrivalGeneration==generation) {
				wait();
			}
		}
	}
}
