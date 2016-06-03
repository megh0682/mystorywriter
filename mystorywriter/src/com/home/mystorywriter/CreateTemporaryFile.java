package com.home.mystorywriter;

import java.io.File;
import java.io.IOException;

public class CreateTemporaryFile {
	
	public static String forPdf(String filename){
		File temp = null;
		  try{
	    		
	     	   temp = File.createTempFile(filename, ".pdf"); 
	     		
	     	   System.out.println("Temp file : " + temp.getAbsolutePath());
	     		
	     	}catch(IOException e){
	     		
	     	   e.printStackTrace();
	     		
	     	}
		  
		return (temp.getAbsolutePath());
		
		
	}

}
