package d.threadLocal;

/**
 * 非线程安全
 * @author yyl-pc
 *
 */
public class MyTest1 {
	private static int s = 0;
	public static void main(String[] args) throws InterruptedException {
		new Thread() {
			@Override
			public void run() {
				for(int i=0;i<1000000;i++) {
					s++;
				}
				System.out.println(s);
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				for(int i=0;i<1000000;i++) {
					s++;
				}
				System.out.println(s);
			}
		}.start();
	}
}
