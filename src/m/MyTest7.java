package m;

import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;

/**
 * 使用信号量Semaphore来控制任务的提交速率
 * @author yyl-pc
 *
 */
public class MyTest7 {
	public static class BoundedExecutor{
		private final Executor exec;
		private final Semaphore semaphore;
		public BoundedExecutor(Executor exec,int bound) {
			this.exec = exec;
			semaphore = new Semaphore(bound);
		}
		public void submitTask(final Runnable command) throws InterruptedException {
			semaphore.acquire();
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						command.run();
					}finally {
						semaphore.release();
					}
				}
			});
		}
	}
}
