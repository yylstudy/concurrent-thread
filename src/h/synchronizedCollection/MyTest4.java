package h.synchronizedCollection;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 并发容器：CopyOnWriteArrayList,用于代替同步List（Vector），在某些情况下能提供更好的性能，
 * 并且迭代期间不需要对容器进行加锁或者复制，
 * @author yyl-pc
 *
 */

public class MyTest4 {
	public static void main(String[] args) {
		CopyOnWriteArrayList<String> co = new CopyOnWriteArrayList<String>();
		co.add("11");
		
	}
}
