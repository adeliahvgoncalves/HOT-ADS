package logs;

import java.io.Serializable;

public class OurLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7268873210901108607L;
	private static OurLog instance;
	private  OurLogsSerializer serializer;
	
	public static OurLog getInstance(){

		if(instance == null) {
			instance = new OurLog();
		}
		return instance;
	}

	public OurLog() {
		
		if (instance != null){
			throw new IllegalStateException("Cannot instantiate a new singleton instance of log");
		}
		
		this.serializer = new OurLogsSerializer();
	}
	
	public void logMessage(String message) {
		
		this.serializer.log(message);
	}
	
	public static void log(String message) {
		OurLog.getInstance().logMessage(message);
		System.err.println(message);
	}
}