package k.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ExecutorCompletionService的使用
 * ExecutorCompletionService是Executor和BlockQueue结合的jdk类，其实现主要目的是：提交任务线程，每一个线程
 * 任务直线完成后，将返回值放在阻塞队列中，然后可以通过阻塞队列的take方法返回对应线程的执行结果
 * @author yyl-pc
 *
 */
public class MyTest6 {
	public static void main(String[] args) throws Exception{
		int num = 9;
		ExecutorCompletionService executorCompletionService = new ExecutorCompletionService<>(
				MyThreadPool.getExecutor());
		for(int i=0;i<=num;i++) {
			Thread.sleep(1000);
			executorCompletionService.submit(new Task(i));
		}
		for(int i=0;i<=num;i++) {
			Future<String> future = executorCompletionService.take();
			String s = future.get();
			System.out.println(s);
		}
		MyThreadPool.getExecutor().shutdown();
	}
}
class MyThreadPool{
	private static class exec{
		private static ExecutorService executor = Executors.newCachedThreadPool();
	}
	public static ExecutorService getExecutor() {
		return exec.executor;
	}
}

class Task implements Callable<String>{
	private volatile int i;
	Task(int i){
		this.i = i;
	}
	@Override
	public String call() throws Exception {
		Thread.sleep(1001);
		return Thread.currentThread().getName()+"任务："+i;
	}
	
}





