package com.deadmadness.algorithm;

import java.io.File;

/*
 * 
 * 
 * Class to set file filters for the JFileChooser 
 * to display to the user
 * @author deadmadness
 */

import javax.swing.filechooser.FileFilter;

public class TxtFileFilter extends FileFilter {
	private String txtFormat = "TXT";
	private char dot = '.';
	
	@Override
	public boolean accept(File f) {
		/*if(f.isDirectory()){
			return true;
		}*/
		if(extension(f).equalsIgnoreCase(txtFormat)){
			return true;
		}
		else{
			return false;
		}
		
	}

	@Override
	public String getDescription() {
		
		return "txt Formats Only";
	}
	
	public String extension(File f){
		String fileName = f.getName();
		int indexFile = fileName.lastIndexOf(dot);
		
		if(indexFile > 0 && indexFile < fileName.length()-1){
			return fileName.substring(indexFile+1);
		} 
		else {
			return "";
		}
	}

}
