package k.executor;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * java �̳߳ط�Ϊ����
 * 1��newFixedThreadPool:newFixedThreadPool���μ�һ���̶����ȵ��̳߳أ�ÿ���ύһ������ʱ���ʹ���һ���߳�ֱ���ﵽ�̳߳ص����������
 * 2��newCachedThreadPool:newCachedThreadPool ���μ�һ���ɻ�����̳߳أ�����̳߳صĹ�ģ������������ʱ����ô�����տ��е��̣߳�����������ʱ��
 * ���Դ����µ��̣߳��̳߳صĹ�ģ�������κ�����
 * 3��newSingleThreadPool: newSingleThreadPool ��һ�����̵߳�Executor,���������������ߵ��߳���ִ�������������߳��쳣��������ô�ᴴ��
 * �µ��߳������
 * 4��newScheduledThreadPool: newScheduledThreadPool ������һ���̶����ȵ��̳߳أ���������ʱ��ʱ�ķ�ʽ��ִ������������Timmer
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
