package de.morpheus.chatbot.aiml.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Writes incoming messages into a CSV file. 
 * Directory, filename and separation sequence can be specified.
 * @author Blizzard Postapex
 *
 */
public class String_to_CSV_Writer implements DAOInterface<String> {
	
	protected File file;
	protected BufferedWriter bw;
	protected String valueSeparationSequence;
	protected int idCounter = 0;
	
	
	private final static String 
	defaultDirectory = System.getProperty("java.class.path").split(File.pathSeparator)[0],		
	defaultFileName = "Strings.csv",
	defaultSeparator = ";"
	;
	
	
	public String_to_CSV_Writer(String directoryPath, String fileName, String separator) {
		File temp = new File(directoryPath);
		String dir;
		if(temp.isDirectory()) {
			dir = directoryPath;
		} 
		else {
			System.out.println("Invalid directory: " + directoryPath + 
					"\nUsing default directory " + defaultDirectory);
			dir = defaultDirectory;
		}
		
		file = new File(dir + File.separatorChar + fileName);
		valueSeparationSequence = separator;
		
		initRemainder();
	}
	
	public String_to_CSV_Writer(String directoryPath, String fileName) {
		this(directoryPath, fileName, defaultSeparator);
	}
	
	public String_to_CSV_Writer(String directoryPath) {
		this(directoryPath, defaultFileName);
	}

	public String_to_CSV_Writer() {
		this(defaultDirectory);
	}
	
	protected void initRemainder() {
		try {
			file.createNewFile();
			bw = createWriter();
			idCounter = restoreAll().size();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public int store(String input) throws IOException, IllegalArgumentException {
		if(input.contains(valueSeparationSequence)) {
			throw new IllegalArgumentException("Message mustn't contain '" + valueSeparationSequence + "'!");
		}
		String str = input.replaceAll("\n", " ");
		int id = idCounter++;
		bw.write(String.valueOf(id));
		bw.write(valueSeparationSequence);
		bw.write(str);
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
				String[] components = line.split(valueSeparationSequence, 2);
				if(Integer.valueOf(components[0]) == id) {
					str = components[1];
					break;
				}
			}
		}
		
		return str;
	}

	@Override
	public List<String> restoreAll() throws IOException {
		List<String> result = new ArrayList<String>();
		
		try(BufferedReader br = createReader()) {
			String line;
			while((line = br.readLine()) != null) {
				result.add(line.split(valueSeparationSequence, 2)[1]);
			}
		}
		return result;
	}
	
	@Override
	public int getStoredCount() {
		return idCounter;
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
		return "String to CSV writer - File: " + file.getAbsolutePath();
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
