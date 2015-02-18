package de.morpheus.chatbot.brain.io.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import de.morpheus.chatbot.model.brain.ModelChatbotBrain;
import de.morpheus.chatbot.model.brain.ModelChatbotBrainContent;
import de.morpheus.chatbot.model.brain.ModelChatbotBrainTopic;

public class DataSourceDB implements DataSource
{


	@SuppressWarnings("finally")
	public Connection con()
	{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
		String host1 ="jdbc:oracle:thin:@VM-PROJ-852:1521:xe";
		String username ="Steve";
		String password = "sys";
		Connection con = null;
		try{
		con = DriverManager.getConnection( host1, username, password );
		return con;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			return con;
		}
	}
	
	@SuppressWarnings("finally")
	public ResultSet selectstatement(String s1)
	{
		Connection con=con();
		ResultSet result = null;
		try
		{	
			Statement stmt = con.createStatement();
			 result = stmt.executeQuery(s1);
			return result;
		}
		catch(SQLException e)
		{
			
		}
		finally
		{
			return result;
		}
		
	}
	
	@SuppressWarnings("finally")
	public int UpdateStatement(String s1)
	{
		Connection con=con();
		int result=0;
		try
		{	
			Statement stmt = con.createStatement();
			 result = stmt.executeUpdate(s1);
			return result;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			return result;
		}
	
		
		
	}
	
	
		public ModelChatbotBrain readAll() 
		{
			// daten aus Datenbank lesen
			//Model chatbot brain zur�ck geben, = Hashmap
			//Hashmap key= Kategorie; Value = Hashmap(key Topic, value Wert)
			

			ModelChatbotBrain returnValue = ModelChatbotBrain.getInstance();
			try {
				
			
				String Query1 = "SELECT distinct(Kategoriename)from brain join kategorie on brain.BRAINKATEGORIEID =kategorie.KATEGORIEID";
				ResultSet rsdistinct=selectstatement(Query1);
				
				
				ArrayList<String> kategorielist = new ArrayList<String>();
			
				while (rsdistinct.next())
				{
				System.out.println(rsdistinct.getString(1));
			kategorielist.add(rsdistinct.getString(1));
			
				}
				
					System.out.println(kategorielist.size()-1);
					for(int i = 0;i<= kategorielist.size()-1;i++)
					{
					
					String Query2="SELECT Kategoriename,braintopic,brainvalue from brain join kategorie on brain.BRAINKATEGORIEID =kategorie.KATEGORIEID where Kategoriename='"+kategorielist.get(i)+"'";				  
				    ResultSet rs = selectstatement(Query2);
				  
				    returnValue.clear();
				
				while ( rs.next() ) {
		              
				
					String key = rs.getString("braintopic");
		      		String value =rs.getString("brainvalue");
		      		String category =rs.getString("Kategoriename");
		            
		      		String content = new String();

		      		
		      		if(!returnValue.containsKey(category)){
						ModelChatbotBrainTopic innerArrayList = new ModelChatbotBrainTopic();
						returnValue.put(category, innerArrayList);
					}
					if(!returnValue.get(category).containsKey(key)){
						returnValue.get(category).put(key, new ModelChatbotBrainContent());
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
			
			
			
		
			Connection con;
			try{
							
			con=con();
			//f�r jede Kategorie schleife
			for(Entry<String, ModelChatbotBrainTopic> currentEntry : brainModel.entrySet()){
				String category = currentEntry.getKey();	
	
				//f�r jedes topic,value in der Kategorie
				
				System.out.println(category);
				
				//check if category existiert
				String existskategorie ="Select count(*) from Kategorie where Kategoriename ='"+category+"' ";
				ResultSet rsexistskategorie = selectstatement(existskategorie);
				int katexists=1;
				if(rsexistskategorie.next())
				{
					 katexists =rsexistskategorie.getInt(1);
					System.out.println(katexists);
				}
				if (katexists==0)
				{
					//anlegen der kategorie
					String InsertKategorie = "Insert into Kategorie(Kategoriename)values('"+category+"') ";
					int Ok = UpdateStatement(InsertKategorie);
				}
				
				
				String preselectString="Select Kategorieid from Kategorie where Kategoriename ='"+category+"'";
				ResultSet Preresult = selectstatement(preselectString);
				
				//schreiben der KategorieID in answerid
				Integer answerid;
				if (Preresult.next())
				{
				answerid= Preresult.getInt(1);
				System.out.println(answerid);
				}
				else
				{
					answerid=null;
					System.out.println("leer");
				}	
			
				
				
				
				for(Map.Entry<String, ModelChatbotBrainContent> currentModelCategory : currentEntry.getValue().entrySet()){
						for(String currentContent : currentModelCategory.getValue()){
							String topic = currentModelCategory.getKey();
							String value = currentContent;
							
							boolean multiple=brainModel.get(category).get(topic).isMultiple();
							Integer Intmultiple;
							if (multiple==false)
							{
								Intmultiple = 0;
							}
							else
							{
								Intmultiple =1;
							}
							
							//insert Kategorie
							String topicexists ="Select count(*) from Topic where topic ='"+topic+"'";
							ResultSet rstopicexists =selectstatement(topicexists);
							int rtopicexists=1;
							if(rstopicexists.next())
							{
								rtopicexists=rstopicexists.getInt(1);
							}
							if (rtopicexists==0)
							{
								String createtopic="insert into Topic (Topic,KategorieID,Multiple) values('"+topic+"','"+answerid+"','"+Intmultiple+"')";
								int ok= UpdateStatement(createtopic);
								System.out.println(ok);
							}
							
							
							//check if multiple true false
							String multiplecheck = "select multiple from topic where topic ='"+topic+"'";
							ResultSet rsmultiplecheck =selectstatement(multiplecheck);
							int multi=0; //bessre l�sung andre fragen
							
							if(rsmultiplecheck.next())
							{
								multi= rsmultiplecheck.getInt(1);
							}
							
							//check if topic + value already in der Datenbank ->
							String braincheck = "select count(*) from Brain where BrainTopic ='"+topic+"' and Brainvalue='"+value+"'";
							ResultSet rsbraincheck =selectstatement(braincheck);
							int braincount= 0; //bessere l�sung andre fragen
							if (rsbraincheck.next())
							{
							braincount= rsbraincheck.getInt(1);
								System.out.println(braincount);
							}
							//check if topic +  already in der Datenbank 
							String topiccheck ="select count(*)from brain where BrainTopic='"+topic+"'";
							ResultSet rstopiccheck =selectstatement(topiccheck);
							int topiccount=0;
							if(rstopiccheck.next())
							{
								topiccount=rstopiccheck.getInt(1);
							}
							
							//�berschreiben oder anlegen
							
							if(multi==1  )
							{
								//wenn topic+value nicht vorhanden == 0 dann insert
								if(braincount==0){
								String abfrage = "INSERT INTO BRAIN (Brainkategorieid, Braintopic, Brainvalue) VALUES ("+answerid+", '"+topic+"', '"+value+"')";
								
								int ok = UpdateStatement(abfrage);
								System.out.println(ok);
								}
							}
							
							
							else if (multi==0)
							{
								//wenn topic vorhanden, �beschreibe Wert
								if(topiccount>0)
								{
									//update
									String Updatevalue="Update Brain set Brainvalue ='"+value+"' where Braintopic='"+topic+"'";
									int ok = UpdateStatement(Updatevalue);
											
								}
								else if (topiccount==0)
								{
									String InsertValue="Insert Into Brain (Brainkategorieid, Braintopic, Brainvalue) VALUES ("+answerid+", '"+topic+"', '"+value+"')";
									int OK= UpdateStatement(InsertValue);
								}
								}
							
							
							
							
							
						}
					}

				
				}
			}
			catch(SQLException e) 
			{
				System.out.println(e);
			}
			}
		
		
	
	
	public void write(String category, ModelChatbotBrainTopic obj) 
	{
		
	}
	
	
	
	
}
