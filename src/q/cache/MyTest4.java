package q.cache;

/**
 * CountDownLatch闭锁：这个闭锁有个缺陷，这个闭锁打开后无法再关闭
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
