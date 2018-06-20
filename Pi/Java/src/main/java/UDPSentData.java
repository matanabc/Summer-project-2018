import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;


public class UDPSentData implements Runnable {

	private static final String FILE_PLACE = "/home/pi/Documents/vision/Java/Values";//fill place

	protected BlockingQueue<String> queue = null;
	protected DatagramSocket clientSocket;
	protected InetAddress IPAddress;
	protected byte[] sendData;
	protected DatagramPacket sendPacket;
	protected Properties properties;
	protected int port;

	public UDPSentData(BlockingQueue<String> queue) {
		this.queue = queue;
		try {
			this.properties.load(new FileInputStream(FILE_PLACE));
			this.clientSocket = new DatagramSocket();
			this.IPAddress = InetAddress.getByName(properties.getProperty("roboRioIP", "roborio-3211-FRC.local"));
			this.port = Integer.parseInt(properties.getProperty("UDPPort", "3211"));
		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			this.sendData = this.queue.take().getBytes();
			this.sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			this.clientSocket.send(this.sendPacket);					
			clientSocket.close();

		}catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

}
