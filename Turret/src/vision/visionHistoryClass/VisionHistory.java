package vision.visionHistoryClass;

import java.util.Calendar;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class VisionHistory extends Subsystem {

	private int maxDecoderHestorySize;
	private double pixelToAngle;
	private double senterInPixel;
	private String NTValueName;
	private double lastTime;

	//Tree of history position from encoder
	private TreeMap<Long,Double> encoderHistory = new TreeMap<Long,Double>();


	public VisionHistory(int maxDecoderHestorySize, double cameraAngle, double cameraWidth, String NTValueName) {
		this.maxDecoderHestorySize= maxDecoderHestorySize;
		this.pixelToAngle = cameraAngle / cameraWidth; 
		this.senterInPixel = cameraWidth / 2;
		this.NTValueName = NTValueName;
	}

	public void initDefaultCommand() {
		setDefaultCommand(new VisionHistoryCommand(this));
	}

	public abstract double addToHistory();

	public void addEncoderPoditionToHistory() {
		//Add encoder position and time to history
		encoderHistory.put(Calendar.getInstance().getTimeInMillis(), addToHistory());

		// remove the lowest key so he don't grow forever...
		if(encoderHistory.size() > this.maxDecoderHestorySize) {
			encoderHistory.remove(encoderHistory.firstKey());	
		}
	}

	public double getEncoderPositionFromTime(Long time) {
		//check if is bigger then 0
		if(encoderHistory.size()==0) return 0;

		//Find the key value who closer to the time from camera  	
		Entry<Long, Double> encoderTime = encoderHistory.floorEntry(time);

		//If the key equals to null get the first key
		if(encoderTime == null) {
			encoderTime = encoderHistory.firstEntry();
		}

		//Return encoder position form the time
		return encoderTime.getValue();
	}

	public VisionData getAngleToTargetAndY() {

		String [] TargetInfo = SmartDashboard.getString(this.NTValueName, "0;").split(";");
		
		/*
		 * TargetInfo[0] - get the x pixel from the frame
		 * TargetInfo[1] - get the x pixel from the frame
		 * TargetInfo[2] - get the time when the fram was taking
		*/

		if(TargetInfo.length == 3){//if the data is came;
			if(Long.parseLong(TargetInfo[2]) > lastTime){//if the frame take after the last time
				
				lastTime = Double.parseDouble(TargetInfo[2]);//change the last time 

				return new VisionData(
						(getEncoderPositionFromTime(Long.parseLong(TargetInfo[2])) //position when the frame was take
								
								- 	//need to check it it cold be - or +
						
						((Double.parseDouble(TargetInfo[0]) - this.senterInPixel) * this.pixelToAngle)), //angle error when the frame was take

						Double.parseDouble(TargetInfo[1])); //target Height in pixel
			}
		}
		return new VisionData(0, 0); //there is problem with the data from the vision
	}
}



