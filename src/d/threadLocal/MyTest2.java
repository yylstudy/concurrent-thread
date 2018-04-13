package d.threadLocal;

/**
 * 线程安全
 * @author yyl-pc
 *
 */
public class MyTest2 {
	private static ThreadLocal<Integer> s = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return 0;
		}
	};
	public static void main(String[] args) throws InterruptedException {
		new Thread() {
			@Override
			public void run() {
				for(int i=0;i<1000000;i++) {
					s.set(s.get()+1);
				}
				System.out.println(s.get());
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				for(int i=0;i<1000000;i++) {
					s.set(s.get()+1);
				}
				System.out.println(s.get());
			}
		}.start();
	}
}
