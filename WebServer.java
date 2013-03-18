package javaServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * �����࣬��HttpRequestһ��������
 * @author asus
 *
 */
public final class WebServer {
	public static void main(String[] args) throws IOException {
		int port=9980;
		ServerSocket ss=new ServerSocket(port);
		while(true){
			Socket socket=ss.accept();
			HttpRequest request=new HttpRequest(socket);
			Thread thread=new Thread(request);
			thread.start();
		}
	}

}
