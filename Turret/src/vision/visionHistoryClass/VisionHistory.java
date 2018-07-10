package vision.visionHistoryClass;

import java.util.Calendar;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class VisionHistory{

	private int maxDecoderHestorySize;
	private double pixelToAngle;
	private double senterInPixel;
	private String NTValueName;
	private double lastTime;
	protected VisionAddHistoryTrhad addHistoryTrhad;
	private VisionData data;
	private double offSeat;

	//Tree of history position from encoder
	private TreeMap<Long,Double> encoderHistory = new TreeMap<Long,Double>();


	public VisionHistory(int maxDecoderHestorySize, double cameraAngle, double cameraWidth, String NTValueName, double offSeat) {
		this.maxDecoderHestorySize= maxDecoderHestorySize;
		this.pixelToAngle = cameraAngle / cameraWidth; 
		this.senterInPixel = cameraWidth / 2;
		this.NTValueName = NTValueName;
		this.offSeat = offSeat;

		addHistoryTrhad = new VisionAddHistoryTrhad();
	}

	protected abstract double addToHistory();

	protected abstract double targetHeightToDistance(double targetHeight);

	//Don't change if you don't need to!!!
	public void addEncoderPositionToHistory() {
		//Add encoder position and time to history
		encoderHistory.put(Calendar.getInstance().getTimeInMillis(), addToHistory());

		// remove the lowest key so he don't grow forever...
		if(encoderHistory.size() > this.maxDecoderHestorySize) {
			encoderHistory.remove(encoderHistory.firstKey());	
		}

		//System.out.println("add to history");
	}

	//Don't change if you don't need to!!!
	protected double getPositionFromTime(Long time) {
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

	//Don't change if you don't need to!!!
	public VisionData getAngleAndDistanceToTarget() {

		String [] TargetInfo = SmartDashboard.getString(this.NTValueName, "0;").split(";");

		/*
		 *	TargetInfo[0] - get the x pixel from the frame
		 *	TargetInfo[1] - get the x pixel from the frame
		 *	TargetInfo[2] - get the time when the fram was taking
		*/

		if(TargetInfo.length == 3){//if the data is came;
			if(Long.parseLong(TargetInfo[2]) > lastTime){//if the frame take after the last time

				lastTime = Double.parseDouble(TargetInfo[2]);//change the last time 

				data = new VisionData(
						
						(getPositionFromTime(Long.parseLong(TargetInfo[2])) //position when the frame was take

						- 	//need to check it it cold be - or +

						((Double.parseDouble(TargetInfo[0]) - this.senterInPixel) * this.pixelToAngle)) //angle error when the frame was take

						+

						offSeat, //offSeat from target if camera is not in the center

						targetHeightToDistance(Double.parseDouble(TargetInfo[1]))); //target Height in pixel
				
			}
		}
		//return new VisionData(0, 0); //there is problem with the data from the vision
		return data;
	}

	//Don't change if you don't need to!!!
	protected void startAddToHistoryTrhad(){
		new Thread(addHistoryTrhad).start();
	}	
}



