package r.cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * �������ļ�������ʹ��casʵ��һ���̰߳�ȫ�ļ�������
 * �ɿ���increment��û�м���������increment���̰߳�ȫ��ԭ�Ӳ�����ԭ���ǣ����������Բ���
 * ����û�м���������increment�������������ģ�ֻ��CAS���Ƚϡ��滻����ʧ�ܾ����ԣ�֪�����¸�ֵ
 * ����CasCounter���̰߳�ȫ�ģ�
 * ����������CAS�ļ������ƺ��Ȼ������ļ��������ܲ�һЩ����ʵ���ϣ��������̶Ȳ���ʱ������CAS�ļ���������ԶԶ������������
 * ��������
 * @author yyl-pc
 *
 */
public class MyTest2 {
	public static void main(String[] args) throws InterruptedException {
		long t1 = System.currentTimeMillis();
		CasCounter cas = new CasCounter(new SimulateCas());
		ExecutorService pool = Executors.newFixedThreadPool(5);
		for(int i=0;i<1000000;i++) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					cas.increment();
				}
			});
		}
		pool.shutdown();
		pool.awaitTermination(10, TimeUnit.SECONDS);  
		System.out.println(cas.getValue());
		System.out.println(System.currentTimeMillis()-t1);
	}
	static class CasCounter{
		private SimulateCas value;
		public CasCounter(SimulateCas value) {
			this.value = value;
		}
		public int getValue() {
			return value.get();
		}
		public int increment() {
			int v;
			do {
				v = value.get();
			}while(v!=value.compareAndSwap(v, v+1));
			return v+1;
		}
	}
	static class SimulateCas{
		private int value;
		public synchronized int get() {
			return value;
		}
		public synchronized int compareAndSwap(int expectedValue,int newValue) {
			int oldValue = value;
			if(oldValue==expectedValue)
				value = newValue;
			return oldValue;
		}
		public synchronized boolean compareAndSet(int exectedValur,int newValue) {
			return compareAndSwap(exectedValur,newValue)==exectedValur;
		}
	}
}
