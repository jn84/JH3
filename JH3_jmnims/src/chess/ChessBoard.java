package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class ChessBoard extends JFrame
{
	private final int windowHeight = 200, windowWidth = 200;

	private final Color[] boardColors = { Color.ORANGE, Color.CYAN };
	
	private final PieceType[] kingRow = 
		{ PieceType.Rook,   PieceType.Knight, PieceType.Bishop, 
		  PieceType.Queen,  PieceType.King,   PieceType.Bishop, 
		  PieceType.Knight, PieceType.Rook};
	
	BoardDimensions bDims = null;
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
	
	private Color toggleColor(Color c)
	{
		if (c == boardColors[0])
			return boardColors[1];
		return boardColors[0];
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);

		bDims = new BoardDimensions(
					this.getInsets().left,
					this.getInsets().top, 
					this.getContentPane().getWidth() / 8, 
					this.getContentPane().getHeight() / 8);
		
		
		 
		Color rectColor = boardColors[0];
		for (int i = 0; i < 8; i++) // 8 rows
		{
			rectColor = toggleColor(rectColor);
			for (int j = 0; j < 8; j++) // 8 columns
			{
				g.setColor(rectColor);
				g.fillRect(
						j * bDims.square_width + bDims.left,
						i * bDims.square_height + bDims.top,
						bDims.square_width, 
						bDims.square_height);
				rectColor = toggleColor(rectColor);
			}
		}

		int[] pawnRows = { 1, 6 };
		int[] kingRows = { 0, 7 };
		ColorType[] colorSwitch = { ColorType.black, ColorType.white };

		// Draw king rows
		for (int i = 0; i < kingRows.length; i++)
			for (int y = 0; y < 8; y++)
				new Piece(kingRow[y], colorSwitch[i], y, kingRows[i])
					.drawInPosition(g, bDims);
		
		// Draw pawn rows
		for (int i = 0; i < pawnRows.length; i++)
			for (int y = 0; y < 8; y++)
				new Piece(PieceType.Pawn, colorSwitch[i], y, pawnRows[i])
					.drawInPosition(g, bDims);
	}
	
	public static void main(String[] args)
	{
		ChessBoard cBoard = new ChessBoard();
	}
}
