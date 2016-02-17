package first_shape_drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

import javax.swing.JFrame;

public class DrawingProgram extends JFrame{

    Drawing drawing = new Drawing();

    // INNER Class
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
    }

    DrawingProgram()
    {
        super("My Drawing Program");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        MyMouseHandler mmh = new MyMouseHandler();
        addMouseListener(mmh);
        addMouseMotionListener(mmh);
        DrawingToolbar drawingToolbar = new DrawingToolbar();
        this.add(drawingToolbar, BorderLayout.NORTH);
        setVisible(true);
    }
    public void paint(Graphics g)
    {
        Dimension dimen = getSize();
        Insets insets = getInsets();
        int top = insets.top;
        int left = insets.left;
        g.setColor(Color.white);
        g.fillRect(0, 0, dimen.width, dimen.height);

        drawing.draw(g);
        String str = drawing.toString();
        System.out.println(str);
    }

    public static void main(String[] args) {

        DrawingProgram dp = new DrawingProgram();
        Scanner keyboard = new Scanner(System.in);

        boolean continueFlag=true;
        while(continueFlag)
        {
            System.out.println("Cmds: r,o,l,s,p,a,q,?,f,d,b,m,g");
            String str = keyboard.next().toLowerCase();
            if (str.length() == 0) continue;

            switch(str.charAt(0))
            {
            case 'r':
                dp.drawing.setDrawType(DrawType.rectangle);
                break;
            case 'o':
                dp.drawing.setDrawType(DrawType.oval);
                break;
            case 'l':
                dp.drawing.setDrawType(DrawType.line);
                break;
            case 's':
                dp.drawing.setDrawType(DrawType.scribble);
                break;
            case 'p':
            case 'a':
                dp.drawing.setDrawType(DrawType.polygon);
                break;
            case 'q':
                continueFlag = false;
                break;
            case 'f':
                dp.drawing.setFilled(true);
                break;
            case 'd':
                dp.drawing.setFilled(false);
                break;
            case 'b':
                dp.drawing.setColor(Color.blue);
                break;
            case 'm':
                dp.drawing.setColor(Color.magenta);
                break;
            case 'g':
                dp.drawing.setColor(Color.green);
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
                break;
            }
        }
        System.out.println("Exitting the Drawing Program");
        dp.dispose();
        keyboard.close();

    }

}

