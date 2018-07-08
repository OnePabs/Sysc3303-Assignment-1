
public class Server2 extends Server{
	
	public Server2() {
		super(69);
	}
	public static void main(String[] args) {
		Server2 s2 = new Server2();
		for(;;){ 
			s2.receiveDatagram();
			s2.sendDatagram();
		}
	}

}
