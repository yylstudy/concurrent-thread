package k.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ʹ��Futureʵ��ҳ����Ⱦ����������Ⱦͼ�����Ⱦ�ı��߳�����ʱ�������ȫ��ͬ��������Ⱦͼ���ʱ����Ⱦ�ı���ʱ���Ķ࣬
 * ��ô�������д���������������ޣ���˿��Ը�ϸ���ȵķֽ���Ⱦͼ�������
 * @author yyl-pc
 *
 */
public  class MyTest5 {
	
	private final ExecutorService executor = Executors.newCachedThreadPool();
	public void renderPage(CharSequence charSequence) {
		final List<ImageInfo> list = new ArrayList<ImageInfo>();
		Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
			@Override
			public List<ImageData> call() throws Exception {
				List<ImageData> result = new ArrayList<ImageData>();
				for(ImageInfo imageInfo:list) {
					result.add(imageInfo.downloadImage());
				}
				return result;
			}
		};
		Future<List<ImageData>> future = executor.submit(task);//����Ⱦͼ����߳���������ͬʱִ����Ⱦ�ı��ķ���
		renderText();
		try {
			List<ImageData> target = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			//���������̵߳��ж�״̬
			Thread.currentThread().interrupt();
			future.cancel(true);//���ڲ���Ҫ�����ȡ������
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	private void renderText() {
		
	}
	interface ImageData {

	}
	interface ImageInfo {
		ImageData downloadImage();
	}
	
}
