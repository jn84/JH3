package first_shape_drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

// Stand alone toolbar class
// Implements custom events to which the instantiating object can subscribe
public class DrawingToolbar extends JToolBar implements ActionListener, DrawingToolbarEventGenerator
{
	private final Dimension windowSize = new Dimension(800, 150);
	
	//// Toolbar components
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
	
	// Array to store the various shape buttons
	private ArrayList<ShapeButtonType> drawingButtons =
			new ArrayList<ShapeButtonType>();
	
	// Store the subscriptions to this objects events
	private ArrayList<DrawingToolbarListener> drawingToolbarListeners = 
			new ArrayList<DrawingToolbarListener>();
	
	//// Toolbar window components
	JFrame toolbarWindow = new JFrame("Drawing Tools");
		
	public DrawingToolbar()
	{
		super();
		this.setFloatable(false);
		this.setLayout(new GridLayout(1, 2));
		this.setDoubleBuffered(true);
		
		// Easily add/remove/reorganize shape buttons with single lines
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
		blueButton.setActionCommand("b");
		blueButton.setBackground(Color.BLUE);
		
		magentaButton = new JButton();
		magentaButton.setActionCommand("m");
		magentaButton.setBackground(Color.MAGENTA);
		
		greenButton = new JButton();
		greenButton.setActionCommand("g");
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
		
		toolbarWindow.add(this);
		toolbarWindow.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		toolbarWindow.setSize(windowSize);
	}
	
	public void toggleToolbarWindow()
	{
		if (!toolbarWindow.isVisible())
			toolbarWindow.setVisible(true);
		else
			toolbarWindow.setVisible(false);
	}
	
	// Add the buttons from the drawingButtons array
	private void addShapeButtons(JPanel p)
	{
		// Create a JButton array based on the drawingButtons array
		toolbarButtons = new ArrayList<JButton>();
		for (int i = 0; i < drawingButtons.size(); i++)
		{
			toolbarButtons.add(new JButton(drawingButtons.get(i).label));
			
			// Set the action command for the button (the command that will be passed to the event generator)
			toolbarButtons.
				get(toolbarButtons.size() - 1).
					setActionCommand(drawingButtons.get(i).cmd);
			
			// Add the button to the proper panel within the JToolBar
			p.add(toolbarButtons.get(i));
		}
	}
	
	// Register the listeners for the buttons
	private void registerActionListeners()
	{
		for (JButton button : toolbarButtons)
			button.addActionListener(this);
		fillCheckBox.addActionListener(this);
		blueButton.addActionListener(this);
		magentaButton.addActionListener(this);
		greenButton.addActionListener(this);
	}
	
	// Handle button presses and trigger the custom event so that
	// the instantiating object can know what happened
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String cmd = e.getActionCommand();
		if (cmd.equals("f")) // Will always be "f" if fill checkbox changed
		{
			// Determine which command to send based on the state of the checkbox
			if (fillCheckBox.isSelected())
				triggerDrawingToolbarEvent(new DrawingToolBarEvent(e, "f"));
			else
				triggerDrawingToolbarEvent(new DrawingToolBarEvent(e, "d"));
			return;
		}
		triggerDrawingToolbarEvent(new DrawingToolBarEvent(e, cmd));
	}

	// Add a listener to the listeners array
	@Override
	public void addDrawingToolbarEventListener(DrawingToolbarListener listener)
	{
		drawingToolbarListeners.add(listener);
	}
	
	// If there are no listeners, forget about it and return.
	// Otherwise, send the event to each listener
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
