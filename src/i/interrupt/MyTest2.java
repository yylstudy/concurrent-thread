package i.interrupt;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁：可以延迟线程进度直到其达到终止状态，闭锁的作用相当于一扇门，在闭锁到达结束状态之前，这扇门一直是关闭的，
 * 并且没有任何线程能通过，在闭锁到达结束状态后，将不会再改变状态，闭锁可以确保某些线程直到其它线程都完成后再完成
 * 常用的闭锁：CountDownLatch
 * @author yyl-pc
 *
 */
public class MyTest2 {
	public static void main(String[] args) throws Exception {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(10);
		for(int i=0;i<10;i++) {
			new Thread() {
				public void run() {
					try {
						//这些线程一直阻塞，直到startGate构造函数的参数值变成0（没调用一次countDown改值解回减1）
						//当变成0后，该CountDownLatch中的所有线程会同时被唤醒，同时运行
						startGate.await();
						System.out.println(Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					endGate.countDown();
				};
			}.start();
		}
		long start = System.currentTimeMillis();
		startGate.countDown();
		endGate.await();//endGate中的所有线程被阻塞，直到10个线程全部运行endGate.countDown()，继续向下执行
		long end = System.currentTimeMillis();
		System.out.println("耗时："+(end-start));
	}
}
