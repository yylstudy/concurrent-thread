package g.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import g.concurrent.MyTest4.ListHelper;

/**
 * �ͻ��˼�������
 * ��ȷ����ͬ����list�ϵ����Ƿ�������Ҳ����list����������������ǰ�putIfAbsent������������list�ϣ�����ʵ��
 * ��ȷ���̰߳�ȫ��ͨ������һ��ԭ�Ӳ�������չ���Ǵ����ģ���Ϊ������ļ������룬�ֲ���������У�
 * 
 * ������Խ��Ϊ��� test,test ��˵��putIfAbsent��add����ͬ����add�������putIfAbsent����ִ������ִ��
 * @author yyl-pc
 *
 */
public class MyTest5 {
	public static class ListHelper<E>{
		public Vector<E> list =new Vector<E>();
		public boolean putIfAbsent(E e) {
			synchronized(list) {
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
