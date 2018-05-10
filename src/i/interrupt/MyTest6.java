package i.interrupt;

import java.util.concurrent.CyclicBarrier;

/**
 * 	դ����ʹ�ã���Ҫ��CyclicBarrier��ʹ��
 * CyclicBarrier���ǿ�ѭ��ʹ�ã�Cyclic�������ϣ�Barrier������Ҫ������������һ���̵߳���һ������
 * ��Ҳ���Խ�ͬ���㣩ʱ��������ֱ�����һ���̵߳�������ʱ�����ϲŻῪ�ţ����б��������ص��̲߳Ż�����ɻ�
 * ���������ʾ��������դ����
 * @author yyl-pc
 *
 */
public class MyTest6 {
	public static void main(String[] args) throws Exception{
		//�ò�����ʾ�������ص��߳�����ÿ���̵߳���await����������CyclicBarrier�����ѵ������ϣ�Ȼ��ǰ�̱߳�����
		//������Ӳ���Ϊ2�������̺߳��ڲ����̶߳��Ѿ�����await����ʱ���������߳̾ͻ��������ִ�У�
		//�������������Ϊ3����ô���̺߳����̻߳���Զ�ȴ�����Ϊû�е������߳�ִ��await
		CyclicBarrier bar = new CyclicBarrier(2);
		new Thread() {
			public void run() {
				try {
					bar.await();
					bar.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
		bar.await();
		bar.await();
		System.out.println("�ﵽͨ��դ���߳���������");
	}
	
	
}
