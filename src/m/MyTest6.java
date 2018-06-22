package m;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池饱和策略
 * 当有界队列被填满后，饱和策略开始发挥作用，ThreadPoolExecutor的饱和策略可以通过setRejectedExecutionHandler来修改，jdk提供
 * 几种不同的RejectedExecutionHandler实现，每种实现包含不同的饱和策略：
 * AbortPolicy、CallerRunsPolicy、DiscardPolicy和DiscardOldestPolicy
 * 
 * Abort（中止）:这个策略是默认的饱和策略，当新提交的任务由于队列已满无法保存到队列时，“抛弃（discard）”策略会悄悄抛弃该任务
 * “抛弃最旧的（discardOldestPolicy）”策略将会抛弃下一个被执行的任务，然后尝试重新提交新任务，（如果工作队列是一个优先队列，那么
 * discardOldestPolicy）将导致抛弃优先级最高的任务，因此最好不要将discardOldestPolicy和优先级队列一起使用
 * Caller-Runs 调用者运行：该策略既不会抛弃任务，也不会抛出异常，而是将某些任务回退到调用者，从而降低新任务的流量，
 * 他不会在线程池的某个线程执行新提交的任务，而是在一个调用了execute、submit的线程中执行该任务，
 * 如下程序就是在主线程执行新提交的任务，由于执行任务需要一些时间，因此主线程至少在这一段时间内不能提交任何任务，从而使
 * 工作者线程有时间来处理完正在执行的任务，
 * 
 * @author yyl-pc
 *
 */
public class MyTest6 {
	public static void main(String[] args) {
		ThreadPoolExecutor pool = new 
				ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(10));
		pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	}
}


