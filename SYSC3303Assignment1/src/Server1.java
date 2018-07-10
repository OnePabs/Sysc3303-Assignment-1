/*
 * Server one extends server, so a thread for this specific server is created.
 * @author Kevin Ho (100997967) and Juan Paulo Contreras(101006952)
 * @version July 10, 2018 
 */

public class Server1 extends Server{
	//calling superclass constructor to create a server at port 23
	public Server1() {
		super(23);
	}
	public static void main(String[] args) {
		//creating the server and running it until timeout 
		Server1 s1 = new Server1();
		for(;;){ 
			s1.receiveDatagram();
			s1.sendDatagram();
		}
	}

}
