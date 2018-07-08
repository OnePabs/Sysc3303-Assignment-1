
import java.net.*;
import java.io.*;


public class Proxy {
	
	//fields
	int currServerPort; //holds the port number of the current server in use (23 or 69)
	int clientPort; //holds the port number of the client
	int proxyPort = 5000; //port number of the proxy
	
	DatagramPacket receivePacket, sendPacket;
	DatagramSocket receiveSocket, sendSocket;
	
	public Proxy(){
		//initialize sockets so proxy port is 5000
		try{
			receiveSocket = new DatagramSocket(5000);
			sendSocket = new DatagramSocket();
		}catch(SocketException se){
			se.printStackTrace();
			System.exit(1);
		}
	}
	
	public void receiveDatagramFromClient(){
		//listens to channel 5000 for a package to be received
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
	
	public void sendDatagramToPort(int port){
		
		//create data 
		byte[] data = receivePacket.getData();
		
		//initialize the sendPacket 
		try{
			sendPacket = new DatagramPacket(data,data.length,
			InetAddress.getLocalHost(), port);
		}catch(UnknownHostException e) {
			e.printStackTrace();
			System.exit(1);
		}

		
		//send datagram to server
		try {
			sendSocket.send(sendPacket);
		}catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println(" Packet sent!");
	}
	
	
	public static void main(String[] args){
		Proxy p = new Proxy();
		for(;;){
			p.receiveDatagramFromClient(); //get data from client
			p.sendDatagramToPort(23);	//send to server 1
			p.receiveDatagramFromClient();	//get data from server 1
			p.sendDatagramToPort(13); //send to client
			
			
			p.receiveDatagramFromClient(); //get data from client
			p.sendDatagramToPort(69);	//send to server 1
			p.receiveDatagramFromClient();	//get data from server 1
			p.sendDatagramToPort(13); //send to client
		}
	}
}
