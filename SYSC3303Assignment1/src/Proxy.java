
import java.net.*;
import java.io.*;
import java.util.Random;

/*
 * Proxy acts as a load balancer between client and server. Will randomly select one of two servers.
 * @author Kevin Ho (100997967) and Juan Paulo Contreras(101006952)
 * @version July 10, 2018
 */
public class Proxy {
	
	//fields
	private DatagramPacket receivePacket, sendPacket;
	private DatagramSocket receiveSocket, sendSocket;
	
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
	
	private void receiveDatagramFromClient(){
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
			System.out.println("Waiting for packet");
			receiveSocket.receive(receivePacket);
		}catch(IOException e){
			System.out.println("Packet not received");
			System.exit(1);
		}
		System.out.println(" Packet Received"); 
	}
	
	private void sendDatagramToPort(int port){
		
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
		System.out.println("  Packet sent to port " + port);
	}
	
	
	public static void main(String[] args){
		Proxy p = new Proxy();
		//random generator to choose one of the two servers
		Random rand = new Random();
		int n;
		for(int i = 0; i < 100; i++){
			n = rand.nextInt(2) + 1;
			if (n == 1) {
				System.out.println(i + ". Sending to port 23");
				p.receiveDatagramFromClient(); //get data from client
				p.sendDatagramToPort(23);	//send to server 1
				p.receiveDatagramFromClient();	//get data from server 1
				p.sendDatagramToPort(13); //send to client
			}
			else {
				System.out.println(i + ". Sending to port 69");
				p.receiveDatagramFromClient(); //get data from client
				p.sendDatagramToPort(69);	//send to server 1
				p.receiveDatagramFromClient();	//get data from server 1
				p.sendDatagramToPort(13); //send to client
			}
		}
		System.out.println("All messages sent");
		p.receiveSocket.close();
		p.sendSocket.close();
	}
}
