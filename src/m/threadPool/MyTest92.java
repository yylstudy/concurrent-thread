package m.threadPool;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 递归算法的并行化
 * @author yyl-pc
 *
 */
public class MyTest92 {
	public <T> void sequentialRecursive(List<Node<T>> nodes,final Collection<T> results) {
		ExecutorService pool = Executors.newCachedThreadPool();
		for(Node<T> node:nodes) {
			results.add(node.compute());
			if(node.getChildren()!=null&&!node.getChildren().isEmpty()) {
				parallelRecursive(pool,node.getChildren(),results);
			}
		}
		pool.shutdown();//等待结果
		System.out.println(results);
	}
	public<T> void parallelRecursive(final Executor exec,List<Node<T>> nodes, Collection<T> results) {
		for(Node<T> node:nodes) {
			exec.execute(new Runnable() {
				@Override
				public void run() {
					results.add(node.compute());
				}
			});
			if(node.getChildren()!=null&&!node.getChildren().isEmpty()) {
				parallelRecursive(exec,node.getChildren(),results);
			}
		}
	}
	static class Node<T> {
		private String name;
		private List<Node<T>> children;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		public List<Node<T>> getChildren() {
			return children;
		}
		public void setChildren(List<Node<T>> children) {
			this.children = children;
		}
		public T compute() {
			return null;
		}
	}
}
