package r.cas;

/**
 * 简单的CAS（Compare And Swap）（比较和交换）实现:
 * cas中包括三个操作数，需要读写的内存位置V，进行比较的的值A，拟写入的新值B，
 * 当且仅当V的值等于A时，cas才会通过原子的方式更新V的值，无论位置V的值是否等于A，都将返回V原有的值
 * CAS的含义是：我认为V的值应该为A，若是那么将V的值更新为B，否则不更新并告诉V的值实际是多少
 * 
 * 当多个线程尝试使用CAS同时更新同一变量时，只有其中一个线程能更新变量的值，而其他线程都将失败，然而失败的线程不会被挂起
 * 而是被告知在竞争中失败，并可以再次尝试，大大增加灵活性
 * compareAndSwap、get、compareAndSet不都是用了锁了吗？为什么说CAS不用加锁也能实现原子操作，注意CAS的不用加锁
 * 指的是：使用的复合操作，最底层的替换操作好还是要加锁的，使用CAS不加锁实现原子操作具体请看MyTest2
 * @author yyl-pc
 *
 */
public class MyTest1 {
	static class SimulateCas{
		private int value;
		public synchronized int get() {
			return value;
		}
		
		public synchronized int compareAndSwap(int expectedValue,int newValue) {
			int oldValue = value;
			if(oldValue==expectedValue)
				value = newValue;
			return oldValue;
		}
		public synchronized boolean compareAndSet(int exectedValur,int newValue) {
			return compareAndSwap(exectedValur,newValue)==exectedValur;
		}
	}
}
