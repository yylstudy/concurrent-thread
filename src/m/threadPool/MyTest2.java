package m.threadPool;

/**
 * ���������̳߳صĴ�С
 * ���ڼ����ܼ���������ӵ��N��CPU��������ϵͳ�ϣ����̳߳صĴ�СΪN+1ʱ��ͨ����ʵ�����ŵ������ʣ�
 * ���ڰ���I/O�������������������������������̲߳���һֱִ�У�����̳߳صĹ�ģӦ�ÿ��Ը���Ҫ��ȷ�������̳߳صĴ�С��
 * �������������ĵȴ�ʱ��������ļ���ʱ��ı�ֵ�������ֵ����Ҫ�ܾ�ȷ����ֵ�Ժ���˵
 * ��ͨ��Runtime����ȡCPU����ĿRuntime.getRuntime().availableProcessors()
 * @author yyl-pc
 *
 */
public class MyTest2 {
	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());//���㵱ǰ�������CPU����
		
	}
}
