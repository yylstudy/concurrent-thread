package r.cas;

/**
 * �򵥵�CAS��Compare And Swap�����ȽϺͽ�����ʵ��:
 * cas�а�����������������Ҫ��д���ڴ�λ��V�����бȽϵĵ�ֵA����д�����ֵB��
 * ���ҽ���V��ֵ����Aʱ��cas�Ż�ͨ��ԭ�ӵķ�ʽ����V��ֵ������λ��V��ֵ�Ƿ����A����������Vԭ�е�ֵ
 * CAS�ĺ����ǣ�����ΪV��ֵӦ��ΪA��������ô��V��ֵ����ΪB�����򲻸��²�����V��ֵʵ���Ƕ���
 * 
 * ������̳߳���ʹ��CASͬʱ����ͬһ����ʱ��ֻ������һ���߳��ܸ��±�����ֵ���������̶߳���ʧ�ܣ�Ȼ��ʧ�ܵ��̲߳��ᱻ����
 * ���Ǳ���֪�ھ�����ʧ�ܣ��������ٴγ��ԣ�������������
 * compareAndSwap��get��compareAndSet����������������Ϊʲô˵CAS���ü���Ҳ��ʵ��ԭ�Ӳ�����ע��CAS�Ĳ��ü���
 * ָ���ǣ�ʹ�õĸ��ϲ�������ײ���滻�����û���Ҫ�����ģ�ʹ��CAS������ʵ��ԭ�Ӳ��������뿴MyTest2
 * @author yyl-pc
 *
 */
public class MyTest1 {
	static class SimulateCas{
		private int value;
		public synchronized int get() {
			return value;
		}
		
		public synchronized int compareAndSwap(int expectedValue,int newValue) {
			int oldValue = value;
			if(oldValue==expectedValue)
				value = newValue;
			return oldValue;
		}
		public synchronized boolean compareAndSet(int exectedValur,int newValue) {
			return compareAndSwap(exectedValur,newValue)==exectedValur;
		}
	}
}
