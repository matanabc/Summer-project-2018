package LogFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.LinkedList;

import robot.RobotMap;

public class WriteToFile implements Runnable{

	protected FileWriter fw;
	protected BufferedWriter bw;
	
	protected LinkedList<String> writeToFile;

	public WriteToFile(LinkedList<String> writeToFile) {
		
		this.writeToFile = writeToFile;

		startToWrite();
	}

	@Override
	public void run() {

		while(bw != null) {
			if(!writeToFile.isEmpty()) {
				if(writeToFile.getFirst().equals("clean")) {
					cleanFile();
				}
				writeToFile(writeToFile.removeFirst());
			}
		}
	}

	protected void writeToFile(String write) {
		try {
			bw.write(write);
			bw.newLine();
			bw.flush();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void startToWrite() {
		try {
			fw = new FileWriter(RobotMap.FILE_PLACE, true);
			bw = new BufferedWriter(fw);
			
			writeToFile("Robot is on");
			
			new Thread(this).start();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void cleanFile() {
		try {
			fw = new FileWriter(RobotMap.FILE_PLACE);
			bw = new BufferedWriter(fw);

			writeToFile("Robot is on");

			fw = new FileWriter(RobotMap.FILE_PLACE, true);
			bw = new BufferedWriter(fw);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void stopToWrite() {
		try {
			bw.close();
			fw.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
