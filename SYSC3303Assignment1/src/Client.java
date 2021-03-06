import java.io.*;
import java.net.*;
import java.util.Random;

/*
 * Client generates a sequence of 20 characters as a string, sends to proxy then receives a sequence back from proxy
 * @author Kevin Ho (100997967) and Juan Paulo Contreras(101006952)
 * @version July 10, 2018
 */

public class Client {
	
	private DatagramPacket sendPacket, receivePacket;
	private DatagramSocket sendReceiveSocket;
	
	public Client() {
		/* Construct a datagram socket with any port 
		 * Used to send and receive UDP Datagram packets
		 */
		try {sendReceiveSocket = new DatagramSocket(13);
		} catch (SocketException se) { // Can't create the socket.
			se.printStackTrace();
			System.exit(1);
		}
	}
	
	private void sendString() {
		//randomly generating a sequence of 20 characters 
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
		//creating a datagram packet with the 20 byte message 
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
		//receiving the packet and blocking until packet receieved
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

	
	public static void main(String[] args){
		final long startTime = System.currentTimeMillis();
		Client c = new Client();
		//100 iterations of messages
		for(int i = 0; i < 100; i++) {
			System.out.println(i + "."); 
			c.sendString();
			c.receiveString();
		}
		c.sendReceiveSocket.close();
		final long endTime = System.currentTimeMillis();
		//calculation and printing execution time
		System.out.println("Total execution time: " + (endTime - startTime) + "ms." );
	}
}
