package testing.communication;
import java.io.*;
import java.net.*;

/**
 * PC-end socket message server
 * Receive and print messages from client-end
 * 
 * @author sunruoxi
 *
 */
public class GossipServer
{
	public static void main(String[] args) throws Exception{
		// the socket port number must be as same as the client port number
		ServerSocket sersock = new ServerSocket(3000);
		System.out.println("Server  ready for chatting");
		Socket sock = sersock.accept();                          

		InputStream istream = sock.getInputStream();
		BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

		String receiveMessage = "";  
		// if a END message is received, the server will be terminated
		while(receiveMessage != "END"){
			if((receiveMessage = receiveRead.readLine()) != null){
				System.out.println(receiveMessage);
			}
		}
		sersock.close();
	}
}