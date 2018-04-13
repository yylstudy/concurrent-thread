package c.publish;


/**
 * 这就是个this逸出的例子，当MyTest1发布EventListener时，也隐含发布了MyTest1实例本身（因为调用了dosomething方法）
 * 在构造器未完成时，使用this
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
