package graphing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// Reads and stores the graph data from file at dataFileName
// If isError is true, there has been a failure somewhere and no data can be returned from the class

public class GraphDataReader 
{
	private boolean isError = false;
	private String graphTitle = "";
	private ArrayList<String> names = null;
	private ArrayList<Integer> scores = null; 
	
	public GraphDataReader(String dataFileName)
	{
		names = new ArrayList<String>();
		scores = new ArrayList<Integer>();
		File inputFile = new File(dataFileName);
		Scanner inputStream = null;		
		try
		{
			inputStream = new Scanner(inputFile);
		} 
		catch (FileNotFoundException e)	
		{
			isError = true;
			return;
		}
		
		isError = PopulateArrays(inputStream);
		inputStream.close();
	}
	
	
	// Returns TRUE if there is an error and the file was unreadable
	private boolean PopulateArrays(Scanner inStream)
	{
		StringTokenizer dataTokens = null;
		
		if (inStream.hasNextLine())
			graphTitle = inStream.nextLine();
		else
			return true;
		
		while (inStream.hasNextLine())
		{
			String temp = "";
			
			dataTokens = new StringTokenizer(inStream.nextLine(), ";");
			
			if (!dataTokens.hasMoreTokens())  // Encountered a blank line of input, try the next line
				continue;
			
			temp = dataTokens.nextToken();
			names.add(temp);
			
			if (!dataTokens.hasMoreTokens()) // Line is missing the score value or was improperly formatted. Call for error state
				return true;
			
			temp = dataTokens.nextToken();
			temp = temp.trim();
			
			try
			{
				scores.add(Integer.valueOf(temp));
			}
			catch (NumberFormatException e)
			{
				return true;
			}
		}
		return false;
	}
	
	// Getters to return sane values depending on whether or not 
	// the data file was successfully read
	public boolean IsError()
	{
		return isError;
	}

	public int GetNumberOfDataPoints()
	{
		if (isError)
			return -1;
		return names.size();
	}
	
	public String GetName(int n)
	{
		if (isError || n < 0 || n >= names.size())
			return "";
		return names.get(n);
	}
	
	public int GetScore(int s)
	{
		if (isError || s < 0 || s >= names.size())
			return -1;
		return scores.get(s);
	}
	
	public String GetTitle()
	{
		if (isError)
			return "";
		return graphTitle;
	}
}
