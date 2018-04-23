package n.deadLock;

import java.util.HashSet;
import java.util.Set;

/**
 * ���ص�Э������֮�������
 * setLocation ��Ҫ��ȡTaxi�������ٻ�ȡDispatcher����
 * getImage ��Ҫ���Dispatcher�������ٻ�ȡTaxi����
 * �������п����������
 * @author yyl-pc
 *
 */
public class MyTest3 {
	static class Taxi{
		private Dispatcher dispatcher;
		private Point location, destination;
		public synchronized Point getLocation() {
            return location;
        }
        public synchronized void setLocation(Point location) {
            this.location = location;
            if (location.equals(destination))
                dispatcher.notifyAvailable(this);
        }
        public synchronized Point getDestination() {
            return destination;
        }
        public synchronized void setDestination(Point destination) {
            this.destination = destination;
        }
	}
	static class Dispatcher {
		private final Set<Taxi> taxis;
		private final Set<Taxi> availableTaxis;
		public Dispatcher() {
			taxis = new HashSet<Taxi>();
			availableTaxis = new HashSet<Taxi>();
		}
		public synchronized void notifyAvailable(Taxi taxi) {
			availableTaxis.add(taxi);
		}
		public synchronized Image getImage() {
			Image image = new Image();
			for(Taxi t:taxis) {
				image.drawMarker(t.getLocation());
			}
			return image;
		}
	}
	static class Image {
        public void drawMarker(Point p) {
        }
    }
	public class Point {
	    public final int x, y;
	    public Point(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
	}
}
