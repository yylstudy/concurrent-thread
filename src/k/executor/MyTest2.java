package k.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池的生命周期，这里不能使用Executor作为线程池的引用，因为Executor没有生命周期的概念
 * 为了管理Executor,jdk加入了 ExecutorService 来管理线程池，ExecutorService是Executor的子类
 * @author yyl-pc
 *
 */
public class MyTest2 {
	private static final int nthreads = 100;
	private static final ExecutorService executor = Executors.newFixedThreadPool(nthreads);
	public void start() throws Exception {
		ServerSocket server = new ServerSocket(8080);
		while(!executor.isShutdown()) {
			Socket socket = server.accept();
			executor.execute(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread());
				}
			});
		}
	}
	//不在接收新任务，同时等待已经提交的任务执行完成，包括那些还未开始执行的任务
	public void stop() {
		executor.shutdown();
	}
	//尝试取消所有正在运行中的任务，并且不再启动尚未开始的任务
	public void stopNow() {
		executor.shutdownNow();
	}
	
	
}
