package a.synchronized1;

/**
 * �������า�Ǹ��෽���󣬵������෽��ʱ�����ȡ�������
 * @author yyl-pc
 *
 */
public class MyTest2 {
	public static void main(String[] args) throws InterruptedException {
		final LoggingWidget lw = new LoggingWidget();
		Thread th1 = new Thread("th1") {  
            @Override  
            public void run() {  
                System.out.println(super.getName() + ":start\r\n");  
                lw.doSometing();  
            }  
        };  
        Thread th2 = new Thread("th2") {  
            @Override  
            public void run() {  
                System.out.println(super.getName() + ":start\r\n");  
                /** Ϊ��˵�����ิд���෽���󣬵���ʱҲ���и�������*/  
                lw.doAnother();  
                /**֤��������������Щû�м�synchronized���η��ķ����ǲ������õ�*/  
//                lw.doNother();  
                /**Ϊ��˵�����ิд���෽���󣬵���ʱҲ���и�������Ҳ�����Լ��������*/  
                // lw.doMyLike();  
                /**���������̣߳�������Ҫ�ȴ��ģ������Ǽ̳еĹ�ϵ���������룬�����Ƿ�����һ���߳��е�*/  
                //lw.doSometing();  
            }  
        }; 
        th1.start();  
        Thread.sleep(1000);  
        th2.start();
	}
}
class Widget {  
    public synchronized void doSometing() {  
        System.out.println("widget ... do something...");  
    }  
    public synchronized void doAnother() {  
        System.out.println("widget... do another thing...");  
    }  
    public void doNother() {  
        System.out.println("widget... do Nothing...");  
    }  
}  
class LoggingWidget extends Widget {  
    @Override  
    public synchronized void doSometing() {  
        try {  
            System.out.println("loggingwidget do something...");  
            Thread.sleep(5000);  
            System.out.println("end loggingwidget do something...");  
        } catch (InterruptedException e) {  
        }  
        super.doSometing();  
    }  
    public synchronized void doMyLike() {  
        System.out.println("loggingwidget do my like...");  
    }  
}  
