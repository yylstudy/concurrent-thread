package g.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于监视器模式的车辆追踪，这里的获取车辆集合和车辆位置之所以需要使用clone，
 * 是因为想让location 和 location中的MutablePointer都为发布，即使我们通过getLocations获取车辆集合
 * 修改这个集合数据，也不影响原来的集合数据，因为clone了，所以指向不一样的对象，保证了这两个对象不被发布
 * 从而实现了线程安全
 * @author yyl-pc
 *
 */
public class MyTest1 {
	public class MonitorVehicleTracker{
		private final Map<String,MutablePointer> locations;
		public MonitorVehicleTracker(Map<String,MutablePointer> locations) {
			this.locations = deepCopy(locations);
		}
		public synchronized Map<String,MutablePointer> getLocations(){
			return deepCopy(locations);
		}
		
		public synchronized MutablePointer getLocation(String id) {
			MutablePointer p = (MutablePointer) locations.get(id).clone();
			return p;
		}
		public synchronized void setLocation(String id,int x,int y) {
			MutablePointer p = locations.get(id);
			p.x = x;
			p.y = y;
		}
		public Map<String,MutablePointer> deepCopy(Map<String,MutablePointer> map) {
			Map<String,MutablePointer> result = new HashMap<String,MutablePointer>();
			for(String key:map.keySet()) {
				result.put(key, (MutablePointer)(map.get(key).clone())); 
			}
			return Collections.unmodifiableMap(result);
		}
	}
	public class MutablePointer implements Cloneable{
		private int x;
		private int y;
		public Object clone() {
			try {
				return super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				throw new NullPointerException();
			}
		}
	}
}
