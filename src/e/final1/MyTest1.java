package e.final1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * ���ɱ�������̰߳�ȫ�ģ����ɱ䲻���ڰѶ������е���������Ϊfinal����ʹ�������е�����final���������Ҳ��Ȼ�ǿɱ��
 * ��Ϊ��final���пɱ���ɱ��������ã�ֻ������һ������ʱ��������ǲ��ɱ�ģ�
 * 1�����󴴽�����״̬�Ͳ����޸�
 * 2���������������final
 * 3����������ȷ�����ģ����ڶ��󴴽��ڼ䣬this����û���ݳ���
 * 
 * final���εı���ʱ��ʾ��������hashֵ�ǲ��ɱ��
 * 
 * @author yyl-pc
 *
 *��ʾfinal���εı���ʱ�ɸı��
 */
public class MyTest1 {
	public static void main(String[] args) {
		final Date date = new Date();
		System.out.println(date.hashCode());
		date.setMonth(7);
		System.out.println(date.hashCode());
		final Set set = new HashSet();
		System.out.println(set.hashCode());
		set.add("11");
		System.out.println(set.hashCode());
		
	}
}
