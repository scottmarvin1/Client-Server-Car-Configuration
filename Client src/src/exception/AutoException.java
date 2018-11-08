package exception;

import java.io.*;
import java.util.Calendar;

/**
 * AutoException class. Custom exception handler for errors
 * that may occur in building an auto object. Writes the 
 * error number and messages to file with a time stamp 
 * 
 * @version   release4 29 May 2018
 * @author    Gregory Scott Marvin
 */
public class AutoException extends Exception {
	
	private int errorno;
	private String errormsg;
	
	public AutoException() {
		super();
		printmyproblem();
		writeProblemToFile();
	}
	
	public AutoException(String errormsg) {
		super();
		this.errormsg = errormsg;
		printmyproblem();
		writeProblemToFile();
	}
	
	public AutoException(int errorno) {
		super();
		this.errorno = errorno;
		printmyproblem();
		writeProblemToFile();
	}
	
	public AutoException(int errorno, String errormsg) {
		super();
		this.errorno = errorno;
		this.errormsg = errormsg;
		printmyproblem();
		writeProblemToFile();
	}
	
	public int getErrorno() {
		return errorno;
	}
	
	public void setErrorno(int errorno) {
		this.errorno = errorno;
	}
	
	public String getErrormsg() {
		return errormsg;
	}
	
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	
	public void printmyproblem() {
		System.out.println("AutoException errorno=" + errorno + ", errormsg=" + errormsg); 
	}
	
	public Object fix(int errno) {
		Fix1to100 f1 = new Fix1to100();
		
		if (errno >= 1 && errno <= 100) {
			switch(errno) {
			case 1 : 
				String modelName = f1.fix1();
				return modelName;
				
			case 2 : 
				Float modelPrice = f1.fix2();
				return modelPrice;
			}
		}
		String str = "Fix not performed";
		return str;
	}
	
	public void writeProblemToFile() {
		try {
			File file = new File("errorlog.txt");
			FileWriter fw = new FileWriter(file, true);
			fw.write("Error: " + errorno + " -- " + errormsg + " : ");
			fw.write(Calendar.getInstance().getTime().toString() + "\n");
			fw.close();
		} 
		catch (IOException e) {
			System.out.println("Error -- " + e.toString());
		}
	}
	

}
