package k.executor;

import java.util.ArrayList;
import java.util.List;

/**
 * ���̴߳���ҳ����Ⱦ
 * ���е�ҳ����Ⱦ
 * 
 * @author yyl-pc
 *
 */
public abstract class MyTest4 {
	public void renderPage(CharSequence charSequence) {// ��Ⱦҳ�淽��
		renderText(charSequence);//��Ⱦ�ı�
		List<ImageData> imageData = new ArrayList<ImageData>();
		for(ImageInfo imageInfo:scanForImageInfo(charSequence)) {//�����ַ���ȡͼ���ַ
			imageData.add(imageInfo.downloadImage());//����ͼ���ַ����ͼƬ
		}
		for(ImageData im:imageData) {
			renderImage(im);//��ȾͼƬ
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
