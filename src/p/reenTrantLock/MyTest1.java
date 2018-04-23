package p.reenTrantLock;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * 通过tryLock来避免锁顺序死锁，这个就是比内置锁好的地方，
 * 内置锁很难实现带有时间限制的操作，并且内置锁不能中断获取锁的操作
 * @author yyl-pc
 *
 */
public class MyTest1 {
	private static Random rnd = new Random();
	static class Account {
		public Lock lock;
		private DollarAmount balance;
		public DollarAmount getBalance() {
			return balance;
		}
		public void setBalance(DollarAmount balance) {
			this.balance = balance;
		}
		public void debit(DollarAmount d) {
			balance = balance.subtract(d);
		}
		public void credit(DollarAmount d) {
			balance = balance.add(d);
		}
	}
	static class DollarAmount {
		public DollarAmount add(DollarAmount d) {
			return null;
		}
		public DollarAmount subtract(DollarAmount d) {
			return null;
		}
		public int compareTo(DollarAmount dollarAmount) {
			return 0;
		}
	}
	public boolean transferMoney(Account fromAccount,Account toAccount,DollarAmount amount,long timeout,TimeUnit unit) throws Exception{
		long fixedDelay = getFixedDelayComponentNanos(timeout, unit);	
		long randMod = getRandomDelayModulusNanos(timeout, unit);
		long stopTime = System.currentTimeMillis()+unit.toNanos(timeout);
		while(true) {
			if(fromAccount.lock.tryLock()) {
				try {
					if(toAccount.lock.tryLock()) {  
						try {
							if(fromAccount.getBalance().compareTo(amount)<0) {
								throw new NullPointerException();
							}else {
								fromAccount.debit(amount);
								toAccount.credit(amount);
								return true;
							}
						} finally {
							toAccount.lock.unlock();
						}
					}
				}finally {
					fromAccount.lock.unlock();
				}
			}
			if(System.currentTimeMillis()<stopTime)
				return false;
			NANOSECONDS.sleep(fixedDelay + rnd.nextLong() % randMod);
		}
	}
	private static final int DELAY_FIXED = 1;
	private static final int DELAY_RANDOM = 2;
	static long getFixedDelayComponentNanos(long timeout, TimeUnit unit) {
        return DELAY_FIXED;
    }

    static long getRandomDelayModulusNanos(long timeout, TimeUnit unit) {
        return DELAY_RANDOM;
    }
}
