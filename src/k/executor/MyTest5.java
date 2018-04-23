package k.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 使用Future实现页面渲染器，由于渲染图像和渲染文本线程消耗时间可能完全不同，假如渲染图像耗时比渲染文本耗时长的多，
 * 那么下面这个写法提升的性能有限，因此可以更细粒度的分解渲染图像的任务
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
		Future<List<ImageData>> future = executor.submit(task);//让渲染图像的线程先启动，同时执行渲染文本的方法
		renderText();
		try {
			List<ImageData> target = future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
			//重新设置线程的中断状态
			Thread.currentThread().interrupt();
			future.cancel(true);//由于不需要结果，取消任务
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
