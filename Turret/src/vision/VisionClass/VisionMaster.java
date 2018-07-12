package vision.VisionClass;

import java.util.Calendar;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import vision.VisionControllers.VisionControllerInterface;

public class VisionMaster{

	private int maxDecoderHestorySize;
	private double pixelToAngle;
	private double senterInPixel;
	private String NTValueName;
	private double lastTime;
	protected VisionAddHistoryTrhad addHistoryTrhad;
	private VisionData data;
	private double offSeat;
	private VisionControllerInterface VCTilt;
	private VisionControllerInterface VCPan;
	private boolean singleTbothF;
	private  boolean panTtiltF;

	//private String chose;

	//Tree of history position from encoder
	private TreeMap<Long,Double> panHistory;
	private TreeMap<Long,Double> tiltHistory;


	public VisionMaster(VisionControllerInterface VC, int maxDecoderHestorySize, double cameraAngle, 
			double cameraWidth, String NTValueName, double offSeat, boolean panTtiltF) {

		this.maxDecoderHestorySize= maxDecoderHestorySize;
		this.pixelToAngle = cameraAngle / cameraWidth; 
		this.senterInPixel = cameraWidth / 2;
		this.NTValueName = NTValueName;
		this.offSeat = offSeat;
		
		if(panTtiltF){
			this.VCPan = VC;
		}else{
			this.VCTilt = VC;
		}

		singleTbothF = true;
		this.panTtiltF = panTtiltF;

		panHistory = new TreeMap<Long,Double>();

		addHistoryTrhad = new VisionAddHistoryTrhad();
		startAddToHistoryTrhad();

		data = new VisionData(0, 0);
	}

	public VisionMaster(VisionControllerInterface VCPan, VisionControllerInterface VCTilt, int maxDecoderHestorySize,
			double cameraAngle, double cameraWidth, String NTValueName, double offSeat) {

		this.maxDecoderHestorySize= maxDecoderHestorySize;
		this.pixelToAngle = cameraAngle / cameraWidth; 
		this.senterInPixel = cameraWidth / 2;
		this.NTValueName = NTValueName;
		this.offSeat = offSeat;
		this.VCPan = VCPan;
		this.VCTilt = VCTilt;

		singleTbothF = false;

		panHistory = new TreeMap<Long,Double>();
		tiltHistory = new TreeMap<Long,Double>();

		addHistoryTrhad = new VisionAddHistoryTrhad();
		startAddToHistoryTrhad();

		data = new VisionData(0, 0);
	}

	//Don't change if you don't need to!!!
	public void addPanAndTiltPositionToHistory() {
		
		if(singleTbothF){
			
			if(panTtiltF){
				//Add position and time to history
				panHistory.put(Calendar.getInstance().getTimeInMillis(), VCPan.getSource());

				// remove the lowest key so he don't grow forever...
				if(panHistory.size() > this.maxDecoderHestorySize) {
					panHistory.remove(panHistory.firstKey());	
				}
				
			}else{
				//Add position and time to history
				tiltHistory.put(Calendar.getInstance().getTimeInMillis(), VCTilt.getSource());

				// remove the lowest key so he don't grow forever...
				if(tiltHistory.size() > this.maxDecoderHestorySize) {
					tiltHistory.remove(tiltHistory.firstKey());	
				}
			}
			
		}else{
			
			//Add position and time to history
			panHistory.put(Calendar.getInstance().getTimeInMillis(), VCPan.getSource());
			tiltHistory.put(Calendar.getInstance().getTimeInMillis(), VCTilt.getSource());
			
			// remove the lowest key so he don't grow forever...
			if(panHistory.size() > this.maxDecoderHestorySize) {
				panHistory.remove(panHistory.firstKey());	
			}
			
			// remove the lowest key so he don't grow forever...
			if(tiltHistory.size() > this.maxDecoderHestorySize) {
				tiltHistory.remove(tiltHistory.firstKey());	
			}
		}
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
				
				setVisionData(TargetInfo);
			}
		}
		//return new VisionData(0, 0); //there is problem with the data from the vision
		return data;
	}

	//Don't change if you don't need to!!!
	protected void startAddToHistoryTrhad(){
		new Thread(addHistoryTrhad).start();
	}	

	protected void setVisionData(String [] TargetInfo){
		
		if(singleTbothF){
			if(panTtiltF){
				this.data.setAngleToTarget(

						(getPanPositionFromTime(Long.parseLong(TargetInfo[2])) //position when the frame was take

						- 	//need to check it it cold be - or +

						((Double.parseDouble(TargetInfo[0]) - this.senterInPixel) * this.pixelToAngle)) //angle error when the frame was take

						+

						offSeat); //offSeat from target if camera is not in the center

				this.data.setPixelHeightToTarget(Double.parseDouble(TargetInfo[1])); //target Height in pixel

			}else{
				this.data.setAngleToTarget(Double.parseDouble(TargetInfo[0])); //offSeat from target if camera is not in the center

				this.data.setPixelHeightToTarget(VCTilt.castYpixel(Double.parseDouble(TargetInfo[1]),
													getTiltPositionFromTime(Long.parseLong(TargetInfo[2]))));
				
			}

		} else {

			this.data.setAngleToTarget(

					(getPanPositionFromTime(Long.parseLong(TargetInfo[2])) //position when the frame was take

					- 	//need to check it it cold be - or +

					((Double.parseDouble(TargetInfo[0]) - this.senterInPixel) * this.pixelToAngle)) //angle error when the frame was take

					+

					offSeat); //offSeat from target if camera is not in the center

			this.data.setPixelHeightToTarget(VCTilt.castYpixel(Double.parseDouble(TargetInfo[1]),
					getTiltPositionFromTime(Long.parseLong(TargetInfo[2]))));
			
		}
	}
}



/*protected double TargetHightMath(double targetHigth) {
		if(chose.equals("RPM")) {
			return VC.TargetHightToRPM(targetHigth);

		}else if (chose.equals("Distance")) {
			return VC.TargetHightToDistance(targetHigth);

		}else if (chose.equals("Angle")) {
			return VC.TargetHightToAngle(targetHigth);
		}else {
			return 0;
		}
}*/




