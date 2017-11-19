package hot;

import logs.OurLog;
import logs.Level;

public class Device {

	boolean on = false;
	
	public void turnOn() {
		on = true;
	
	}

	public void turnOff() {
		on = false;
		
	}

	public boolean isOn() {
		OurLog.log("["+Level.INFO +"]" + "   " + "device" + "   " + on);
		return on;
		
	}

}
