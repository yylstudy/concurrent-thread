package k.executor;

import java.util.ArrayList;
import java.util.List;

/**
 * 单线程处理页面渲染
 * 串行的页面渲染
 * 
 * @author yyl-pc
 *
 */
public abstract class MyTest4 {
	public void renderPage(CharSequence charSequence) {// 渲染页面方法
		renderText(charSequence);//渲染文本
		List<ImageData> imageData = new ArrayList<ImageData>();
		for(ImageInfo imageInfo:scanForImageInfo(charSequence)) {//根据字符获取图像地址
			imageData.add(imageInfo.downloadImage());//根据图像地址下载图片
		}
		for(ImageData im:imageData) {
			renderImage(im);//渲染图片
		}
	}
	interface ImageData {

	}
	interface ImageInfo {
		ImageData downloadImage();
	}

	public abstract void renderText(CharSequence charSequence);
	abstract List<ImageInfo> scanForImageInfo(CharSequence charSequence);
	abstract void renderImage(ImageData imageData);
}
