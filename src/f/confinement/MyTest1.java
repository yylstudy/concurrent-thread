package f.confinement;

import java.util.HashSet;
import java.util.Set;

/**
 * ��װ��վ��ǵ�һ�����󱻷�װ����һ��������ʱ���ܹ����ʱ���װ���������·��������֪�ģ���������������������ʵ������ȣ�
 * �����ڶԴ�����з���������յĶ���һ�����ܳ������Ǽȶ���������
 * �����������˵�����ͨ��������������ʹһ�����Ϊ�̰߳�ȫ����
 * @author yyl-pc
 *
 */
public class MyTest1 {
	private final Set<Person> mySet = new HashSet<Person>();
	
	public synchronized void addPerson(Person p) {
		mySet.add(p);
	}
	public synchronized boolean containPerson(Person p) {
		return mySet.contains(p);
	}
	public class Person{
		
	}
}

