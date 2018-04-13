package g.concurrent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ���ڼ�����ģʽ�ĳ���׷�٣�����Ļ�ȡ�������Ϻͳ���λ��֮������Ҫʹ��clone��
 * ����Ϊ����location �� location�е�MutablePointer��Ϊ��������ʹ����ͨ��getLocations��ȡ��������
 * �޸�����������ݣ�Ҳ��Ӱ��ԭ���ļ������ݣ���Ϊclone�ˣ�����ָ��һ���Ķ��󣬱�֤�����������󲻱�����
 * �Ӷ�ʵ�����̰߳�ȫ
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
