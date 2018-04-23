package i.interrupt;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier的高级运用，CyclicBarrier有一个更高级的构造函数，CyclicBarrier(int parties, Runnable barrierAction)
 * 用于在线程到达屏障时，优先执行barrierAction
 * 1会先输出
 * CyclicBarrier和CountDownLatch的区别：
 * CountDownLatch计数器只能使用一次，而CyclicBarrier计数器，可以使用reset()方法重置，所以CyclicBarrier能处理更复杂的业务
 * @author yyl-pc
 *
 */
public class MyTest7 {
	public static void main(String[] args) throws Exception{
		CyclicBarrier cb = new CyclicBarrier(3,new Runnable() {
			@Override
			public void run() {
				System.out.println("1：我应该是最先执行的，虽然我没有执行CyclicBarrier的await方法");
			}
		});
		for(int i=0;i<2;i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						cb.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("我在1之后随机输出");
				}
			}.start();
		}
		cb.await();
	}
}
