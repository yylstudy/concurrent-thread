package o.test;

import java.util.Set;

/**
 * ���ֽ�
 * ���һ������Ҫ��������໥������״̬��������ô���Խ��������Ϊ�����������ÿ����ֻ����һ��������
 * �Ӷ���߿������ԣ�������ÿ�����������Ƶ�ʣ�
 * @author yyl-pc
 *
 */
public class MyTest1 {
	//��ֵ�������
	static class ServerStatus{
		public final Set<String> users;
		public final Set<String> queries;
		public ServerStatus(Set<String> users,Set<String> queries) {
			this.users = users;
			this.queries = queries;
		}
		public synchronized void addUser(String u) {
			users.add(u);
		}
		public synchronized void addQuery(String u) {
			queries.add(u);
		}
		public synchronized void removeUser(String u) {
			users.remove(u);
		}
		public synchronized void removeQuery(String q) {
			queries.remove(q);
		}
	}
	
	//ʹ�����ֽ⼼��
		static class ServerStatus2{
			public final Set<String> users;
			public final Set<String> queries;
			public ServerStatus2(Set<String> users,Set<String> queries) {
				this.users = users;
				this.queries = queries;
			}
			public void addUser(String u) {
				synchronized(users) {
					users.add(u);
				}
			}
			public void addQuery(String u) {
				synchronized(queries) {
					queries.add(u);
				}
			}
			public void removeUser(String u) {
				synchronized(users) {
					users.remove(u);
				}
			}
			public void removeQuery(String q) {
				synchronized(queries) {
					queries.remove(q);
				}
			}
		}
}
