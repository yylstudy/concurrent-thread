
package q.waitAndNotify;

/**
 * 调用obj.await()方法会释放obj的锁，notify只会通知等待队列的第一个线程，notifyAll会通知所有等待该竞争资源的线程
 * 假如有三个线程执行了 obj.wait()方法，那么obj.notifyAll()则能全部唤醒 thread1,thread2,thread3，但是要继续执行obj.wait()
 * 后面的代码，就需要先获得锁，因此，thread1,thread2,thread3都要先获得锁才能向下运行，
 * 但是 调用obj.notify或者notifyAll方法时当前线程并不会释放锁，所以thread1,thread2,thread3必须要等到调用obj.notify或者notifyAll
 * 的线程是否这个锁时，才能继续向下执行
 * 演示：两个线程循环打印AB
 * @author yyl-pc
 *
 */
public class MyTest1 {
	public static void main(String[] args) throws Exception {
		new MyThread("1").start();
		Thread.sleep(10);
		new MyThread1("1").start();
	}
	static class MyThread extends Thread{
		private String s;
		public MyThread(String s) {
			this.s = s;
		}
		@Override
		public void run() {
			for(int i=0;i<10;i++) {
				synchronized(s) {
					System.out.print("A");
					try {
						s.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
					s.notify();
				}
			}
		}
	}
	static class MyThread1 extends Thread{
		private String s;
		public MyThread1(String s) {
			this.s = s;
		}
		@Override
		public void run() {
			for(int i=0;i<10;i++) {
				synchronized(s) {
					s.notify();
					System.out.print("B");
					try {
						s.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
