package f.confinement;

import java.util.HashSet;
import java.util.Set;

/**
 * 封装封闭就是当一个对象被封装到另一个对象中时，能够访问被封装对象的所有路径都是已知的，与对象可以由整个程序访问的情况相比，
 * 更易于对代码进行分析，被封闭的对象一定不能超过它们既定的作用域，
 * 下面这个例子说明如何通过封闭与加锁机制使一个类成为线程安全的类
 * @author yyl-pc
 *
 */
public class MyTest1 {
	private final Set<Person> mySet = new HashSet<Person>();
	
	public synchronized void addPerson(Person p) {
		mySet.add(p);
	}
	public synchronized boolean containPerson(Person p) {
		return mySet.contains(p);
	}
	public class Person{
		
	}
}

