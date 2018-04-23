package h.synchronizedCollection;

import java.util.Iterator;
import java.util.Vector;

import annotation.NotThreadSafe;
import annotation.ThreadSafe;

/**
 * Vector的迭代问题，以下程序可能抛出异常，但这并不意味着Vector就不是线程安全的类，只是迭代其实是一个复合操作，
 * 为了使迭代线程安全，可以对整个迭代加锁，然而这样会导致其它线程无法访问该Vector
 * 许多现代同步容器类也并没有消除容器迭代的问题，for each循环也一样，因为当初在设计这些容器的迭代器类时，并没有考虑
 * 并发修改的问题，并且若是在迭代容器过程中发生其它线程修改该容器，就会抛出一个ConcurrentModificationException,
 * concurrentHashMap不会抛出这种异常，已对迭代进行处理
 * 经过测试 使用for each和 iterator遍历数组时，若修改 集合，都会抛出ConcurrentModificationException
 * 而普通的for循环则不会，为了不抛出异常，那么需要对容器 迭代加锁，但是加锁会损耗大量的性能，
 * 如果不希望在容器迭代期间加锁，那么一种的替代方式就是克隆容器，在副本上进行迭代，由于副本被封闭在线程内，
 * 因此其他线程不会在迭代期间修改，这样就避免抛出ConcurrentModificationException，但是这种方式取决于容器的大小
 * 调用频率等等
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
