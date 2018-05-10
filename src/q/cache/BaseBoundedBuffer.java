package q.cache;
/**
 * 基于数组的循环缓存
 * @author yyl-pc
 *
 * @param <V>
 */
@SuppressWarnings("unchecked")
public abstract class BaseBoundedBuffer<V> {
	private final V[] buf;//存放元素的数组
	private int tail; //当前元素的下标索引，此时这个下标对应的元素是空的
	private int head; //第一个元素的下标索引
	private int count; //元素的个数

	protected BaseBoundedBuffer(int capacaty) {//初始化缓存的大小
		buf = (V[]) new Object[capacaty];
	}
	
	//当当前下标+1等于数组长度时，那么就把下一个元素的下标设置为首元素，重新赋值
	//这里就体现了循环
	protected synchronized final void doPut(V v) {
		buf[tail] = v;
		if(++tail==buf.length)
			tail = 0;
		++count;
	}
	//当当前下标+1等于数组长度时，说明已经取到这个数组的最后一个下标
	//这里就体现了循环
	protected synchronized final V doTake() {
		V v = buf[head];
		buf[head] = null;
		if(++head==buf.length)
			head = 0;
		count--;
		return v;
	}
	
	public synchronized final boolean isFull(){//元素个数是否等于数组的长度
		return count==buf.length;
	}
	public synchronized final boolean isEmpty(){//元素个数是不是等于0
		return count==0;
	}
}
