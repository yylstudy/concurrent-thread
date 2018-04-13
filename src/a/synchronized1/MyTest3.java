package a.synchronized1;

import java.math.BigInteger;

/**
 * �÷���ģ��һ��Servlet ��ͳ��������
 * ������ʹ��synchronized long���Ͳ�����ʵ�֣�����ʹ��AtomicLongԭ�ӵ�������û�б�Ҫ�ģ��������ʹ��ͬ���������ʵ���̰߳�ȫ
 * ��ô��ʹ��ԭ�ӵ������������ǲ������κ�����
 * @author yyl-pc
 *
 */
public class MyTest3{
	private BigInteger lastNumber;
	private BigInteger[] lastFactors;
	private long hits;
	private long cacheHits;
	
	public long getHits() {
		return cacheHits;
	}
	public double getCacheHitRatio() {
		return (double)cacheHits/hits;
	}
	public void service() {
		BigInteger i = new BigInteger("1");
		BigInteger[] factors = null;
		synchronized(this){
			hits++;
			if(i==lastNumber) {
				cacheHits++;
				factors = lastFactors.clone();
			}
		}
		if(factors==null) {
			factors = new BigInteger[] {i};
			synchronized(this) {
				lastNumber = i;
				lastFactors = factors.clone();
			}
		}
	}
	
}
