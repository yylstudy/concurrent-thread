package m.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * �̹߳�����ʹ�ã�ÿ���̳߳���Ҫ����һ�����߳�ʱ������ͨ���̹߳�����������ɵģ�Ĭ�ϵ��̹߳�������������һ���µġ����ػ����߳�
 * ���Ҳ����������������Ϣ����ThreadFactory��ֻ������һ������ newThread��ÿ���̳߳���Ҫ����һ�����߳�ʱ��������������
 * Ȼ�����������£�����Ҫʹ���ƶ����̹߳�������
 * @author yyl-pc
 *
 */
public class MyTest8 {
	public static void main(String[] args) {
		ThreadPoolExecutor pool = new ThreadPoolExecutor(5,
				10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
		MyThreadFactory myThreadFactory = new MyThreadFactory("my pool");
		pool.setThreadFactory(myThreadFactory);
	}
	
	static class MyThreadFactory implements ThreadFactory{
		private final String poolName;
		public MyThreadFactory(String poolName) {
			this.poolName = poolName;
		}
		@Override
		public Thread newThread(Runnable r) {
			return new MyAppThread(r,poolName);
		}
	}
	static class MyAppThread extends Thread{
		public static final String DEFAULT_NAME = "MyAppThread";
		private static volatile boolean debugLifecycle = false;
		private static final AtomicInteger created = new AtomicInteger();
		private static final AtomicInteger alive = new AtomicInteger();
		private static final Logger log = Logger.getAnonymousLogger();
		
		public MyAppThread(Runnable r) {
			this(r,DEFAULT_NAME);
		}
		public MyAppThread(Runnable runnable, String name) {
			super(runnable,name+"-"+created.incrementAndGet());
			 new Thread.UncaughtExceptionHandler(){
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					log.log(Level.SEVERE, "UNCAUGHT in thread"+t.getName(), e);
				}
			};
		}
		@Override
		public void run() {
			boolean debug = debugLifecycle;
			if(debug) {
				log.log(Level.FINE, "Created"+getName());
			}
			try {
				alive.incrementAndGet();
				super.run();
			}finally {
				alive.decrementAndGet();
				if(debug)
					log.log(Level.FINE, "exiting"+getName());
			}
		}
		public static int getThreadsCreated() {
			return created.get();
		}
		public static int getThreadsAlive() {
			return alive.get();
		}
		public static boolean getDebug() {
			return debugLifecycle;
		}
		public static void setDebug(boolean b) {
			debugLifecycle = b;
		}
	}
}
