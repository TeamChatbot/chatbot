package de.morpheus.chatbot.aiml.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class Map_to_CSV_Writer implements MapDAOInterface {

	protected File file;
	protected BufferedWriter bw;
	protected String valueSeparationSequence;
	protected int idCounter = 0;
	
	
	private final static String 
	defaultDirectory = System.getProperty("java.class.path").split(File.pathSeparator)[0],		
	defaultFileName = "Map.csv",
	defaultSeparator = ";"
	;
	
	
	public Map_to_CSV_Writer(String directoryPath, String fileName, String separator) {
		File temp = new File(directoryPath);
		String dir;
		if(temp.isDirectory()) {
			dir = directoryPath;
		} 
		else {
			System.out.println("Invalid directory: " + directoryPath + 
					"\nUsing default directory" + defaultDirectory);
			dir = defaultDirectory;
		}
		
		file = new File(dir + File.separatorChar + fileName);
		valueSeparationSequence = separator;
		
		initRemainder();
	}
	
	public Map_to_CSV_Writer(String directoryPath, String fileName) {
		this(directoryPath, fileName, defaultSeparator);
	}
	
	public Map_to_CSV_Writer(String directoryPath) {
		this(directoryPath, defaultFileName);
	}

	public Map_to_CSV_Writer() {
		this(defaultDirectory);
	}
	
	protected void initRemainder() {
		try {
			file.createNewFile();
			bw = createWriter();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int store(String key, String value) throws IOException, IllegalArgumentException {
		if(key.contains(valueSeparationSequence)) {
			throw new IllegalArgumentException("Message mustn't contain '" + valueSeparationSequence + "'!");
		}
		deleteByKey(key);
		while(restoreByID(idCounter) != null) {
			idCounter++;
		}
		
		int id = idCounter++;
		bw.write(String.valueOf(id));
		bw.write(valueSeparationSequence);
		bw.write(key);
		bw.write(valueSeparationSequence);
		bw.write(value);
		bw.newLine();
		bw.flush();
		return id;
	}

	@Override
	public String restoreByID(int id) throws IOException {
		String str = null;
		try(BufferedReader br = createReader()) {
			String line;
			while((line = br.readLine())!=null) {
				String[] components = line.split(valueSeparationSequence, 3);
				if(Integer.valueOf(components[0]) == id) {
					str = components[2];
					break;
				}
			}
		}
		
		return str;
	}
	
	@Override
	public String restoreByKey(String key) throws Exception {
		String str = null;
		try(BufferedReader br = createReader()) {
			String line;
			while((line = br.readLine())!=null) {
				String[] components = line.split(valueSeparationSequence, 3);
				if(components[1].equals(key)) {
					str = components[2];
					break;
				}
			}
		}
		
		return str;
	}

	@Override
	public Map<String, String> restoreAll() throws IOException {
		Map<String, String> result = new HashMap<String, String>();
		
		try(BufferedReader br = createReader()) {
			String line;
			while((line = br.readLine()) != null) {
				String[] values = line.split(valueSeparationSequence, 3);
				result.put(values[1], values[2]);
			}
		}
		return result;
	}
	
	@Override
	public int getStoredCount() throws IOException {
		return restoreAll().size();
	}
	
	@Override
	public void deleteByKey(String key) throws IOException {
    	//Construct the new file that will later be renamed to the original filename.
      	File tempFile = new File(file.getAbsolutePath() + ".tmp");
      	
      	try(BufferedReader reader = createReader();      
      	    	PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {
      		String line = null;

        	//Read from the original file and write to the new
    	    //unless content matches data to be removed.
            while ((line = reader.readLine()) != null) {
            	String[] components = line.split(valueSeparationSequence);
            	if (!components[1].equals(key)) {
            		pw.println(line);
            		pw.flush();
            	}
            }
      	}
    	
    	bw.close();
    	 //Delete the original file
 	    if (!file.delete()) {
 	    	System.out.println("Could not delete file");
 	 	    return;
 	    }
 	    //Rename the new file to the filename the original file had.
 	    if (!tempFile.renameTo(file))
 	    	System.out.println("Could not rename file");
 	    bw = createWriter();
      }
	
	@Override
	public void clearStorage() throws IOException {
		bw.close();
		file.delete();
		file.createNewFile();
		bw = createWriter();
		idCounter = 0;
	}
	
	@Override
	public String toString() {
		return "Map to CSV writer - File: " + file.getAbsolutePath();
	}
	
	@Override
	protected void finalize() throws IOException{
		bw.close();
	}
	
	private BufferedReader createReader() throws FileNotFoundException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(file)));
	}
	
	private BufferedWriter createWriter() throws FileNotFoundException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
	}
    
}
