package first_shape_drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class DrawingToolbar extends JToolBar implements ActionListener, DrawingToolbarEventGenerator
{
    private Graphics toolBarBuffer = null;
    private Dimension toolBarDimension = null;
	
	private JPanel shapePanel = null;
	private JPanel optionPanel = null;
	private JPanel fillPanel = null;
	private JPanel colorPanel = null;
	private JPanel colorSelectPanel = null;
	
	private ArrayList<JButton> toolbarButtons = null;
	
	private JLabel fillCheckLabel = null;
	private JCheckBox fillCheckBox = null;
	
	private JLabel colorSelectLabel = null;
	private JButton blueButton = null;
	private JButton magentaButton = null;
	private JButton greenButton = null;
	
	private ArrayList<ShapeButtonType> drawingButtons =
			new ArrayList<ShapeButtonType>();
	private ArrayList<DrawingToolbarListener> drawingToolbarListeners = 
			new ArrayList<DrawingToolbarListener>();
	
	public DrawingToolbar()
	{
		super();
		this.setFloatable(false);
		this.setLayout(new GridLayout(1, 2));
		
		drawingButtons.add((new ShapeButtonType("Rectangle", "r")));
		drawingButtons.add((new ShapeButtonType("Oval", "o")));
		drawingButtons.add((new ShapeButtonType("Line", "l")));
		drawingButtons.add((new ShapeButtonType("Scribble", "s")));
		drawingButtons.add((new ShapeButtonType("Polygon", "p")));
		drawingButtons.add((new ShapeButtonType("Finish Polygon", "a")));
		
		
		// Create and configure toolbar objects
		shapePanel = new JPanel(new GridLayout(2, 0));
		optionPanel = new JPanel(new GridLayout(1, 2));
		fillPanel = new JPanel(new GridLayout(2, 1));
		colorPanel = new JPanel(new GridLayout(2, 1));
		colorSelectPanel = new JPanel(new GridLayout(1, 0));
		
		fillCheckLabel = new JLabel("Fill Shape");
		fillCheckLabel.setHorizontalAlignment(CENTER);
		fillCheckBox = new JCheckBox();
		fillCheckBox.setHorizontalAlignment(CENTER);
		fillCheckBox.setActionCommand("f");
		
		colorSelectLabel = new JLabel("Color Selection");
		colorSelectLabel.setHorizontalAlignment(CENTER);
		blueButton = new JButton();
		magentaButton = new JButton();
		greenButton = new JButton();
		
		blueButton.setActionCommand("b");
		magentaButton.setActionCommand("m");
		greenButton.setActionCommand("g");
		
		
		blueButton.setBackground(Color.BLUE);
		magentaButton.setBackground(Color.MAGENTA);
		greenButton.setBackground(Color.GREEN);
		
		
		// Add toolbar objects
		this.addShapeButtons(shapePanel);
		this.add(shapePanel);
			
		
		fillPanel.add(fillCheckLabel);
		fillPanel.add(fillCheckBox);
		optionPanel.add(fillPanel);
		
		
		colorPanel.add(colorSelectLabel);
		colorSelectPanel.add(blueButton);
		colorSelectPanel.add(magentaButton);
		colorSelectPanel.add(greenButton);
		colorPanel.add(colorSelectPanel);
		optionPanel.add(colorPanel);
		
		this.add(optionPanel);
		
		this.registerActionListeners();
	}
	
	private void addShapeButtons(JPanel p)
	{
		toolbarButtons = new ArrayList<JButton>();
		for (int i = 0; i < drawingButtons.size(); i++)
		{
			toolbarButtons.add(new JButton(drawingButtons.get(i).label));
			toolbarButtons.
				get(toolbarButtons.size() - 1).
					setActionCommand(drawingButtons.get(i).cmd);
			p.add(toolbarButtons.get(i));
		}
	}
	
	private void registerActionListeners()
	{
		for (JButton button : toolbarButtons)
			button.addActionListener(this);
		fillCheckBox.addActionListener(this);
		blueButton.addActionListener(this);
		magentaButton.addActionListener(this);
		greenButton.addActionListener(this);
	}
	
	public void getGrapicsImage()
	{
		Graphics image = this.getGraphics();
	}
	
	public void paint(Graphics screen)
	{
		super.paint(screen);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		System.out.println(e.getSource().toString());
		if (cmd.equals("f")) // Will always be "f" if fill checkbox changed
		{
			if (fillCheckBox.isSelected())
				triggerDrawingToolbarEvent(new DrawingToolBarEvent(e, "f"));
			else
				triggerDrawingToolbarEvent(new DrawingToolBarEvent(e, "d"));
			return;
		}
		triggerDrawingToolbarEvent(new DrawingToolBarEvent(e, cmd));
	}

	@Override
	public void addDrawingToolbarEventListener(DrawingToolbarListener listener)
	{
		drawingToolbarListeners.add(listener);
	}
	
	private void triggerDrawingToolbarEvent(DrawingToolBarEvent e) {
		if (drawingToolbarListeners == null || drawingToolbarListeners.isEmpty())
			return;
		for (int i = 0; i < drawingToolbarListeners.size(); i++) {
			drawingToolbarListeners.get(i).handleDrawingToolbarEvent(e);
		}
	}
	
	private class ShapeButtonType
	{
		String cmd = "",
			   label = "";
		
		public ShapeButtonType(String lbl, String c)
		{
			cmd = c;
			label = lbl;
		}
	}
}

class DrawingToolBarEvent extends EventObject
{
	String command = "";
	
	public DrawingToolBarEvent(Object source, String c)
	{
		super(source);
		command = c;
		System.out.println("command set as: " + command);
	}
}

interface DrawingToolbarListener extends EventListener
{
	void handleDrawingToolbarEvent(DrawingToolBarEvent e);
}

interface DrawingToolbarEventGenerator
{
	void addDrawingToolbarEventListener(DrawingToolbarListener listener);
}
