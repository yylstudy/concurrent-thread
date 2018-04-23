package n.deadLock;

/**
 * Ϊ�˱�������������ʹ��̬����˳�򣬱�ɹ̶��ģ��������ö����hashCode
 * �����������������û�ת�ˣ����ֻ���hashCode���ֵ���û��������ٻ�ȡС��hashCodeֵ���û��������Ӷ���������
 * ����������£������û�ӵ��ͬ����hashֵ����ʱ����ͨ��ĳ�����ⷽ������������˳��
 * ����ʹ�á���ʱ���������ڻ�ȡ����Account��֮ǰ���Ȼ�ȡ�������ʱ��������
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
