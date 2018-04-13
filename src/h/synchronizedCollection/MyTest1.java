package h.synchronizedCollection;

import java.util.Vector;
import java.util.concurrent.CyclicBarrier;

/**
 * Vector上可能导致混乱结果的复合操作
 * 使用CyclicBarrier这种栅栏的方式好像更容易测试出数组下标越界，这里只是测试多个线程同时调用
 * deleteLast会报错，同理多个线程同时调用getLast和deleteLast也都会报错，解决方法就是对
 * List这个对象加锁，注意不是对getLast和deleteLast进行加锁，参照g.concurrent.MyTest4类
 * public static Object getLast(Vector list) {
 * 		synchronized(list){
 * 			int lastIndex = list.size()-1;
			return list.get(lastIndex);
 * 		}
	}
	使用CyclicBarrier提高异常几率
 * @author yyl-pc
 */
public class MyTest1 {
	public static Object getLast(Vector list) {
		int lastIndex = list.size()-1;
		return list.get(lastIndex);
	}
	public static void deleteLast(Vector list) {
		int lastIndex = list.size()-1;
		list.remove(lastIndex);
	}
	public static void main(String[] args) throws Exception{
		Vector vector = new Vector();
		for(int i=0;i<10;i++)
			vector.add(i);
		CyclicBarrier cb = new CyclicBarrier(10);
		for(int i=0;i<9;i++) {
			new Thread() {
				@Override
				public void run() {
					try {
						cb.await();
					} catch (Exception e) {
						e.printStackTrace();
					}
					deleteLast(vector);
				}
			}.start();
		}
		cb.await();
		System.out.println(vector);
	}
}
