package i.interrupt;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Semaphore �ź�����ʹ�ã�Semaphore�й�����һ���������ɣ���ɵĳ�ʼ�����ɹ��캯����ָ����
 * ��ִ�в���ʱ�������Ȼ�ȡ��ɣ�ֻҪ���п��õ���ɣ�
 * ����ʹ�ú��ͷ���ɣ����û����ɣ���ôacquire��������ֱ������ɣ�release����������һ����ɸ��ź�����Ҳ�����ͷ���ɣ�
 * ��ʼֵΪ1��Semaphore�����������壬���߱���������ļ������壬
 * 
 * @author yyl-pc
 *
 */
public class MyTest5 {
	public static class BoundedHashSet<T> {
		private final Set<T> set;
		private final Semaphore semaphore;
		public BoundedHashSet(int bound) {
			set = Collections.synchronizedSet(new HashSet<T>());
			semaphore = new Semaphore(bound);
		}
		public boolean add(T t) throws Exception{
			semaphore.acquire();//��ȡһ�����
			boolean wasAdded = false;
			wasAdded = set.add(t);
			if(!wasAdded) {
				semaphore.release();//��û��������ͷ�һ����ɣ���Ӷ��ٸ�Ԫ�أ�semaphore�о��ж��ٸ����
			}
			return wasAdded;
		}
		public boolean remove(T t) {
			boolean wasRemove = set.remove(t);
			if(wasRemove) {
				semaphore.release();//ɾ���ɹ����ͷ����
			}
			return wasRemove;
		}
	}
}
