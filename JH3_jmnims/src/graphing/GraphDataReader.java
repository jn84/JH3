package graphing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

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
			temp = dataTokens.nextToken();
			names.add(temp);
			
			if (!dataTokens.hasMoreTokens())
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
		if (isError)
			return "";
		return names.get(n);
	}
	
	public int GetScore(int s)
	{
		if (isError)
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
