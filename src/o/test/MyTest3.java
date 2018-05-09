package o.test;

import java.util.concurrent.Semaphore;

/**
 * �����ź������н绺�棨��ʵ������У���Ҫʹ��һ���н绺�棬Ӧ��ֱ��ʹ��ArrayBlockingQueue����LinkedBlockingQueue��
 * �������Լ���д������ֻ���Լ�ʵ��һ����������
 * @author yyl-pc
 *
 */
public class MyTest3 {
	public static void main(String[] args) throws Exception {
		final BoundedBuffer<String> bb = new BoundedBuffer<String>(10);
		bb.take();
	}
	static class BoundedBuffer<E>{
		private final Semaphore avaliableItem;//��ʾ���Դӻ���ɾ����Ԫ�ظ�������ʼֵΪ0����Ϊ������ĳ�ʼ״̬Ϊ�գ�
		private final Semaphore avaliableSpaces;//��ʾ���Բ��뵽�����Ԫ�ظ��������ĳ�ʼֵΪ����Ĵ�С
		private final E[] items;
		private int putPosition = 0,takePosition = 0;
		public BoundedBuffer(int capacity) {
			avaliableItem = new Semaphore(0);
			avaliableSpaces = new Semaphore(capacity);
			items = (E[]) new Object[capacity];
		}
		public boolean isEmpty() {//�ӻ���ɾ����Ԫ��Ϊ0����ʾ�������Ϊ��
			return avaliableItem.availablePermits()==0;//���ص�ǰ�ź������п��õ������
		}
		public boolean isFull() {//���뻺���Ԫ�ظ���Ϊ0����ʾ��������Ѿ�����
			return avaliableSpaces.availablePermits()==0;
		}
		//����Ԫ�ؾͻ�ȡavaliableSpaces��һ����ɣ����ͷ�avaliableItem��һ�����
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
