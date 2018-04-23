package h.synchronizedCollection;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 并发容器：ConcurrentHashMap代替HashTable和SynchronizedMap，其中的许多操作已是同步操作
 * @author yyl-pc
 *
 */
public class MyTest3 {
	public static void main(String[] args) {
		ConcurrentHashMap<String,String> concurrentHashMap = new ConcurrentHashMap<String,String>();
		concurrentHashMap.putIfAbsent("1", "2");//若不存在则添加
		concurrentHashMap.remove("1", "2");//当这个键值对存在时，才移除
	}
}
