package o.test;

import java.util.Set;

/**
 * 锁分解
 * 如果一个锁需要保护多个相互独立的状态变量，那么可以将这个锁分为多个锁，并且每个锁只保护一个变量，
 * 从而提高可伸缩性，并降低每个锁被请求的频率，
 * @author yyl-pc
 *
 */
public class MyTest1 {
	//最粗的锁粒度
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
	
	//使用锁分解技术
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
