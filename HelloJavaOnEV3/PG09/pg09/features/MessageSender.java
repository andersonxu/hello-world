package pg09.features;

import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Helper class to send a message via socket, from robot to PC-end
 * 
 * @author sunruoxi
 *
 */
public class MessageSender {
	private Socket socket;
	private OutputStream ostream;
	private PrintWriter writer;
	
	public MessageSender(String ipAddress, int port) throws Exception{
		this.socket = new Socket(ipAddress, port);
		this.ostream = socket.getOutputStream();
		this.writer = new PrintWriter(ostream, true);
	}
	
	public void send(String message) {
		writer.println(message);
		writer.flush();
	}
	
	public void close() throws IOException {
		socket.close();
	}

	public Socket getSocket() {
		return socket;
	}

	public OutputStream getOstream() {
		return ostream;
	}

	public PrintWriter getWriter() {
		return writer;
	}
}
