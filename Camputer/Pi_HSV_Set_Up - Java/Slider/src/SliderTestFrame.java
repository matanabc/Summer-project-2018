

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;

class SliderTestFrame extends JFrame
{
	public SliderTestFrame()
	{
		setTitle("SliderTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		sliderPanel = new JPanel();
		sliderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		this.sliders = new JSlider[6];
		this.textField = new JTextField[6];
		

		this.sliders[0] = new JSlider();
		textField[0] = new JTextField();
		
		addSlider(this.sliders[0], "H Min", 0, textField[0]);
		//addText(textField[0]);
		
		
		this.sliders[1] = new JSlider();
		textField[1] = new JTextField();
       
		addSlider(this.sliders[1], "H Max", 255, textField[1]);
		//addText(textField[1]);
        
        
        this.sliders[2] = new JSlider();
        textField[2] = new JTextField();
        addSlider(this.sliders[2], "S Min", 0, textField[2]);
		//addText(textField[2]);
		
		
        this.sliders[3] = new JSlider();
        textField[3] = new JTextField();
        addSlider(this.sliders[3], "S Max", 255, textField[3]);
		//addText(textField[3]);
		
        
        this.sliders[4] = new JSlider();
        textField[4] = new JTextField();
        addSlider(this.sliders[4], "V Min", 0, textField[4]);
		//addText(textField[4]);
		
        
        this.sliders[5] = new JSlider();
        textField[5] = new JTextField();
        addSlider(this.sliders[5], "V Max", 255, textField[5]);
		//addText(textField[5]);
        
		add(sliderPanel, BorderLayout.CENTER);
	}

	/**
	 * Adds a slider to the slider panel and hooks up the listener
	 * @param s the slider
	 * @param description the slider description
	 */
	public void addSlider(JSlider s, String description, int startValue, JTextField t)
	{
		s.addChangeListener(listener);
		s.setMaximum(255);
		s.setMinimum(0);
		s.setValue(startValue);
		//Dimension size = new Dimension(200, 16);
		//t.setText("seting the values");
		JPanel panel = new JPanel();
		panel.add(s);
		panel.add(new JLabel(description));
		panel.add(t);
		sliderPanel.add(panel);
	}
	
	public JSlider[] getSlider() {
		return this.sliders;
	}
	
	public JTextField[] getTextField() {
		return this.textField;
	}

	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 400;

	private JSlider[] sliders;
	//private TextField textField;

	private JPanel sliderPanel;
	private JTextField[] textField;
	private ChangeListener listener;
}

