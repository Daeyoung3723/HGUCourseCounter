package edu.handong.analysise.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;


public class Utils {
	
	public static ArrayList<String> getLines(String file, boolean removeHeader){
		ArrayList<String> lines = new ArrayList<String>();
		
		String fileName = file;
		try
		{
			Scanner inputStream = new Scanner(new File(fileName));
			String line = null;
			// Skip the header line by reading and ignoring it if removeHeader is true
			if(removeHeader) {
				line = inputStream.nextLine();
			}
			
			// Read the rest of the file line by line
			while (inputStream.hasNextLine())
			{
				// Contains line
				line = inputStream.nextLine();
				
				lines.add(line);
			}
			inputStream.close( );
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot find file " + fileName);
		}
		return lines;	
	}

	public static void writeAFile(ArrayList<String> lines, String targetFileName) {




		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(
					new FileOutputStream(targetFileName));
			
			File theDir = new File(targetFileName);
			if (!theDir.exists()) theDir.mkdirs();

			outputStream.writeChars("StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester" + "\n");
			
			for(String line : lines) {
				outputStream.writeChars(line + "\n");
			}

			outputStream.close();
			
		} catch(FileNotFoundException e) {
			System.out.println("Problem opening the file " + targetFileName);
		} catch (IOException e) {
			System.out.println("Problem with output to file " + targetFileName);
		}


	}
}
