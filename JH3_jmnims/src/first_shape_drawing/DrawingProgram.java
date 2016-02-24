// Handles all input and passes it to the neccessary controller

package first_shape_drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

import javax.swing.JFrame;

public class DrawingProgram extends JFrame implements DrawingToolbarListener
{

	private final Dimension initDimen = new Dimension(800, 400);
	private final int STATUSBAR_HEIGHT = 20;
	
    Drawing drawing = new Drawing();
    DrawingToolbar drawingToolbar = new DrawingToolbar();
    Image offScreenImage = null;
    Dimension screenDimension = null;
    
    class MyMouseHandler extends MouseAdapter
    {        
        public void mousePressed(MouseEvent e)
        {
            drawing.mousePressed(e.getPoint());
            repaint();
        }
        public void mouseReleased(MouseEvent e)
        {
            drawing.mouseReleased(e.getPoint());
            repaint();
        }
        public void mouseDragged(MouseEvent e)
        {
            drawing.mouseDragged(e.getPoint());
            repaint();
        }
        public void mouseClicked(MouseEvent e)
        {
        	drawing.mouseClicked(e.getPoint());
        	repaint();
        }
    }

    DrawingProgram()
    {
        super("My Drawing Program");
        setSize(initDimen);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        MyMouseHandler mmh = new MyMouseHandler();
        addMouseListener(mmh);
        addMouseMotionListener(mmh);
        
        drawingToolbar.addDrawingToolbarEventListener(this);
        setVisible(true);
    }
    
    public void paint(Graphics screen)
    {
    	Dimension dimen = getSize();
        Insets insets = getInsets();
        
        // If the buffer image doesn't exist or the screen size changed
        if (offScreenImage == null || !dimen.equals(screenDimension))
        {
        	// Create a new image of the needed size
            screenDimension = dimen;
            offScreenImage = createImage(dimen.width, dimen.height);
        }
        
        // Get a graphics object representing the buffer image
        Graphics g = offScreenImage.getGraphics();
        
        // Get the status bar text
    	String str = drawing.toString();
    	
    	// Calculate the status bar text y position so that it's vertically centered on the status bar
    	int textPos = (STATUSBAR_HEIGHT - g.getFontMetrics().getHeight()) / 2;

    	// Fill background
    	g.setColor(Color.white);
    	g.fillRect(0, 0, dimen.width, dimen.height);
    	
    	// Draw shapes
    	drawing.draw(g);
    	
    	// Draw rectangle for status bar
    	g.setColor(Color.YELLOW);
    	g.fillRect(0, dimen.height - insets.bottom - STATUSBAR_HEIGHT, dimen.width, STATUSBAR_HEIGHT);
    	
    	// Draw text for status bar
    	g.setColor(Color.BLACK);
    	g.drawString(str, insets.left, dimen.height - STATUSBAR_HEIGHT + textPos);
    	
    	// Draw the buffer image to the screen
        screen.drawImage(offScreenImage, 0, 0, this);
    }
    
    // Capture events from the drawing toolbar and send the command to the handler
	@Override
	public void handleDrawingToolbarEvent(DrawingToolBarEvent e)
	{
		commandHandler(e.command.charAt(0));
	}
    
    // Process commands
    public boolean commandHandler(char cmd)
    {
		switch(cmd)
		{
		case 'r':
			this.drawing.setDrawType(DrawType.rectangle);
			break;
		case 'o':
			this.drawing.setDrawType(DrawType.oval);
			break;
		case 'l':
			this.drawing.setDrawType(DrawType.line);
			break;
		case 's':
			this.drawing.setDrawType(DrawType.scribble);
			break;
		case 'p':
		case 'a':
			this.drawing.setDrawType(DrawType.polygon);
			break;
		case 'q':
			return false;
		case 'f':
			this.drawing.setFilled(true);
			break;
		case 'd':
			this.drawing.setFilled(false);
			break;
		case 'b':
			this.drawing.setColor(Color.blue);
			break;
		case 'm':
			this.drawing.setColor(Color.magenta);
			break;
		case 'g':
			this.drawing.setColor(Color.green);
			break;
		case 't':
			this.drawingToolbar.toggleToolbarWindow();
			break;
		default: // '?' comes here
			System.out.println("r - drawType= Rectangle");
			System.out.println("o - drawType= Oval");
			System.out.println("l - drawType= Line");
			System.out.println("s - drawType= Scribble");
			System.out.println("p - drawType= Polygon");
			System.out.println("a - another Polygon");
			System.out.println("q - quit");
			System.out.println("f - filled objects");
			System.out.println("d - draw objects (not filled)");
			System.out.println("b - Use Blue Color");
			System.out.println("m - Use magenta Color");
			System.out.println("g - Use Green Color");
			System.out.println("t - Toggle visibility of toolbar window");
			break;
		}
		this.repaint();
		return true;
    }
    
    // Process keyboard input while command handler returns true
    public void inputProcessor()
    {
    	Scanner keyboard = new Scanner(System.in);

    	boolean continueFlag = true;

    	while(continueFlag)
    	{
    		System.out.println("Commands: ?, r, o, l, s, p, a, q, f, d, b, m, g, t");
    		String str = keyboard.next().toLowerCase();
    		if (str.length() < 1) 
    			continue;
    		continueFlag = commandHandler(str.charAt(0));
    		drawingToolbar.repaint();
    	}
    	keyboard.close();
    }

    public static void main(String[] args) 
    {
        DrawingProgram dp = new DrawingProgram();

        dp.inputProcessor();

        System.out.println("Exiting the Drawing Program");
        dp.dispose();
    }
}

