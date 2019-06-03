package edu.handong.analysis;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVReader;

public class CSVRead {
	
	private String filename;
	public CSVRead() {}

	public List<String[]> readCsv(String fileName) {
		filename = fileName;
		List<String[]> data = new ArrayList<String[]>();
		
		try {
            CSVReader reader = new CSVReader(new FileReader(filename));
            
            String[] s =  reader.readNext();
            while ((s = reader.readNext()) != null) {
                data.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
        return data;
	}

}
