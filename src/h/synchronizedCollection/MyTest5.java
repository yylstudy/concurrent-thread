package h.synchronizedCollection;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ���������������ã�BlockingQueueʹ�ã�����ĳ��Ŀ¼��
 * �������������ṩһ���ʺ��̵߳ķ���������Ŀ¼����ֽ�Ϊ���򵥵������������indexFile���������Ҫ���е�ʱ��ܾã�
 * ��ѭ��Ŀ¼�Ͳ��������ļ��ֽ⿪���и��ߵĴ���ɶ��Ժ����ܣ������и�ȱ�㣺
 * �������߳���Զ�޷��˳�����������޷���ֹ�����潫������༼���������Щ����
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
