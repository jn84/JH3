package first_shape_drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

abstract class Shape
{
	Color color;
	Shape ( Color c)
	{
		color =c;
	}
	abstract void firstPoint(Point p);
	abstract void draw(Graphics g);
	abstract void subsequentPoint(Point p);
}
class Rectangle extends Shape
{
	boolean filled=false;
	Point start;
	Point lastPoint;
	Rectangle(Color c, boolean filled)
	{
		super(c);
		lastPoint = start;
		this.filled = filled;
	}
	@Override
	void firstPoint(Point p) {
		start =p;
		lastPoint =p;
	}
	@Override
	void draw(Graphics g) {
		g.setColor(color);
		int x = Math.min(start.x, lastPoint.x);
		int y = Math.min(start.y, lastPoint.y);
		int w = Math.abs(start.x - lastPoint.x);
		int h = Math.abs(start.y - lastPoint.y);
		if (filled)
			g.fillRect(x, y, w, h);
		else
			g.drawRect(x, y, w, h);

	}
	@Override
	void subsequentPoint(Point p) {
		lastPoint =p;

	}
}

class Scribble extends Shape
{
	ArrayList<Point> points= new ArrayList<Point>();
	Scribble(Color c)
	{
		super(c);
	}
	@Override
	void firstPoint(Point p) {
		points.add(p);

	}
	@Override
	void draw(Graphics g) {
		g.setColor(color);
		for (int i=1; i < points.size(); i++)
		{            
			Point first = points.get(i-1);
			Point next = points.get(i);
			g.drawLine(first.x, first.y, next.x, next.y);
		}

	}
	@Override
	void subsequentPoint(Point p) {
		points.add(p);

	}
}


