package k.executor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * �̳߳ص��������ڣ����ﲻ��ʹ��Executor��Ϊ�̳߳ص����ã���ΪExecutorû���������ڵĸ���
 * Ϊ�˹���Executor,jdk������ ExecutorService �������̳߳أ�ExecutorService��Executor������
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
	//���ڽ���������ͬʱ�ȴ��Ѿ��ύ������ִ����ɣ�������Щ��δ��ʼִ�е�����
	public void stop() {
		executor.shutdown();
	}
	//����ȡ���������������е����񣬲��Ҳ���������δ��ʼ������
	public void stopNow() {
		executor.shutdownNow();
	}
	
	
}
