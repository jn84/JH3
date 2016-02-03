package graphing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JFrame;

class GBar
{
    String text;
    int value;
    GBar(String t, int v)
    {
        text=t;
        value =v;
    }
}

public class graph extends JFrame
{

    ArrayList<GBar> gbarArr = new ArrayList<GBar>();

    graph(ArrayList<GBar> garr)
    {
        super("Graph Beginnings");

        gbarArr = garr;
        setSize(600,600);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Find the maximum width of the strings in pixels
    int getMaxTextWidth(ArrayList<GBar> garr, FontMetrics fm)
    {
        int maxValue=0;
        for (int i=0; i < garr.size(); i++)
        {
            int width = fm.stringWidth(garr.get(i).text);
            if (width > maxValue)
                maxValue = width;
        }
        return maxValue;            
    }

    // Find the maximum value in the ArrayList
    int getMaxBarWidth(ArrayList<GBar> garr)
    {
        int maxValue=0;
        for (int i=0; i < garr.size(); i++)
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
        int top = insets.top;
        int left = insets.left;
        int right = insets.right;

        Font font = g.getFont();
        FontMetrics fm = getFontMetrics(font);
        int fontHeight = fm.getHeight();
        int maxAscent = fm.getMaxAscent();

        int strMaxWidth = left + getMaxTextWidth( gbarArr, fm);
        int x_bar_start =  strMaxWidth +1/* a little white space pad*/; 
        
        int barMaxValue = getMaxBarWidth(gbarArr);
        double scale = (dimen.width -x_bar_start - right) / (double) barMaxValue;
        
        int y_start = top;
        
        int bar_height = fontHeight;
        
        for (int i=0; i < gbarArr.size(); i++)
        {
            String text = gbarArr.get(i).text;
            int strWidth = fm.stringWidth(text);
            int value =gbarArr.get(i).value;
            int scaledValue = (int)(value * scale); 
            g.drawString(text, strMaxWidth - strWidth, y_start + maxAscent);
            g.fillRect(x_bar_start, y_start, scaledValue, bar_height);
            
            y_start += fontHeight + 10/*  a little space between rows */;
        }
        g.drawLine(strMaxWidth, top, strMaxWidth, dimen.height);
    }

    public static void main(String[] args) {
        ArrayList<GBar> garr = new ArrayList<GBar>();
        
        graphDataReader gdr = new graphDataReader("graphing.txt");
        
        for (int i = 0; i < gdr.GetNumberOfDataPoints(); i++)
        	garr.add(new GBar(gdr.GetName(i), gdr.GetScore(i)));
     
        graph gb = new graph(garr);

    }
}