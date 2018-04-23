package h.synchronizedCollection;

import java.util.Iterator;
import java.util.Vector;

import annotation.NotThreadSafe;
import annotation.ThreadSafe;

/**
 * Vector�ĵ������⣬���³�������׳��쳣�����Ⲣ����ζ��Vector�Ͳ����̰߳�ȫ���ֻ࣬�ǵ�����ʵ��һ�����ϲ�����
 * Ϊ��ʹ�����̰߳�ȫ�����Զ���������������Ȼ�������ᵼ�������߳��޷����ʸ�Vector
 * ����ִ�ͬ��������Ҳ��û�������������������⣬for eachѭ��Ҳһ������Ϊ�����������Щ�����ĵ�������ʱ����û�п���
 * �����޸ĵ����⣬���������ڵ������������з��������߳��޸ĸ��������ͻ��׳�һ��ConcurrentModificationException,
 * concurrentHashMap�����׳������쳣���ѶԵ������д���
 * �������� ʹ��for each�� iterator��������ʱ�����޸� ���ϣ������׳�ConcurrentModificationException
 * ����ͨ��forѭ���򲻻ᣬΪ�˲��׳��쳣����ô��Ҫ������ �������������Ǽ�������Ĵ��������ܣ�
 * �����ϣ�������������ڼ��������ôһ�ֵ������ʽ���ǿ�¡�������ڸ����Ͻ��е��������ڸ�����������߳��ڣ�
 * ��������̲߳����ڵ����ڼ��޸ģ������ͱ����׳�ConcurrentModificationException���������ַ�ʽȡ���������Ĵ�С
 * ����Ƶ�ʵȵ�
 * @author yyl-pc
 */
public class MyTest2 {
	@NotThreadSafe
	public static void iterator(Vector<String> vector) {
		for(int i=0;i<vector.size();i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			doSomething(vector.get(i));
		}
	}
	@NotThreadSafe
	public static void iterator3(Vector<String> vector) {
		for(String str:vector) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			doSomething(str);
		}
	}
	@NotThreadSafe
	public static void iterator4(Vector<String> vector) {
		for(Iterator<String> iterator = vector.iterator();iterator.hasNext();) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			doSomething(iterator.next());
		}
	}
	@ThreadSafe
	public static void iterator2(Vector<String> vector) {
		synchronized(vector) {
			for(int i=0;i<vector.size();i++) {
				doSomething(vector.get(i));
			}
		}
	}
	private static void doSomething(String string) {
		System.out.println(string);
	}
	public static void main(String[] args) throws Exception {
		Vector<String> vector = new Vector<String>();
		vector.add("11");
		vector.add("22");
		vector.add("33");
		new Thread() {
			@Override
			public void run() {
				iterator4(vector);
			}
		}.start();
		Thread.sleep(100);
		new Thread() {
			@Override
			public void run() {
				vector.add("44");
			}
		}.start();
	}
	
	
	
}
