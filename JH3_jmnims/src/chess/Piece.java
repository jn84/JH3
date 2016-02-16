package chess;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

enum ColorType { black, white };
enum PieceType { Pawn, Rook, Knight, Bishop, Queen, King };

class ChessPiece
{
    private static PieceType[] pieceType = PieceType.values(); 
    private static ColorType[] colorType = ColorType.values();
    
    public static ChessPiece[][]chessPieces = new ChessPiece[colorType.length][pieceType.length];
    
    public static void readInImages()
    {
        for (int i=0; i < colorType.length; i++)
            for (int j=0; j < pieceType.length; j++)
                chessPieces[i][j] = new ChessPiece(i,j);
    }

    public void draw(Graphics g, int x, int y, int wSpace, int hSpace)
    {
        double scale_width = (double)wSpace/width;
        double scale_height = (double)hSpace/height;
        
        double scale = Math.min(scale_width, scale_height);
        int newHeight = (int)(height*scale);
        int newWidth = (int)(width*scale);
        x = x + wSpace/2 - newWidth/2;
        y = y + hSpace/2 - newHeight/2;
        g.drawImage(pieceImg, x, y, newWidth, newHeight, null);
    }
    
    private Image pieceImg;
    
    private int width, height;
    
    
    // helper method to load the image files
    private Image loadImage(String fileName) 
    {
    	return new ImageIcon("Resources\\" + fileName).getImage();
    }
    
    // Constructor (the color of the piece, the id of the piece (from the enum?))
    private ChessPiece(int colorIndex, int pieceIndex)
    {
    	
    	// Generate the filename to load the image into this object
        String imageName = 
        		colorType[colorIndex].toString() +
                pieceType[pieceIndex].toString() + ".gif";
        
        // Load the image
        pieceImg = loadImage(imageName);
        
        // set the private member integer variables so that we know how big the image is 
        width = pieceImg.getWidth(null);
        height = pieceImg.getHeight(null);
    }
}


// Stores the board dimensions 
// Should adjust when the window size is changed to manage scaling
class BoardDimensions 
{
    int left, top, square_width, square_height;
    BoardDimensions(int left, int top, int square_width, int square_height)
    {
        this.left = left;
        this.top = top;
        this.square_width = square_width;
        this.square_height = square_height;
    }
}


//Create a piece object for each piece
// Keeps track of the piece's location on the board
// Stores the pieces type and color

class Piece 
{    
    int xSquare, ySquare;
    PieceType pieceType;  
    ColorType color;      
    			  
    public Piece (PieceType p, ColorType color, int xSquare, int ySquare)
    {
        this.pieceType = p;
        this.color = color;
        this.xSquare = xSquare;
        this.ySquare = ySquare;
    }
    
    
    // Draw the chess piece on the board
    public void drawInPosition(Graphics g, BoardDimensions b)
    {
         ChessPiece chessPiece = ChessPiece.chessPieces[color.ordinal()][pieceType.ordinal()];
         int x = b.left + xSquare * b.square_width ;
         int y = b.top + ySquare * b.square_height;
         chessPiece.draw(g, x, y, b.square_width, b.square_height);
    }
}
