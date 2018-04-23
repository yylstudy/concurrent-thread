package p.reenTrantLock;

/**
 * synchronized和ReentrantLock的选择：
 * 仅当内置锁（synchronized）不满足要求时，才使用ReentrantLock，因为synchronized比ReentrantLock简洁、编写更加安全
 * 但是有时候synchronized不能满足要求：可定时的、可轮询的、可中断的锁获取操作，否则应该优先使用synchronized
 * 
 * @author yyl-pc
 *
 */
public class MyTest4 {
	public static void main(String[] args) {
		
	}
}
