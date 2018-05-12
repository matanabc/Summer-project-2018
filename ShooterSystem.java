package robot.subsystems;

import java.util.Calendar;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.wpi.first.wpilibj.command.Subsystem;

public class ShooterSystem extends Subsystem {

	private static final int MAX_DECODER_HOSTORY_SIZE = 1000;
	private double encoderPosition = 0;//need to be the encoder

	//Tree of history position from encoder
	private TreeMap<Long,Double> encoderHistory = new TreeMap<Long,Double>();
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}

	public void addEncoderPoditionToHistory() {
		//Add encoder position and time to history
		encoderHistory.put(Calendar.getInstance().getTimeInMillis(), encoderPosition);
		
		// remove the lowest key so he don't grow forever...
		if(encoderHistory.size() > MAX_DECODER_HOSTORY_SIZE) {
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
}

