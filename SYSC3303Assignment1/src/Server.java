import java.net.*;
import java.io.*;

public class Server {
	
	//fields
	DatagramPacket receivePacket;
	DatagramPacket sendPacket;
	DatagramSocket receiveSocket;
	DatagramSocket sendSocket;
	int DatagramLength = 20;
	
	public Server(int ServerPortNumber){
		//initialize sockets
		try{
			receiveSocket = new DatagramSocket(ServerPortNumber);
			sendSocket = new DatagramSocket();
		}catch(SocketException se){
			se.printStackTrace();
			System.exit(1);
		}
	}//end constructor

	public void receiveDatagram(){
		//listens to channel assigned for a package to be received
		//once the package is received, copy its data to the field receivePackage
		//parameters: none
		//return: none, field receivePacket initialized with the package received
		
		//create a datagram packet where the information will be copyed
		byte[] data = new byte[20];
		receivePacket = new DatagramPacket(data,data.length);
		
		//listen on port using socket receiveSocket
		//when datagram packet received, copy it into receivePacket
		try{
			System.out.println("Waiting for Packet...");
			receiveSocket.receive(receivePacket);
		}catch(IOException e){
			System.out.println("Packet not received");
			System.exit(1);
		}
		System.out.println("Packet Received"); 
		
		
	}
	
	public byte[] eliminateVowels(byte[] args){
		//removes the bytes with ANSCII valued equivalent to vowels
		//parameter: "len" byte array representing alpha-numeric characters (no null characters)
		//return: "len" byte array without any number that in ANSCII is a vowel
		
		//declare the length of the array to be used
		int len = 20;
		
		//Check the incoming array is 20 bytes
		if(args.length!=len){
			System.out.println("number of Bytes not equal to 20");
			System.exit(1);
		}
		
		//check there are no null characters in array
		for(int i = 0; i<len;i++){
			if(args[i] == 0){
				System.out.println("null character found");
				System.exit(1);
			}	
		}
		

		
		//create a string that will hold the ANSCII character values of the bytes
		String s = new String(args);
		s = s.replaceAll("[aeiouAEIOU]", "");	//replace the vowels
		
		//create the byte array that will hold the new String without vowels
		byte[] noVowels = s.getBytes();
		
		System.out.println("Vowels eliminated!");
		
		return noVowels;
	}//end eliminateVowels

	public void sendDatagram(){
		//sends back the user datagram without vowels
		//Pre-condition: Client has to have sent a package before
		//parameters: array of bytes containing the no vowel data
		//return: void
		
		//create the data that will be sent
		byte[] data = this.eliminateVowels(receivePacket.getData());
		
		//initialize the sendPacket with the no-vowel data
		sendPacket = new DatagramPacket(data,data.length,
				receivePacket.getAddress(), 5000);
		
		//send datagram packet back to client 
		try {
			sendSocket.send(sendPacket);
		}catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println(" Packet sent!");
	}

	public static void main(String[] args){
		Server server1 = new Server(23);
		Server server2 = new Server(69);
		
		for(;;){
			server1.receiveDatagram();
			server1.sendDatagram();
			
			server2.receiveDatagram();
			server2.sendDatagram();
			
		}

	}
}
