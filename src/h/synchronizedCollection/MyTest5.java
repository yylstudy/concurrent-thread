package h.synchronizedCollection;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生产者消费者运用：BlockingQueue使用，搜索某个目录，
 * 生产者消费者提供一组适合线程的方法将搜索目录问题分解为更简单的组件，比如若indexFile这个方法需要运行的时间很久，
 * 将循环目录和操作具体文件分解开具有更高的代码可读性和性能，这里有个缺点：
 * 消费者线程永远无法退出，因而程序无法终止，后面将介绍许多技术来解决这些问题
 * @author yyl-pc ProducerConsumer
 *
 */
public class MyTest5 {
	public static class FileCrawler implements Runnable{
		private final BlockingQueue<File> fileQueue;
		private final FileFilter fileFilter;
		private final File root;
		public FileCrawler(BlockingQueue<File> fileQueue,final FileFilter fileFilter,File root) {
			this.fileQueue = fileQueue;
			this.fileFilter = new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory()||fileFilter.accept(pathname);
				}
			};
			this.root = root;
		}
		@Override
		public void run() {
			craw1(root);
		}
		private void craw1(File root) {
			File[] entries = root.listFiles(fileFilter);
			if(entries!=null) {
				for(File entry:entries) {
					if(entry.isDirectory()) {
						craw1(entry);
					}else if(!alreadyIndexed(entry)) {
						try {
							fileQueue.put(entry);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		private boolean alreadyIndexed(File file) {
			return false;
		}
	}
	public static class Indexer implements Runnable{
		private final BlockingQueue<File> queue;
		public Indexer(BlockingQueue<File> queue) {
			this.queue = queue;
		}
		@Override
		public void run() {
			while(true) {
				try {
					indexFile(queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		public void indexFile(File file) {
			System.out.println(file.getName());
		}
	}
	
	public static void main(String[] args) throws Exception{
		BlockingQueue<File> blockingQueue = new LinkedBlockingQueue<File>();
		final FileFilter fileFilter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return true;
			}
		};
		File file = new File("E:\\yyl");
		FileCrawler fc = new FileCrawler(blockingQueue,fileFilter,file);
		new Thread(fc).start();
		Indexer in = new Indexer(blockingQueue);
		new Thread(in).start();
	}
	
}
