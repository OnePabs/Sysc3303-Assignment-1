
public class Server1 extends Server{
	
	public Server1() {
		super(23);
	}
	public static void main(String[] args) {
		Server1 s1 = new Server1();
		for(;;){ 
			s1.receiveDatagram();
			s1.sendDatagram();
		}
	}

}
