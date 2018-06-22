package m;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ����ThreadPoolExecutor
 * ThreadPoolExecutor�� AbstractExecutorService�����࣬��AbstractExecutorService��ExecutorSerivce������
 * ExecutorSerivce��Executor�����࣬�����ThreadPoolExecutor���༶Ŀ¼
 * @author yyl-pc
 */
public class MyTest3 {
	public static void main(String[] args) {
		/**
		 * ThreadPoolExecutor��Ҫ����
		 * corePoolSize ���ĳش�С��Ҳ������û������ִ��ʱ�̳߳صĴ�С������ֻ���ڹ�����������֮�󣬲Żᴴ����������������߳�
		 * �������ĳش�С���߳�+���ĳش�С����������߳���
		 * maximumPoolSize ����߳�����
		 * keepAliveTime ���ʱ�䣬���ĳ���̵߳Ŀ���ʱ�䳬�������ʱ�䣬��ô���߳̾ͻᱻ���գ�
		 * unit  ���ʱ��ĵ�λ
		 * workQueue��������� 
		 */
//		new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		
		/**
		 * newFixedThreadPool�����̳߳��ǽ� corePoolSize��maximumPoolSize������Ϊ�ò��������Ҵ��ʱ��Ϊ0
		 * ��ʾ�����̳߳صĿ����̲߳��ᱻ����
		 * 
		 * newFixedThreadPool��newSingleThreadExecutor,Ĭ���������ʹ��һ���޽��LinkedBlockingQueue��Ϊ��������
		 */
		ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.
				newFixedThreadPool(Runtime.getRuntime().availableProcessors()+1);//���������������̳߳ظ���������
		for(int i=0;i<6;i++) {
			Runnable my = new MyTest3.MyRunnable();
			executorService.submit(my);
		}
		System.out.println(executorService.getActiveCount());
		
	}
	public static class MyRunnable implements Runnable{
		@Override
		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
