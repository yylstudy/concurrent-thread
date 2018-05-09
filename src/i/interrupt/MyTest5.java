package i.interrupt;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 信号量的使用：Semaphore中管理着一组虚拟的许可，许可的初始数量由构造函数来指定，
 * 在执行操作时可以首先获取许可（只要还有可用的许可）
 * 并在使用后释放许可，如果没有许可，那么acquire将阻塞，直到有许可，release方法将返回一个许可给信号量（也就是释放许可）
 * 初始值为1的Semaphore可用作互斥体，并具备不可重入的加锁语义，
 * 
 * @author yyl-pc
 *
 */
public class MyTest5 {
	public static class BoundedHashSet<T> {
		private final Set<T> set;
		private final Semaphore semaphore;
		public BoundedHashSet(int bound) {
			set = Collections.synchronizedSet(new HashSet<T>());
			semaphore = new Semaphore(bound);
		}
		public boolean add(T t) throws Exception{
			semaphore.acquire();//获取一个许可
			boolean wasAdded = false;
			wasAdded = set.add(t);
			if(!wasAdded) {
				semaphore.release();//若没有添加则，释放一个许可，添加多少个元素，semaphore中就有多少个许可
			}
			return wasAdded;
		}
		public boolean remove(T t) {
			boolean wasRemove = set.remove(t);
			if(wasRemove) {
				semaphore.release();//删除成功后释放许可
			}
			return wasRemove;
		}
	}
}
