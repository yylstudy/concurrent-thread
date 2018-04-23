package i.interrupt;

import java.util.concurrent.BlockingQueue;

/**
 * �߳��жϣ�Thread�ṩinterrupt�����������ж��̻߳��߲�ѯ�߳��Ƿ��жϣ��ж���һ��Э�����ƣ�һ���̲߳���ǿ�������߳�
 * ֹͣ����ִ�еĲ�����������Ҫ�������߳��ڿ�����ͣ�ĵط�ֹͣ����ִ�еĲ���
 * ����Ĵ����׳�InterruptedException�쳣����ʱ�����Լ��ķ���Ҳ���һ���������������ұ���Ҫ������жϵ���Ӧ������������
 * 1������InterruptedException��������������쳣�����߲�����쳣���д�����ٴ��׳����쳣
 * 2����ʱ�����׳�InterruptedException������Runnable�ӿ�run��������Ҫ�׳����쳣������Щ����£����벶����쳣��
 * ��ͨ����ǰ�̵߳�interrupt�����ظ��ж�״̬�������ڵ���ջ�и��߲�Ĵ��뽫��������һ���ж�
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
