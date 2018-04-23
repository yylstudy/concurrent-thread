package k.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * ͼ�����ظ��ţ���ͼ�����ط�Ϊ����̣߳�ÿһ��ͼ��һ�����񣬽����̳߳�ִ�У������е�ͼ�����طֽ�Ϊ���е�ͼ������ �⽫������������ͼ���ʱ��
 * ʹ��CompletionService����
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
				new ExecutorCompletionService<ImageData>(executor);//����һ��CompletionService����
		for(ImageInfo ii:info) {
			completionService.submit(new Callable<ImageData>() {//Ϊÿ��ͼ�񴴽�һ��Callback
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
