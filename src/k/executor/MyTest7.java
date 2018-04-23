package k.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * 图像下载更优：将图像下载分为多个线程，每一个图像一个任务，交由线程池执行，将串行的图像下载分解为并行的图像下载 这将减少下载所有图像的时间
 * 使用CompletionService处理
 * @author yyl-pc
 *
 */
public class MyTest7 {
	private final ExecutorService executor;

	public MyTest7(ExecutorService executor) {
		this.executor = executor;
	}

	public void renderPage(CharSequence charSequence) {
		List<ImageInfo> info = scanForImageInfo();
		CompletionService<ImageData> completionService = 
				new ExecutorCompletionService<ImageData>(executor);//创建一个CompletionService处理
		for(ImageInfo ii:info) {
			completionService.submit(new Callable<ImageData>() {//为每个图像创建一个Callback
				@Override
				public ImageData call() throws Exception {
					return ii.downloadImage();
				}
			});
		}
		renderText();
		for(int t=0,n=info.size();t<n;t++) {
			try {
				Future<ImageData> future = completionService.take();
				ImageData imageData = future.get();
				renderImage(imageData);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	private void renderImage(ImageData imageData) {
	}
	private List<ImageInfo> scanForImageInfo() {
		return null;
	}
	private void renderText() {

	}
	interface ImageData {

	}
	interface ImageInfo {
		ImageData downloadImage();
	}
}
