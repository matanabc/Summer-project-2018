

import java.awt.EventQueue;

import javax.swing.JFrame;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Main {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SliderTestFrame frame = new SliderTestFrame();
		
		NetworkTable.setClientMode();
		NetworkTable.setIPAddress("10.32.11.2");//ip of the robot
		NetworkTable.initialize();
		NetworkTable VisionTable = NetworkTable.getTable("SmartDashboard");


		EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {         
              frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              frame.setVisible(true);
           }
        });
		
		int i =0;
		
		while(true) {
			
			frame.getTextField()[0].setText("H Min - " + frame.getSlider()[0].getValue());
			frame.getTextField()[1].setText("H Max - " + frame.getSlider()[1].getValue());
			frame.getTextField()[2].setText("S Min - " + frame.getSlider()[2].getValue());
			frame.getTextField()[3].setText("S Max - " + frame.getSlider()[3].getValue());
			frame.getTextField()[4].setText("V Min - " + frame.getSlider()[4].getValue());
			frame.getTextField()[5].setText("V Max - " + frame.getSlider()[5].getValue());
			
			VisionTable.putNumber("hMin", frame.getSlider()[0].getValue());
			VisionTable.putNumber("sMin", frame.getSlider()[2].getValue());
			VisionTable.putNumber("vMin", frame.getSlider()[4].getValue());
			
			VisionTable.putNumber("hMax", frame.getSlider()[1].getValue());
			VisionTable.putNumber("sMax", frame.getSlider()[3].getValue());
			VisionTable.putNumber("vMax", frame.getSlider()[5].getValue());

			
			
			//VisionTable.putNumber("cp", i);
			
			i++;
			
			 //System.out.println("H Min - " + frame.getSlider()[1].getSize());
			 //System.out.println("H Max - " + frame.getSlider()[1].getValue());
			 //System.out.println("s Min - " + frame.getSlider()[2].getValue());
			 //System.out.println("S Max - " + frame.getSlider()[3].getValue());
			 //System.out.println("v Min - " + frame.getSlider()[4].getValue());
			 //System.out.println("V Max - " + frame.getSlider()[5].getValue());
			 
		}
	}

}
