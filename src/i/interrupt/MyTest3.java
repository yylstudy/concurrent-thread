package i.interrupt;

import java.util.concurrent.CountDownLatch;

/**
 * 使用CountDownLatch模拟10个线程跑马拉松
 * @author yyl-pc
 *
 */
public class MyTest3 {
	public static void main(String[] args) throws Exception {
		System.out.println("比赛开始");
		final CountDownLatch cdl = new CountDownLatch(11);
		for(int i=0;i<10;i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						System.out.println(Thread.currentThread().getName()+"准备好了");
						cdl.countDown();
						cdl.await();
						System.out.println(Thread.currentThread().getName()+"到达终点");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
		cdl.countDown();
		cdl.await();
		Thread.sleep(1000);
		System.out.println("比赛结束");
	}
}
