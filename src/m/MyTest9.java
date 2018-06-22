package m;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * ��չThreadPoolExecutor:
 * ���ṩ�˼��������໯�и�д�ķ�����beforeExecute,afterExecute,terminated��Щ������������չThreadPoolExecutor
 * ��ִ��������߳̽�����beforeExecute��afterExecute��terminated ����Щ�����п������־��ʱ�����ӵȹ��ܣ�
 * ���������Ǵ�run�������أ������׳��쳣���أ�afterExecute���ᱻ���ã�������׳�Error��afterExecute�򲻻ᱻ���ã����beforeExecutor
 * ���׳�RuntimeException����ô���񽫲��ᱻִ�У�afterExecuteҲ���ᱻ����
 * ���̳߳���ɹرղ���ʱ���� terminated��Ҳ�������������Ѿ���ɲ������й������̶߳��Ѿ��رպ�
 * terminated���������ͷ�Executor�������������з���ĸ�����Դ�����⻹����ִ�з���֪ͨ����¼��־���ռ�finalizeͳ����Ϣ�Ȳ���
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
