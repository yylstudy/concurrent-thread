package g.concurrent;

import java.util.Vector;

/**
 * ��չ���������
 * ��չjava������̰߳�ȫ����Ĺ����������Լ�����Ҫ������Ҫ��List��ִ��һ���������ھ���ӡ��Ĳ���
 * MyVector��������̰߳�ȫ�ģ���ȻVector��add�������̰߳�ȫ�ģ����������������ھ���ӡ��Ĳ�������ԭ�Ӳ��������ԾͲ����̰߳�ȫ��
 * �����ڷ�������Ҫ����synchronized���������synchronized�ܱ�֤�̰߳�ȫ��ԭ���ǣ�
 * ��ΪMyVector putIfAbsent �����ϵ�����Vector��add��contain�����ϵ�����ͬһ������������
 * ��ִ��putIfAbsent����ʱ������̲߳��ܶ����ִ��Vector�����Դﵽ���̰߳�ȫ
 * @author yyl-pc
 *
 */
public class MyTest3{
	public class MyVector<E> extends Vector<E>{
		public synchronized boolean putIfAbsent(E e) {
			if(!contains(e)) {
				add(e);
				return true;
			}
			return false;
		}
	}
}
