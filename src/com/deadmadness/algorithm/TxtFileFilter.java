package com.deadmadness.algorithm;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class TxtFileFilter extends FileFilter {
	private String fileFormat = "TXT";
	private char dot = '.';
	
	@Override
	public boolean accept(File f) {
		if(f.isDirectory()){
			return true;
		}
		if(extension(f).equalsIgnoreCase(fileFormat)){
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
