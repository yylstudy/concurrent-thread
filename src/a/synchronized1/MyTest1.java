package a.synchronized1;

import java.util.List;

/**
 * 
 * ���ǿ�����ģ�������������룬��ô������δ��뽫����������
 * ������������д���෽�����ڵ������෽��ʱ��ͬʱҲ���ø���ķ�����
 * @author yyl-pc
 */
public class MyTest1 {
	public static void main(String[] args) throws Exception {
		LoggingWeight lw = new MyTest1().new LoggingWeight();
		//�������ﲻ�������LoggingWeight������������Ҳ�����MyTest1����ķ�������������������
		//��ô�ڵ���super.dosomething�е��ø����dosomething�ͻᷢ������
		lw.dosomething();
	}
	
	public synchronized void dosomething() {
		System.out.println("father do something...");
	}
	
	public class LoggingWeight extends  MyTest1{
		@Override
		public synchronized void dosomething() {
			System.out.println("son do something");
			super.dosomething();
		}
	}
}

