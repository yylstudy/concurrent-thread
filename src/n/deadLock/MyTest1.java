package n.deadLock;

/**
 * ������������ͬ�Ļ�ȡ��˳����ܵ�������
 * transferMoney���ܵ�����������Ϊ���Ƕ�̬����˳��
 * 
 * @author yyl-pc
 *
 */
public class MyTest1 {
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
