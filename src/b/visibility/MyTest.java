package b.visibility;

/**
 * 测试在没有同步的情况下共享变量，会发生错误书上是这么说的
 * 但是在测试中几乎不会出现书上描述的场景
 * 书中描述可能出现最奇怪的现象是：可能回输出0，因为ReaderThread可能看到写入ready的值，但是没有看到之后写入number的值
 * 这种情况是“重排序”，只要某个线程无法检测到重排序情况，那么就无法保证线程的操作是按照程序中指定的顺序来执行，
 * 就如下：主线程首先写入number，然后在没有同步的情况下写入ready,那么读线程看到的操作顺序可能正好相反，这种情况就是指令重排
 * 当线程在没有同步的情况下读取变量，可能会得到一个失效值，但至少这个值是由之前的某个线程设置的值，而不是一个随机值，这种安全性保证
 * 叫做最低安全性，最低安全性适用于绝大多数变量，但是存在一个例外：非volatile类型的64位数值变量（double、long）
 * java内存模型要求，变量的读取和写入操作必须是原子操作，但对于非volatile类型的long和double变量，jvm允许将64位的读操作或写操作
 * 分解为两个32位的操作，当读取一个非volatile类型的long变量，那么就会读取一个不安全的变量，除非使用volatile变量来声明他们，
 * 或者用锁保护起来，volatile的作用是：
 * 当把变量使用volatile声明时，编译器与运行时都会注意到这个变量是共享的，因此不会将该变量的操作与其它内存操作一起重排序
 * volatile变量不会被缓存在寄存器或者对其它处理器不可见的地方，因此在读取volatile变量时，总是返回最新写入的值
 * 
 * 当且仅当满足一下所有条件时，才应该使用volatile变量:
	1）：对变量的写入操作不依赖变量的当前值（因为volatile不保证线程的互斥性）,才应该使用volatile
	2)：该变量不会与其他状态变量一起纳入不变性条件中
	3）：在访问变量时不需要加锁
 * @author yyl-pc
 *
 */
public class MyTest {
	private  static boolean ready;
	private  static long number;
	private static class ReaderThread extends Thread{
		public void run() {
			while(!ready) {
				Thread.yield();
				System.out.println(number);
			}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		new ReaderThread().start();
		number = 42;
		new Thread() {
			public void run() {
				ready = true;
			};
		}.start();
	}
}
