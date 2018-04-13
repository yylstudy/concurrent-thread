package a.synchronized1;

import java.util.List;

/**
 * 
 * 锁是可重入的，如果锁不可重入，那么下面这段代码将发生死锁，
 * 并且子类在重写父类方法，在调用子类方法时，同时也会获得父类的方法锁
 * @author yyl-pc
 */
public class MyTest1 {
	public static void main(String[] args) throws Exception {
		LoggingWeight lw = new MyTest1().new LoggingWeight();
		//所以这里不但获得了LoggingWeight这个子类的锁，也获得了MyTest1父类的方法锁，若锁不可重入
		//那么在调用super.dosomething中调用父类的dosomething就会发生死锁
		lw.dosomething();
	}
	
	public synchronized void dosomething() {
		System.out.println("father do something...");
	}
	
	public class LoggingWeight extends  MyTest1{
		@Override
		public synchronized void dosomething() {
			System.out.println("son do something");
			super.dosomething();
		}
	}
}

