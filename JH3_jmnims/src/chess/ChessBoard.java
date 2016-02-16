package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.concurrent.locks.Lock;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessBoard extends JFrame
{
	private final int windowHeight = 600, windowWidth = 600;
	
	BoardDimensions boardDimensions = null;
	Graphics g = null;
	
	public ChessBoard()
	{
		super ("Chess");
		ChessPiece.readInImages();
		this.setSize(windowWidth, windowHeight);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setVisible(true);
	}
	
	public void paint(Graphics g)
	{
		int topInset = this.getInsets().top,
			leftInset = this.getInsets().left;
		
		int insetWidth = this.getContentPane().getWidth(),
			insetHeight = this.getContentPane().getHeight();
		
		System.out.println("W: " + insetWidth);
		System.out.println("W: " + insetHeight);
		
		boardDimensions = 
				new BoardDimensions(
						insetHeight,
						insetWidth, 
						insetWidth / 8, 
						insetHeight / 8);
		
		boolean isDrawRect = false;
		for (int i = 0; i < 8; i++) // 8 rows
		{
			isDrawRect = !isDrawRect;
			for (int j = 0; j < 8; j++) // 8 columns
			{
				if (isDrawRect)
					g.setColor(Color.WHITE);
				else
					g.setColor(Color.GREEN);
				g.fillRect(
						j * boardDimensions.square_width + leftInset, 
						i * boardDimensions.square_height + topInset, 
						boardDimensions.square_width, 
						boardDimensions.square_height);
				isDrawRect = !isDrawRect;
			}
		}
	}
	
	public static void main(String[] args)
	{
		ChessBoard cBoard = new ChessBoard();
	}
}
