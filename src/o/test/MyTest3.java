package o.test;

import java.util.concurrent.Semaphore;

/**
 * 基于信号量的有界缓存
 * @author yyl-pc
 *
 */
public class MyTest3 {
	static class BoundedBuffer<E>{
		private final Semaphore avaliableItem,avaliableSpaces;
		private final E[] items;
		private int putPosition = 0,takePosition = 0;
		public BoundedBuffer(int capacity) {
			avaliableItem = new Semaphore(0);
			avaliableSpaces = new Semaphore(capacity);
			items = (E[]) new Object[capacity];
		}
		public boolean isEmpty() {
			return avaliableItem.availablePermits()==0;//返回当前信号量当中可用的许可数
		}
		public boolean isFull() {
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
