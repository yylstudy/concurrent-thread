package g.concurrent;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ���ʹ��MyTest1�е�MutablePointer,�ͻ��ƻ���װ�ԣ���ΪgetLocations��ָ��һ���ɱ�״̬�����ã���������ò����̰߳�ȫ��
 * ����˵��ûʹ�õ�ͬ��������Ҳ���̰߳�ȫ�ģ���Ϊʹ����ConcurrentHashMap
 * @author yyl-pc
 *
 */
public class MyTest2 {
	public class DelegatingTracker{
		private final ConcurrentHashMap<String,Point> locations;
		private final Map<String,Point> unmodifyMap;
		public DelegatingTracker(Map<String,Point> map) {
			locations = new ConcurrentHashMap<String,Point>(map);
			unmodifyMap = Collections.unmodifiableMap(locations);
		}
		public Map getLocations() {
			return locations;
		}
		public Point getLocation(String id) {
			return locations.get(id);
		}
		public void setLocation(String id,int x,int y) {
			if(locations.replace(id, new Point(x,y))==null)
				throw new NullPointerException();
		}
	}
	
	public class Point{
		public final int x;
		public final int y;
		public Point(int x,int y) {
			this.x = x;
			this.y = y;
		}
	}
}
