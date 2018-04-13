package g.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import g.concurrent.MyTest4.ListHelper;

/**
 * 客户端加锁机制
 * 正确的锁同步，list上的锁是方法锁，也就是list对象本身的锁，若我们把putIfAbsent方法的锁加在list上，就能实现
 * 正确的线程安全，通过增加一个原子操作来拓展类是脆弱的，因为它将类的加锁代码，分布到多个类中，
 * 
 * 这个测试结果为输出 test,test 这说明putIfAbsent和add方法同步，add方法会等putIfAbsent方法执行完再执行
 * @author yyl-pc
 *
 */
public class MyTest5 {
	public static class ListHelper<E>{
		public Vector<E> list =new Vector<E>();
		public boolean putIfAbsent(E e) {
			synchronized(list) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if(!list.contains(e)) {
					list.add(e);
					return true;
				}
				return false;
			}
		}
		@Override
		public String toString() {
			return list.toString();
		}
	}
	
	public static void main(String[] args) throws Exception{
		ListHelper<String> lh = new ListHelper<String>();
		new Thread() {
			@Override
			public void run() {
				lh.putIfAbsent("test");
			}
		}.start();
		Thread.sleep(100);
		new Thread() {
			@Override
			public void run() {
				lh.list.add("test");
			}
		}.start();
		Thread.sleep(2000);
		System.out.println(lh);
	}
}
