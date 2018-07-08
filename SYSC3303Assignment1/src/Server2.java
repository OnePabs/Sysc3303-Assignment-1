
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
