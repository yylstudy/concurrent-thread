package g.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * �ͻ��˼�������
 * �������ͬ��������һ��������ʾ��Ϊʲô���ַ�ʽ���ܴﵽ�̰߳�ȫ�أ��Ͼ�putIfAbsent�����Ѿ�������synchronized��
 * ԭ��Ϊ��synchronized�Ǽ��ڴ���������ˣ�list˵��Ҳ���̰߳�ȫ�ģ�����list�ϵ�����putIfAbsent�����ϵ�������һ�����������޷�
 * ʵ��ͬ���Ĺ��ܣ�
 * ������Խ��Ϊtest��˵����putIfAbsent���������û�к�add����ͬ����������ִ����add��������ִ����putIfAbsent
 * @author yyl-pc
 *
 */
public class MyTest4 {
	public static class ListHelper<E>{
		public List<E> list = Collections.synchronizedList(new ArrayList<E>());
		public synchronized boolean putIfAbsent(E e) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(!list.contains(e)) {
				list.add(e);
				return true;
			}
			return false;
		}
		@Override
		public String toString() {
			return list.toString();
		}
	}
	public static void main(String[] args) throws Exception{
		ListHelper<String> lh = new ListHelper<String>();
		new Thread() {
			@Override
			public void run() {
				lh.putIfAbsent("test");
			}
		}.start();
		Thread.sleep(100);
		new Thread() {
			@Override
			public void run() {
				lh.list.add("test");
			}
		}.start();
		Thread.sleep(2000);
		System.out.println(lh);
	}
	
	
	
	
	
	
}
