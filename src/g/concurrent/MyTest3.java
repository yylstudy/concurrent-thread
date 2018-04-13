package g.concurrent;

import java.util.Vector;

/**
 * 拓展类加锁机制
 * 拓展java类库中线程安全的类的功能以满足自己的需要，如我要在List中执行一个“不存在就添加”的操作
 * MyVector这个类是线程安全的，虽然Vector的add操作是线程安全的，但是整个“不存在就添加”的操作不是原子操作，所以就不是线程安全的
 * 所以在方法上需要加上synchronized，这里加上synchronized能保证线程安全的原因是：
 * 因为MyVector putIfAbsent 方法上的锁和Vector的add、contain方法上的锁是同一个锁，想象下
 * 在执行putIfAbsent方法时，别的线程不能额外的执行Vector，所以达到了线程安全
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
