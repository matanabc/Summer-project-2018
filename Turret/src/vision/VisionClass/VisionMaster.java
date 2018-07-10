package vision.VisionClass;

import java.util.Calendar;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import vision.VisionControllers.VisionPanController;

public class VisionMaster{

	private int maxDecoderHestorySize;
	private double pixelToAngle;
	private double senterInPixel;
	private String NTValueName;
	private double lastTime;
	protected VisionAddHistoryTrhad addHistoryTrhad;
	private VisionData data;
	private double offSeat;
	private VisionPanController VC1;
	private VisionPanController VC2;

	//Tree of history position from encoder
	private TreeMap<Long,Double> panHistory = new TreeMap<Long,Double>();
	private TreeMap<Long,Double> tiltHistory = new TreeMap<Long,Double>();


	public VisionMaster(VisionPanController VC, int maxDecoderHestorySize, double cameraAngle, double cameraWidth, String NTValueName, double offSeat) {
		this.maxDecoderHestorySize= maxDecoderHestorySize;
		this.pixelToAngle = cameraAngle / cameraWidth; 
		this.senterInPixel = cameraWidth / 2;
		this.NTValueName = NTValueName;
		this.offSeat = offSeat;
		this.VC1 = VC;

		addHistoryTrhad = new VisionAddHistoryTrhad();
		startAddToHistoryTrhad();
	}

	public VisionMaster(VisionPanController VC1, VisionPanController VC2, int maxDecoderHestorySize, double cameraAngle, double cameraWidth, String NTValueName, double offSeat) {
		this.maxDecoderHestorySize= maxDecoderHestorySize;
		this.pixelToAngle = cameraAngle / cameraWidth; 
		this.senterInPixel = cameraWidth / 2;
		this.NTValueName = NTValueName;
		this.offSeat = offSeat;
		this.VC1 = VC1;
		this.VC2 = VC2;

		addHistoryTrhad = new VisionAddHistoryTrhad();
		startAddToHistoryTrhad();
	}

	//need to bee in 
	protected double targetHeightToDistance(double targetHeight){
		return targetHeight;
	}

	//Don't change if you don't need to!!!
	public void addPanAndTiltPositionToHistory() {
		//Add encoder position and time to history

		if(VC2 == null){

			panHistory.put(Calendar.getInstance().getTimeInMillis(), VC1.getPanSource());
			tiltHistory.put(Calendar.getInstance().getTimeInMillis(), VC1.getPanSource());

			// remove the lowest key so he don't grow forever...
			if(panHistory.size() > this.maxDecoderHestorySize) {
				panHistory.remove(panHistory.firstKey());	
			}else if(tiltHistory.size() > this.maxDecoderHestorySize){
				tiltHistory.remove(tiltHistory.firstKey());
			}
		}else{
			panHistory.put(Calendar.getInstance().getTimeInMillis(), VC1.getPanSource());
			tiltHistory.put(Calendar.getInstance().getTimeInMillis(), VC2.getPanSource());

			// remove the lowest key so he don't grow forever...
			if(panHistory.size() > this.maxDecoderHestorySize) {
				panHistory.remove(panHistory.firstKey());	
			}else if(tiltHistory.size() > this.maxDecoderHestorySize){
				tiltHistory.remove(tiltHistory.firstKey());
			}
		}
		
		
		//System.out.println("add to history");
	}

	//Don't change if you don't need to!!!
	protected double getPanPositionFromTime(Long time) {
		//check if is bigger then 0
		if(panHistory.size()==0) return 0;

		//Find the key value who closer to the time from camera  	
		Entry<Long, Double> panTime = panHistory.floorEntry(time);

		//If the key equals to null get the first key
		if(panTime == null) {
			panTime = panHistory.firstEntry();
		}

		//Return encoder position form the time
		return panTime.getValue();
	}
	
	protected double getTiltPositionFromTime(Long time) {
		//check if is bigger then 0
		if(tiltHistory.size()==0) return 0;

		//Find the key value who closer to the time from camera  	
		Entry<Long, Double> tiltTime = tiltHistory.floorEntry(time);

		//If the key equals to null get the first key
		if(tiltTime == null) {
			tiltTime = tiltHistory.firstEntry();
		}

		//Return encoder position form the time
		return tiltTime.getValue();
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

						(getPanPositionFromTime(Long.parseLong(TargetInfo[2])) //position when the frame was take

								- 	//need to check it it cold be - or +

								((Double.parseDouble(TargetInfo[0]) - this.senterInPixel) * this.pixelToAngle)) //angle error when the frame was take

						+

						offSeat, //offSeat from target if camera is not in the center

						Double.parseDouble(TargetInfo[1])); //target Height in pixel
				
						//if VC2 == null like addPanAndTiltPositionToHistory()

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



