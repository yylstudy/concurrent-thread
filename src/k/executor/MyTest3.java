package k.executor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * �����timer��Ϊ,Timer����Ҫ�������ǣ�timer��ִ����������ʱ��ֻ������һ���̣߳���ô��ĳ������ʱ�������
 * �ͻ��ƻ����� TimerTask�ľ�ȷ�ԣ�
 * Timer��һ���������ڣ����TimerTask�׳�һ��δ�����쳣����ôTimer�����ֳ���������Ϊ��Timer�������쳣����˵�
 * TimerTask�׳�δ����쳣ʱ������ֹ�߳�
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
