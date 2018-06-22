package m;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * 拓展ThreadPoolExecutor:
 * 他提供了几个在子类化中改写的方法，beforeExecute,afterExecute,terminated这些方法可用于拓展ThreadPoolExecutor
 * 在执行任务的线程将调用beforeExecute、afterExecute、terminated 在这些方法中可添加日志计时，监视等功能，
 * 无论任务是从run正常返回，还是抛出异常返回，afterExecute都会被调用，如果是抛出Error，afterExecute则不会被调用，如果beforeExecutor
 * 中抛出RuntimeException，那么任务将不会被执行，afterExecute也不会被调用
 * 在线程池完成关闭操作时调用 terminated，也就是所有任务都已经完成并且所有工作者线程都已经关闭后，
 * terminated可以用来释放Executor在其生命周期中分配的各种资源，此外还可以执行发送通知、记录日志、收集finalize统计信息等操作
 * @author yyl-pc
 *
 */
public class MyTest9 {
	static class TimingThreadPool extends ThreadPoolExecutor{
		
		private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
		private final Logger log = Logger.getLogger("TimingThreadPool");
		private final AtomicLong numTasks = new AtomicLong();
		private final AtomicLong totalTime = new AtomicLong();
		@Override
		protected void beforeExecute(Thread t, Runnable r) {
			super.beforeExecute(t, r);
			log.fine(String.format("Thread %s:start %s", t,r));
			startTime.set(System.currentTimeMillis());
		}
		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			try {
				long endTime = System.currentTimeMillis();
				long taskTime = endTime-startTime.get();
				numTasks.incrementAndGet();
				totalTime.addAndGet(taskTime);
				log.fine(String.format("Thread %s:start %s,time=%dns", t,r,taskTime));
			}finally {
				super.afterExecute(r, t);
			}
		}
		@Override
		protected void terminated() {
			log.info(String.format("Terminated :avg time=%dns",totalTime.get()/numTasks.get()));
			super.terminated();
		}
		public TimingThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
				BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}
	}
}
