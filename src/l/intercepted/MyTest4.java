package l.intercepted;

/**
 * 中断策略：最合理的中断策略是某种形式的线程级或服务级取消操作，在检查到中断请求时，任务并不需要放弃所有操作，
 * 它可以推迟处理中断请求，并直到某个更合适的时刻，因此需要记住中断请求，并在完成当前任务后，抛出InterruptedEception
 * 作为中断响应，除了将	InterruptedException传递给调用者外，应该在捕获InterruptedException之后恢复状态，
 * Thread.currentThread.interrupt();
 * @author yyl-pc
 *
 */
public class MyTest4 {

}
