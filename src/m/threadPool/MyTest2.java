package m.threadPool;

/**
 * 合理设置线程池的大小
 * 对于计算密集型任务，在拥有N个CPU处理器的系统上，当线程池的大小为N+1时，通常能实现最优的利用率，
 * 对于包含I/O操作或者其它阻塞操作的任务，由于线程不会一直执行，因此线程池的规模应该可以更大，要正确的设置线程池的大小，
 * 你必须估算出任务的等待时间与任务的计算时间的比值，这个比值不需要很精确：比值以后再说
 * 可通过Runtime来获取CPU的数目Runtime.getRuntime().availableProcessors()
 * @author yyl-pc
 *
 */
public class MyTest2 {
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());//计算当前计算机的CPU个数
		
	}
}
