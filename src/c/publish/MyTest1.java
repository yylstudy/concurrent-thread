package c.publish;


/**
 * ����Ǹ�this�ݳ������ӣ���MyTest1����EventListenerʱ��Ҳ����������MyTest1ʵ��������Ϊ������dosomething������
 * �ڹ�����δ���ʱ��ʹ��this
 * @author yyl-pc
 *
 */
public class MyTest1 {
	public MyTest1(EventSource source) {
		source.registerListener(new EventListener() {
			@Override
			public void onEvent(Event e) {
				MyTest1.this.dosomething(e);
			}
		});
	}
	public void dosomething(Event e) {
		
	}
	
	interface EventSource {
        void registerListener(EventListener e);
    }
	interface EventListener {
        void onEvent(Event e);
    }
	interface Event {
    }
}
