package first_shape_drawing;

import javax.swing.JButton;
import javax.swing.JToolBar;

public class DrawingToolbar extends JToolBar
{
	public DrawingToolbar()

	{
		super();
		this.setFloatable(false);
		JButton jb1 = new JButton("Rectangle");
		this.add(jb1);

	}
	
	private void addButtons(JToolBar jtb)
	{
		
	}
}
