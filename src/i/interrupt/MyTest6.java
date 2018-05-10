package i.interrupt;

import java.util.concurrent.CyclicBarrier;

/**
 * 	栅栏的使用：主要是CyclicBarrier的使用
 * CyclicBarrier就是可循环使用（Cyclic）的屏障（Barrier）：它要做的事情是让一组线程到达一个屏障
 * （也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活
 * 这个例子演示的是两道栅栏，
 * @author yyl-pc
 *
 */
public class MyTest6 {
	public static void main(String[] args) throws Exception{
		//该参数表示屏障拦截的线程数，每个线程调用await方法，告诉CyclicBarrier，我已到达屏障，然后当前线程被阻塞
		//这个例子参数为2，当主线程和内部的线程都已经调用await方法时，这两个线程就会继续向下执行，
		//若把这个参数改为3，那么主线程和子线程会永远等待，因为没有第三个线程执行await
		CyclicBarrier bar = new CyclicBarrier(2);
		new Thread() {
			public void run() {
				try {
					bar.await();
					bar.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
		bar.await();
		bar.await();
		System.out.println("达到通过栅栏线程数：开门");
	}
	
	
}
