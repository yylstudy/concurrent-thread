package k.executor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * java 线程池分为四种
 * 1：newFixedThreadPool:newFixedThreadPool将参加一个固定长度的线程池，每当提交一个任务时，就创建一个线程直到达到线程池的最大数量，
 * 2：newCachedThreadPool:newCachedThreadPool 将参加一个可缓存的线程池，如果线程池的规模超过处理需求时，那么将回收空闲的线程，而需求增加时，
 * 可以创建新的线程，线程池的规模不存在任何限制
 * 3：newSingleThreadPool: newSingleThreadPool 是一个单线程的Executor,它创建单个工作者的线程来执行任务，如果这个线程异常结束，那么会创建
 * 新的线程来替代
 * 4：newScheduledThreadPool: newScheduledThreadPool 创建了一个固定长度的线程池，而且已延时或定时的方式来执行任务，类似于Timmer
 * @author yyl-pc
 */
public class MyTest1 {
	private static final int nthreads = 100;
	private static final ExecutorService executor = Executors.newFixedThreadPool(nthreads);
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(8080);
		while(true) {
			Socket socket = server.accept();
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread());
				}
			};
			executor.submit(runnable);
		}
	}
}
