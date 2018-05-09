package o.test;

import java.util.concurrent.Semaphore;

/**
 * 基于信号量的有界缓存（在实际情况中，若要使用一个有界缓存，应该直接使用ArrayBlockingQueue或者LinkedBlockingQueue）
 * 而不是自己编写，这里只是自己实现一个阻塞队列
 * @author yyl-pc
 *
 */
public class MyTest3 {
	public static void main(String[] args) throws Exception {
		final BoundedBuffer<String> bb = new BoundedBuffer<String>(10);
		bb.take();
	}
	static class BoundedBuffer<E>{
		private final Semaphore avaliableItem;//表示可以从缓存删除的元素个数，初始值为0，因为（缓存的初始状态为空）
		private final Semaphore avaliableSpaces;//表示可以插入到缓存的元素个数，它的初始值为缓存的大小
		private final E[] items;
		private int putPosition = 0,takePosition = 0;
		public BoundedBuffer(int capacity) {
			avaliableItem = new Semaphore(0);
			avaliableSpaces = new Semaphore(capacity);
			items = (E[]) new Object[capacity];
		}
		public boolean isEmpty() {//从缓存删除的元素为0，表示这个缓存为空
			return avaliableItem.availablePermits()==0;//返回当前信号量当中可用的许可数
		}
		public boolean isFull() {//插入缓存的元素个数为0，表示这个缓存已经满了
			return avaliableSpaces.availablePermits()==0;
		}
		//插入元素就获取avaliableSpaces的一个许可，并释放avaliableItem的一个许可
		public void put(E e) throws Exception {
			avaliableSpaces.acquire();
			doInsert(e);
			avaliableItem.release();
		}
		public E take() throws Exception {
			avaliableItem.acquire();
			E item = doExtract();
			avaliableSpaces.release();
			return item;
		}
		private synchronized E doExtract() {
			int i = takePosition;
			E e = items[i];
			items[i] = null;
			takePosition = (++i==items.length)?0:i;
			return e;
		}
		private synchronized void doInsert(E e) {
			int i = putPosition;
			items[i] = e;
			putPosition = (++i==items.length)?0:i;
		}
	}
	
}
