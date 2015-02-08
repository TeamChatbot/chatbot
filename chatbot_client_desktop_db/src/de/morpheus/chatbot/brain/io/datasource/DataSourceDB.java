package de.morpheus.chatbot.brain.io.datasource;

import de.morpheus.chatbot.model.brain.ModelChatbotBrain;
import de.morpheus.chatbot.model.brain.ModelChatbotBrainContent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.morpheus.chatbot.brain.io.file.brain.BufferedFileReader;
import de.morpheus.chatbot.interpreter.InterpreterBrain;
import de.morpheus.chatbot.model.brain.ModelChatbotBrain;
import de.morpheus.chatbot.model.brain.ModelChatbotBrainContent;

public class DataSourceDB implements DataSource
{


	
		public ModelChatbotBrain readAll() 
		{
			// daten aus Datenbank lesen
			//Model chatbot brain zurück geben, = Hashmap
			//Hashmap key= Kategorie; Value = Hashmap(key Topic, value Wert)
			
			String host1 ="jdbc:oracle:thin:@McDaulyChillung:1521:xe";
			String username ="Steve";
			String password = "sys";

			Connection con;
			ModelChatbotBrain returnValue = ModelChatbotBrain.getInstance();
			try {
				con = DriverManager.getConnection( host1, username, password );
				
				System.out.println("yo");
				
				Statement stmt = con.createStatement();
				ResultSet rsdistinct = stmt.executeQuery("SELECT distinct(Kategoriename)from brain join kategorie on brain.BRAINKATEGORIEID =kategorie.KATEGORIEID");
				ArrayList<String> kategorielist = new ArrayList<String>();
			
				while (rsdistinct.next())
				{
				System.out.println(rsdistinct.getString(1));
			kategorielist.add(rsdistinct.getString(1));
				}
				
					System.out.println(kategorielist.size()-1);
					for(int i = 0;i<= kategorielist.size()-1;i++)
					{
					
					String Query1="SELECT Kategoriename,braintopic,brainvalue from brain join kategorie on brain.BRAINKATEGORIEID =kategorie.KATEGORIEID where Kategoriename='"+kategorielist.get(i)+"'";
				    System.out.println(Query1);
				    ResultSet rs = stmt.executeQuery(Query1);
				    returnValue.clear();
				
				while ( rs.next() ) {
		              
				
					String key = rs.getString("braintopic");
		      		String value =rs.getString("brainvalue");
		      		String category =rs.getString("Kategoriename");
		            
		      		String content = new String();

		      		
		      		if(!returnValue.containsKey(category)){
						ModelChatbotBrainContent innerArrayList = new ModelChatbotBrainContent();
						returnValue.put(category, innerArrayList);
					}
					if(!returnValue.get(category).containsKey(key)){
						returnValue.get(category).put(key, new ArrayList<String>());
					}
					content = value;
					returnValue.get(category).get(key).add(content);
				
		      		 
				} 
				ModelChatbotBrain.getInstance().putAll(returnValue);
				
				
			
			
				}
			}
				  catch (SQLException e) 
				  {			
				e.printStackTrace();
				  }
			


		 return ModelChatbotBrain.getInstance();

		}
	
	
		public void writeAll(ModelChatbotBrain brainModel) {
			
			
			
			String host1 ="jdbc:oracle:thin:@McDaulyChillung:1521:xe";
			String username ="Steve";
			String password = "sys";
			Connection con;
			try{
			
				
			con = DriverManager.getConnection( host1, username, password );
					
			
			
			//DB-Verbindung hier -------
			//schreibt kategorie->topic-> value  in eine file .. jede Kateogorie ein file
			
			
			for(Entry<String, ModelChatbotBrainContent> currentEntry : brainModel.entrySet()){
				String category = currentEntry.getKey();	
			
				for(Map.Entry<String, List<String>> currentModelCategory : currentEntry.getValue().entrySet()){
						for(String currentContent : currentModelCategory.getValue()){
							String topic = currentModelCategory.getKey();
							String value = currentContent;
							String abfrage = "Insert Into Brain(Brainkategorieid,Braintopic,Brainvalue)values((Select Kategorieid from Kategorie where Kategoriename ='"+category+"' ),'"+topic+"','"+value+"') ";
							System.out.println(abfrage);
							Statement stmt = con.createStatement();
							ResultSet rsdistinct = stmt.executeQuery("abfrage");
							
							//-------SQL STatement----------
							
						}
					}

				
				}
			}
			catch(SQLException e) 
			{
				
			}
			}
		
		
	
	
	public void write(String category, ModelChatbotBrainContent obj) 
	{
		
	}
	
	
	
	
}
