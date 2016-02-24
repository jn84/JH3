package graphing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.ArrayList;

import javax.naming.spi.DirectoryManager;
import javax.swing.JFrame;

class GBar
{
	String text;
	int value;

	GBar(String t, int v)
	{
		text = t;
		value = v;
	}
}

public class Graphing extends JFrame
{
	private final int BORDER_WIDTH = 10;
	private final int BUFFER_SPACE = 8;

	ArrayList<GBar> gbarArr = new ArrayList<GBar>();
	GraphDataReader gdr = null;

	Graphing(String dataFileName)
	{
		super();

		// Read the graph data
		gdr = new GraphDataReader(dataFileName);

		if (!gdr.IsError())
			for (int i = 0; i < gdr.GetNumberOfDataPoints(); i++)
				gbarArr.add(new GBar(gdr.GetName(i), gdr.GetScore(i)));

		setSize(600, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// Find the maximum width of the strings in pixels
	private int getMaxTextWidth(ArrayList<GBar> garr, FontMetrics fm)
	{
		int maxValue = 0;
		for (int i = 0; i < garr.size(); i++)
		{
			int width = fm.stringWidth(garr.get(i).text);
			if (width > maxValue)
				maxValue = width;
		}
		return maxValue;
	}

	// Find the maximum value in the ArrayList
	private int getMaxBarWidth(ArrayList<GBar> garr)
	{
		int maxValue = 0;
		for (int i = 0; i < garr.size(); i++)
		{
			int value = garr.get(i).value;
			if (value > maxValue)
				maxValue = value;
		}
		return maxValue;
	}

	public void paint(Graphics g)
	{
		super.paint(g);

		Dimension dimen = getSize();
		Insets insets = getInsets();
		int top = insets.top + BORDER_WIDTH;	// Add the border width here so that every 
		int left = insets.left + BORDER_WIDTH;	// other element is is on board with it
		int right = insets.right + BORDER_WIDTH;
		int bottom = insets.bottom + BORDER_WIDTH;

		Font font = g.getFont();
		FontMetrics fm = getFontMetrics(font);
	
		if (gdr != null && !gdr.IsError()) // If everything is good, draw the graph
		{
			int fontHeight = fm.getHeight();
			int maxAscent = fm.getMaxAscent();
			
			int strMaxWidth = left + getMaxTextWidth(gbarArr, fm);
			int x_bar_start = strMaxWidth + 1/* a little white space pad */;

			int barMaxValue = getMaxBarWidth(gbarArr);
			
			//  - BUFFER_SPACE adds some space between border and longest graph bar
			double scale = (dimen.width - x_bar_start - right - BUFFER_SPACE) / (double) barMaxValue;

			int y_start = top;

			int bar_height = fontHeight;

			this.setTitle(gdr.GetTitle());
			
			for (int i = 0; i < gbarArr.size(); i++) // draw the graph components
			{
				String text = gbarArr.get(i).text;
				int strWidth = fm.stringWidth(text);
				int value = gbarArr.get(i).value;
				int scaledValue = (int) (value * scale);
				g.setColor(Color.BLACK);
				g.drawString(text, strMaxWidth - strWidth, y_start + maxAscent);
				g.setColor(Color.GREEN);
				g.fillRect(x_bar_start, y_start, scaledValue, bar_height);

				y_start += fontHeight + 10/* a little space between rows */;
			}
			g.drawLine(strMaxWidth, top, strMaxWidth, dimen.height);

			g.setColor(Color.RED);
			g.fillRect(left - BORDER_WIDTH, top - BORDER_WIDTH, dimen.width, BORDER_WIDTH); // top
			g.fillRect(left - BORDER_WIDTH, top - BORDER_WIDTH, BORDER_WIDTH, dimen.height); // left
			g.fillRect(dimen.width - right, top - BORDER_WIDTH, BORDER_WIDTH, dimen.height); // right
			g.fillRect(left - BORDER_WIDTH, dimen.height - bottom, dimen.width, BORDER_WIDTH); // bottom
		}
		else // Some problem reading the file -> draw an error
		{
			font = font.deriveFont((float)24);
			fm = getFontMetrics(font);
			int errorTextAscent = fm.getMaxAscent();
			int errorTextHeight = fm.getHeight();
			int errorTextTopPos = top + errorTextAscent;
			g.setFont(font);

			this.setTitle("Error");
			g.drawString("An error occurred while loading the graph data.", left, errorTextTopPos);
			g.drawString("File inaccessible or improperly formatted.", left, errorTextTopPos + errorTextHeight);
		}
	}

	public static void main(String[] args)
	{
		// If no filename argument was passed, we still want to  
		// display the window and show an error
		Graphing graph = new Graphing(args.length >= 1 ? args[0] : "");
	}
}