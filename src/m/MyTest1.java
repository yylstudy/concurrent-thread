package m;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ThreadDeadLock�̼߳�������:
 * �����������ִ��������̶߳����ڵȴ������Դ��ڹ��������е�������������������󱻳�Ϊ�������߳�������
 * 
 * @author yyl-pc
 *
 */
public class MyTest1 {
	public static void main(String[] args) throws Exception{
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Thread thread = new Thread() {
			@Override
			public void run(){
				Callable<Integer> callable = new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						return 11;
					}
				};
				Future<Integer> future = executor.submit(callable);
				try {
					//����һ��Ҫget������ȡ����Ϊ�����Ż��������������߳������Ż��������
					//��future.get()ִ������ ---------�����ӡ����Ϊ������
					System.out.println(future.get());
					System.out.println("--------------");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		executor.submit(thread);
		Thread.sleep(1000);//����ܹؼ���˯��һ�룬�����������޷��ر��̳߳�
		executor.shutdown();
	}
}
