package h.synchronizedCollection;

import java.util.Vector;
import java.util.concurrent.CyclicBarrier;

/**
 * Vector�Ͽ��ܵ��»��ҽ���ĸ��ϲ���
 * ʹ��CyclicBarrier����դ���ķ�ʽ��������ײ��Գ������±�Խ�磬����ֻ�ǲ��Զ���߳�ͬʱ����
 * deleteLast�ᱨ��ͬ�����߳�ͬʱ����getLast��deleteLastҲ���ᱨ������������Ƕ�
 * List������������ע�ⲻ�Ƕ�getLast��deleteLast���м���������g.concurrent.MyTest4��
 * public static Object getLast(Vector list) {
 * 		synchronized(list){
 * 			int lastIndex = list.size()-1;
			return list.get(lastIndex);
 * 		}
	}
	ʹ��CyclicBarrier����쳣����
 * @author yyl-pc
 */
public class MyTest1 {
	public static Object getLast(Vector list) {
		int lastIndex = list.size()-1;
		return list.get(lastIndex);
	}
	public static void deleteLast(Vector list) {
		int lastIndex = list.size()-1;
		list.remove(lastIndex);
	}
	public static void main(String[] args) throws Exception{
		Vector vector = new Vector();
		for(int i=0;i<10;i++)
			vector.add(i);
		CyclicBarrier cb = new CyclicBarrier(10);
		for(int i=0;i<9;i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						cb.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
					deleteLast(vector);
				}
			}.start();
		}
		cb.await();
		System.out.println(vector);
	}
}
