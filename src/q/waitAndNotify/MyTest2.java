package q.waitAndNotify;

/**
 * 演示：三个线程循环打印ABC
 * 
 * @author yyl-pc
 *
 */
public class MyTest2 {
	public static void main(String[] args) throws Exception {
		new MyThread("A","B").start();
		Thread.sleep(10);
		new MyThread("B","C").start();
		Thread.sleep(10);
		new MyThread("C","A").start();
	}

	static class MyThread extends Thread {
		private String lock1;
		private String lock2;

		public MyThread(String lock1, String lock2) {
			this.lock1 = lock1;
			this.lock2 = lock2;
		}

		@Override
		public void run() {
			try {
				for (int i = 0; i < 10; i++) {
					synchronized (lock1) {
						synchronized(lock2) {
							System.out.print(lock1);
							lock2.notify();
						}
						lock1.wait();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
}
