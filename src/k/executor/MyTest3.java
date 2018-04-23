package k.executor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 错误的timer行为,Timer类主要的问题是：timer在执行所有任务时，只会启动一个线程，那么若某个任务时间过长，
 * 就会破坏其他 TimerTask的精确性，
 * Timer另一个问题在于，如果TimerTask抛出一个未检查的异常，那么Timer将表现出很糟糕的行为，Timer不捕获异常，因此当
 * TimerTask抛出未检查异常时，将终止线程
 * @author yyl-pc
 */
public class MyTest3 {
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new ThrowTask(), 4000);
	} 
	static class ThrowTask extends TimerTask{
		@Override
		public void run() {
			System.out.println("-----------------");
		}
	}
}
