package logs;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class OurLogsSerializer {


	protected String filePath;
	final String logName = "LogFile";
	final String regex = " -(\\d{4}-\\d{2}-\\d{2}).log";
	private Date currentDate;
	private FileWriter out;
	private File folder;

	public OurLogsSerializer(String filePath) {
		
		this.filePath = filePath;
		this.folder = this.createOrReadLogFolder(this.filePath);
		// 0. guarda a data atual
		try {
			this.createNewFileStream(new Date());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public OurLogsSerializer() {

		this.filePath = System.getProperty("user.dir"); // TODO: get correct path
		// 1.0- validar se tem pasta de logs
		this.folder = this.createOrReadLogFolder(this.filePath);
		// 0. guarda a data atual
		try {
			this.createNewFileStream(new Date());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createNewFileStream(Date newDate) throws IOException {
		
		this.currentDate = newDate;
		
		if (this.out != null) {
		
			this.out.close();
		}
		
		// 1. validar se tem algum ficheiro para esse dia
		// 2. verificar se já existe um ficheiro para esse dia, se nçao existir cria-o
		File logFile = this.getOrCreateFileForDate(this.currentDate, this.folder);
		
		// 3. abrir o file descriptor para esse ficheiro
		this.out = this.openStream(logFile);
	}
	
	public void log(String message) {
		System.err.println("entrei no log");
		Date todaysDate = new Date();
		try {
			if (!this.isDateToday(this.currentDate, todaysDate)) {

				this.createNewFileStream(todaysDate);
			}

			System.err.println("Serialize: " +message);
			this.out.write(message + "\n");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private FileWriter openStream(File logFile) throws IOException {
		
		return new FileWriter(logFile, true);
	}
	
	private File createOrReadLogFolder(String filePath){
		//Determine if a logs directory exists or not.
		File logsFolder = new File(filePath + '/' + "logs");
		if(!logsFolder.exists()){
			//Create the directory 
			System.err.println("INFO: Creating new logs directory in " + filePath);
			logsFolder.mkdir();
		} 
		
		return logsFolder;
	}
	
	private File getOrCreateFileForDate(Date date, File logsFolder) {
		
		if(!logsFolder.isDirectory() ) {

			throw new IllegalStateException("Folder is not valid");
		}

		String logFileName = this.generatefileFormat(date);
		File logFile = new File(logsFolder.getName(), logFileName);
		
		try {
			logFile.createNewFile();
			return logFile;
			
		} catch (IOException e) {				
			return null;
		}
	}
	
	private String generatefileFormat(Date date) {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //Get the current date and time
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		//Create the name of the file from the path and current time
		String finalLogname =  logName + '-' +  dateFormat.format(cal.getTime()) + ".log";
		return finalLogname;
	}
	
	private boolean isDateToday(Date dateFromFile, Date currentDate) {

		Calendar today = Calendar.getInstance();
		today.clear(Calendar.HOUR); 
		today.clear(Calendar.MINUTE); 
		today.clear(Calendar.SECOND);
		Date todayDateReseted = today.getTime();
		
		Calendar dateFromFileCalendar = Calendar.getInstance();
		dateFromFileCalendar.setTime(currentDate);
		dateFromFileCalendar.clear(Calendar.HOUR); 
		dateFromFileCalendar.clear(Calendar.MINUTE); 
		dateFromFileCalendar.clear(Calendar.SECOND);
		dateFromFileCalendar.setTime(dateFromFile);
		
		Date dateFromFileReseted = dateFromFileCalendar.getTime();
		
		if(dateFromFileReseted.equals(todayDateReseted)) {
			return true;
		}	
		return false;
	}

}
