package i.interrupt;

import java.util.concurrent.BlockingQueue;

/**
 * 线程中断：Thread提供interrupt方法，用于中断线程或者查询线程是否被中断，中断是一种协作机制，一个线程不能强制其他线程
 * 停止正在执行的操作，仅仅是要求其他线程在可以暂停的地方停止正在执行的操作
 * 当你的代码抛出InterruptedException异常方法时，你自己的方法也变成一个阻塞方法，并且必须要处理对中断的响应，有两种做法
 * 1：传递InterruptedException，根本不捕获该异常，或者捕获该异常进行处理后再次抛出该异常
 * 2：有时候不能抛出InterruptedException，例如Runnable接口run方法中需要抛出该异常，在这些情况下，必须捕获该异常，
 * 并通过当前线程的interrupt方法回复中断状态，这样在调用栈中更高层的代码将看到引发一个中断
 * @author yyl-pc
 *
 */
public class MyTest1 {
	public static class MyRunnable implements Runnable{
		private BlockingQueue<String> blockingQueue;
		@Override
		public void run() {
			try {
				blockingQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt(); 
			}
		}
	}
}
