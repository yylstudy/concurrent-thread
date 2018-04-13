package e.final1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 不可变对象都是线程安全的，不可变不等于把对象所有的与域都声明为final，即使对象所有的域都是final，这个对象也仍然是可变的
 * 因为在final域中可保存可变对象的引用，只有满足一下条件时，对象才是不可变的，
 * 1：对象创建后，其状态就不能修改
 * 2：对象的所有域都是final
 * 3：对象是正确创建的，（在对象创建期间，this引用没有逸出）
 * 
 * final修饰的变量时表示这个对象的hash值是不可变的
 * 
 * @author yyl-pc
 *
 *演示final修饰的变量时可改变的
 */
public class MyTest1 {
	public static void main(String[] args) {
		final Date date = new Date();
		System.out.println(date.hashCode());
		date.setMonth(7);
		System.out.println(date.hashCode());
		final Set set = new HashSet();
		System.out.println(set.hashCode());
		set.add("11");
		System.out.println(set.hashCode());
		
	}
}
