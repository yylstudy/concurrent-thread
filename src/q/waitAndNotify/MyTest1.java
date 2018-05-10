
package q.waitAndNotify;

/**
 * ����obj.await()�������ͷ�obj������notifyֻ��֪ͨ�ȴ����еĵ�һ���̣߳�notifyAll��֪ͨ���еȴ��þ�����Դ���߳�
 * �����������߳�ִ���� obj.wait()��������ôobj.notifyAll()����ȫ������ thread1,thread2,thread3������Ҫ����ִ��obj.wait()
 * ����Ĵ��룬����Ҫ�Ȼ��������ˣ�thread1,thread2,thread3��Ҫ�Ȼ���������������У�
 * ���� ����obj.notify����notifyAll����ʱ��ǰ�̲߳������ͷ���������thread1,thread2,thread3����Ҫ�ȵ�����obj.notify����notifyAll
 * ���߳��Ƿ������ʱ�����ܼ�������ִ��
 * ��ʾ�������߳�ѭ����ӡAB
 * @author yyl-pc
 *
 */
public class MyTest1 {
	public static void main(String[] args) throws Exception {
		new MyThread("1").start();
		Thread.sleep(10);
		new MyThread1("1").start();
	}
	static class MyThread extends Thread{
		private String s;
		public MyThread(String s) {
			this.s = s;
		}
		@Override
		public void run() {
			for(int i=0;i<10;i++) {
				synchronized(s) {
					System.out.print("A");
					try {
						s.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
					s.notify();
				}
			}
		}
	}
	static class MyThread1 extends Thread{
		private String s;
		public MyThread1(String s) {
			this.s = s;
		}
		@Override
		public void run() {
			for(int i=0;i<10;i++) {
				synchronized(s) {
					s.notify();
					System.out.print("B");
					try {
						s.wait();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
