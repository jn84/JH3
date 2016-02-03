package graphing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class graphDataReader
{
	ArrayList<String> names = null;
	ArrayList<Integer> scores = null; 
	File inputFile = null;
	Scanner inputStream = null;
	
	public graphDataReader(String dataFileName)
	{
		names = new ArrayList<String>();
		scores = new ArrayList<Integer>();
		inputFile = new File(dataFileName);
		
		try
		{
			inputStream = new Scanner(inputFile);
		} 
		catch (FileNotFoundException e)	{ return; } // Nothing left to do. File is empty.
		
		
	}

}
