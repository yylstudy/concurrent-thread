package m;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * �̳߳ر��Ͳ���
 * ���н���б������󣬱��Ͳ��Կ�ʼ�������ã�ThreadPoolExecutor�ı��Ͳ��Կ���ͨ��setRejectedExecutionHandler���޸ģ�jdk�ṩ
 * ���ֲ�ͬ��RejectedExecutionHandlerʵ�֣�ÿ��ʵ�ְ�����ͬ�ı��Ͳ��ԣ�
 * AbortPolicy��CallerRunsPolicy��DiscardPolicy��DiscardOldestPolicy
 * 
 * Abort����ֹ��:���������Ĭ�ϵı��Ͳ��ԣ������ύ���������ڶ��������޷����浽����ʱ����������discard�������Ի���������������
 * ��������ɵģ�discardOldestPolicy�������Խ���������һ����ִ�е�����Ȼ���������ύ�����񣬣��������������һ�����ȶ��У���ô
 * discardOldestPolicy���������������ȼ���ߵ����������ò�Ҫ��discardOldestPolicy�����ȼ�����һ��ʹ��
 * Caller-Runs ���������У��ò��ԼȲ�����������Ҳ�����׳��쳣�����ǽ�ĳЩ������˵������ߣ��Ӷ������������������
 * ���������̳߳ص�ĳ���߳�ִ�����ύ�����񣬶�����һ��������execute��submit���߳���ִ�и�����
 * ���³�����������߳�ִ�����ύ����������ִ��������ҪһЩʱ�䣬������߳���������һ��ʱ���ڲ����ύ�κ����񣬴Ӷ�ʹ
 * �������߳���ʱ��������������ִ�е�����
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


