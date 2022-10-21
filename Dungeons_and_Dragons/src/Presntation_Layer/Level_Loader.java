package Presntation_Layer;
	
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

	import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

	
	public class Level_Loader {
	
		
		private File[] listoflevels;
		private String path;
		private int numberoflevels;
		public Level_Loader(String path)
		{
			  this.path=path;
			  this.loader();
			
		}
		
		public String getlevelinstring(int i)
		{
			
			return this.readFile(listoflevels[i]);
		}
		
		
		public int GetNumberoflevels()
		{
			return this.numberoflevels;
		}
		
		public void loader()
		{
			//https://stackoverflow.com/questions/42667697/how-to-move-all-the-files-from-one-path-to-other-except-with-txt-extention-in-j
			//this the code is taken from this link
			
			 File folder = new File(path);
		        File[] listOfFiles = folder.listFiles();
		        this.listoflevels=listOfFiles;
		        this.numberoflevels=this.listoflevels.length;
		        
			
			
			/*
				 File levelsDir = new File(path);
				this.listoflevels= levelsDir.listFiles((File pathname) -> !pathname.getName().endsWith(".txt"));
				*/
			}

		
		private String readFile(File levelFile)
		{
			
			//https://www.w3schools.com/java/java_files_read.asp
			//this the code is taken from this link
			String data = "";
			try {
				Scanner myReader = new Scanner(levelFile);
				while (myReader.hasNextLine()) {
					data += myReader.nextLine();
					data=data+"\n";
					//System.out.println(data);
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
			return data;
		}

		
		public char[][] encode_Level(String levelinsting)
		{
			
			
			String[] line = levelinsting.split("\n");
			char[][] levelinarray=new char[line.length][line[0].length()];
			
			for(int i=0;i<line.length;i=i+1)
			{
				for(int j=0;j<line[0].length();j=j+1)
				{
					levelinarray[i][j]=line[i].charAt(j);
				}
			}
			
		
			
			
			
			return levelinarray;
		}
	
	  
	
	
	

}
