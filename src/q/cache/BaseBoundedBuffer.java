package q.cache;
/**
 * ���������ѭ������
 * @author yyl-pc
 *
 * @param <V>
 */
@SuppressWarnings("unchecked")
public abstract class BaseBoundedBuffer<V> {
	private final V[] buf;//���Ԫ�ص�����
	private int tail; //��ǰԪ�ص��±���������ʱ����±��Ӧ��Ԫ���ǿյ�
	private int head; //��һ��Ԫ�ص��±�����
	private int count; //Ԫ�صĸ���

	protected BaseBoundedBuffer(int capacaty) {//��ʼ������Ĵ�С
		buf = (V[]) new Object[capacaty];
	}
	
	//����ǰ�±�+1�������鳤��ʱ����ô�Ͱ���һ��Ԫ�ص��±�����Ϊ��Ԫ�أ����¸�ֵ
	//�����������ѭ��
	protected synchronized final void doPut(V v) {
		buf[tail] = v;
		if(++tail==buf.length)
			tail = 0;
		++count;
	}
	//����ǰ�±�+1�������鳤��ʱ��˵���Ѿ�ȡ�������������һ���±�
	//�����������ѭ��
	protected synchronized final V doTake() {
		V v = buf[head];
		buf[head] = null;
		if(++head==buf.length)
			head = 0;
		count--;
		return v;
	}
	
	public synchronized final boolean isFull(){//Ԫ�ظ����Ƿ��������ĳ���
		return count==buf.length;
	}
	public synchronized final boolean isEmpty(){//Ԫ�ظ����ǲ��ǵ���0
		return count==0;
	}
}
