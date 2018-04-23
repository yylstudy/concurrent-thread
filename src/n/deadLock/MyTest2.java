package n.deadLock;

/**
 * 为了避免死锁。可以使动态的锁顺序，变成固定的，比如利用对象的hashCode
 * 这样无论是哪两个用户转账，都现货区hashCode大的值的用户的锁，再获取小的hashCode值的用户的锁，从而避免死锁
 * 极少数情况下，两个用户拥有同样的hash值，此时必须通过某种任意方法来决定锁的顺序，
 * 可以使用“加时赛”锁，在获取两个Account锁之前，先获取这个“加时赛”锁，
 * 
 * @author yyl-pc
 *
 */
public class MyTest2 {
	private static final Object tieLock = new Object();
	static class Account {
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

	public void transferMoney(Account fromAccount,Account toAccount,DollarAmount amount) throws Exception{
		if(fromAccount.hashCode()>toAccount.hashCode()) {
			synchronized(fromAccount) {
				synchronized(toAccount) {
					if(fromAccount.getBalance().compareTo(amount)<0) {
						throw new NullPointerException();
					}else {
						fromAccount.debit(amount);
						toAccount.credit(amount);
					}
				}
			}
		}else if(fromAccount.hashCode()<toAccount.hashCode()) {
			synchronized(toAccount) {
				synchronized(fromAccount) {
					if(fromAccount.getBalance().compareTo(amount)<0) {
						throw new NullPointerException();
					}else {
						fromAccount.debit(amount);
						toAccount.credit(amount);
					}
				}
			}
		}else {
			synchronized(tieLock) {
				synchronized(fromAccount) {
					synchronized(toAccount) {
						if(fromAccount.getBalance().compareTo(amount)<0) {
							throw new NullPointerException();
						}else {
							fromAccount.debit(amount);
							toAccount.credit(amount);
						}
					}
				}
			}
		}
	}
}
