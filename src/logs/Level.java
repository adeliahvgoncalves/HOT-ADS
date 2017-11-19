package logs;

/**
 * Enumeration for log level
 * 
 * @author adeliahvgoncalves
 *
 */
public enum Level {
	DEBUG(1),
	WARNING(2), 
	SEVERE(3), 
	INFO(4), 
	ERROR(5);
	 
	private int level;
 
	
	 
	private Level(int level){
		this.level = level;
	}
	public int getLevel(){
		return level;
	}
}

