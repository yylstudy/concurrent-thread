package h.synchronizedCollection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ����������ConcurrentHashMap����HashTable��SynchronizedMap�����е�����������ͬ������
 * @author yyl-pc
 *
 */
public class MyTest3 {
	public static void main(String[] args) {
		ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<String,String>();
		concurrentHashMap.putIfAbsent("1", "2");//�������������
		concurrentHashMap.remove("1", "2");//�������ֵ�Դ���ʱ�����Ƴ�
	}
}
