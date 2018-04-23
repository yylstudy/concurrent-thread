package i.interrupt;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask的使用，FutureTask可以表示一些计算较长的计算，这些计算可以在使用计算结果之前启动，
 * 如下所示，使用Future可以节省3秒钟
 * @author yyl-pc
 */
public class MyTest4 {
	public static class Preloader{
		private final FutureTask<ProductInfo> future = new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
			@Override
			public ProductInfo call() throws Exception {
				Thread.sleep(3000);
				return 	loadProductInfo();
			}
			private ProductInfo loadProductInfo() {
				return new ProductInfo();
			}
		});
		private final Thread thread = new Thread(future);
		public void start() throws ExecutionException {
			thread.start();
			try {
				Thread.sleep(3000);
				System.out.println(get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		public ProductInfo get() throws InterruptedException, ExecutionException {
			return future.get();
		}
		public static void main(String[] args) throws ExecutionException {
			long t1 = System.currentTimeMillis();
			Preloader p = new Preloader();
			p.start();
			long t2 = System.currentTimeMillis();
			System.out.println(t2-t1);
		}
		
	}
	public static class ProductInfo{
		private int i;

		@Override
		public String toString() {
			return "ProductInfo [i=" + i + "]";
		}
		
	}
}
