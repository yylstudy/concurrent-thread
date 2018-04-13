package g.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 客户端加锁机制
 * 错误的锁同步（不是一个锁）演示，为什么这种方式不能达到线程安全呢，毕竟putIfAbsent方法已经加上了synchronized？
 * 原因为，synchronized是加在错误的锁上了，list说让也是线程安全的，但是list上的锁和putIfAbsent方法上的锁不是一个锁，所以无法
 * 实现同步的功能，
 * 这个测试结果为test，说明了putIfAbsent这个方法并没有和add方法同步，所以先执行了add方法，再执行了putIfAbsent
 * @author yyl-pc
 *
 */
public class MyTest4 {
	public static class ListHelper<E>{
		public List<E> list = Collections.synchronizedList(new ArrayList<E>());
		public synchronized boolean putIfAbsent(E e) {
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
