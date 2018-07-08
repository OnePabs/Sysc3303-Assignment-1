import java.io.*;
import java.net.*;
import java.util.Random;

public class Client {
	
	DatagramPacket sendPacket, receivePacket;
	DatagramSocket sendReceiveSocket;
	
	public Client() {
		/* Construct a datagram socket with any port 
		 * Used to send and receive UDP Datagram packets
		 */
		try {sendReceiveSocket = new DatagramSocket();
		} catch (SocketException se) { // Can't create the socket.
			se.printStackTrace();
			System.exit(1);
		}
	}
	
	private void sendString() {
		//randomly generated sequence of 20 characters 
		final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final int N = alphabet.length();
		Random r = new Random();
		String s = ""; 
		for(int i = 0; i <20; i++) {
			s += alphabet.charAt(r.nextInt(N)); 
		}
		//converting string to bytes
		byte[] msg = new byte[20];
		msg = s.getBytes();
		//create a datagram packet with the 20 byte msg 
		try {
			sendPacket = new DatagramPacket(msg, 20, InetAddress.getLocalHost(), 5000);
		} catch(UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		}
		//send datagram packet to proxy with a socket
		try {
			sendReceiveSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Sent string: " + s);
		
	}
	
	private void receiveString() {
		//creating a datagram packet to receive up to 20 bytes of data
		byte data[] = new byte[20];
		receivePacket = new DatagramPacket(data, data.length);
		
		try {
			sendReceiveSocket.receive(receivePacket); 
		}catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		//process received data and print out
		String receive = new String(data, 0, receivePacket.getLength());
		System.out.println("Recieved string: " + receive);
		
	}
	
	public void printReceivedDatagram(){
		String s = receivePacket.getData().toString();
		System.out.println(s);
	}
	
	public static void main(String[] args){
		final long startTime = System.currentTimeMillis();
		Client c = new Client();
		//100 iterations 
		for(int i = 0; i < 100; i++) {
			c.sendString();
			c.receiveString();
			c.printReceivedDatagram();
		}
		c.sendReceiveSocket.close();
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) + "ms." );
	}
}
