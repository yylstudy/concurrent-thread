package h.synchronizedCollection;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ����������CopyOnWriteArrayList,���ڴ���ͬ��List��Vector������ĳЩ��������ṩ���õ����ܣ�
 * ���ҵ����ڼ䲻��Ҫ���������м������߸��ƣ�
 * @author yyl-pc
 *
 */

public class MyTest4 {
	public static void main(String[] args) {
		CopyOnWriteArrayList<String> co = new CopyOnWriteArrayList<String>();
		co.add("11");
		
	}
}
