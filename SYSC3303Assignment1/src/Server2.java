/*
 * Server wne extends server, so a thread for this specific server is created.
 * @author Kevin Ho (100997967) and Juan Paulo Contreras(101006952)
 * @version July 10, 2018 
 */

public class Server2 extends Server{
	//calling superclass constructor to create a server at port 69
	public Server2() {
		super(69);
	}
	public static void main(String[] args) {
		//creating the server and running it until timeout 
		Server2 s2 = new Server2();
		for(;;){ 
			s2.receiveDatagram();
			s2.sendDatagram();
		}
	}

}
