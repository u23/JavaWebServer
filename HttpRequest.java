package javaServer;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * ��WebServer һ�������࣬����������������ʵ�ֵ�һ����
 * @author asus
 *
 */
public final class HttpRequest implements Runnable {
	final static String CRLF = "\r\n";
	Socket socket;

	public HttpRequest(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		 try {
			processRequest();
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} 
	}

	private void processRequest() throws Exception {
		InputStream is = socket.getInputStream();
		DataOutputStream os = new DataOutputStream(socket.getOutputStream());
		// ���������ļ���
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		// ��ȡhttp������
		String requestLine = br.readLine();
		System.out.println(requestLine+"    requestLine"); // ��ӡ����
		String headLine;
		while ((headLine = br.readLine()).length() != 0) {
			System.out.println(headLine);
		}
		StringTokenizer tokens = new StringTokenizer(requestLine);
		tokens.nextToken();
		String fileName = tokens.nextToken();
		fileName = "." + fileName; // ��Ҫ���ļ�
		FileInputStream fis = null;
		boolean fileExists = true;
		try {
			fis = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			fileExists = false;
		} // ������Ӧ����
		String statusLine = null;
		String contentTypeLine = null;
		String entityBody = null;
		if (fileExists) {
			statusLine = "HTTP/1.0 200 OK" + CRLF;
			contentTypeLine = "Content-type: " + contentType(fileName) + CRLF;
		} else {
			statusLine = "HTTP/1.0 404 Not Found" + CRLF;
			contentTypeLine = "Content-type: text/html" + CRLF;
			entityBody = "<html>" + "<head><title>Not Found</title></head>"
					+ "<body>Not Found</body></html>";
		} // ����statusline
		os.writeBytes(statusLine); // ����content typeline.
		os.writeBytes(contentTypeLine);
		os.writeBytes(CRLF);
		if (fileExists) {
			sendBytes(fis, os);
			fis.close();
		} else {
			os.writeBytes(entityBody);
		}
		os.close();
		is.close();
		br.close();
		socket.close();
	}
	 private static void sendBytes(FileInputStream fis, OutputStream os) throws Exception {         
		 byte[] buffer = new byte[1024];           
		 int bytes = 0;            
		 while((bytes = fis.read(buffer)) != -1) {             
			 os.write(buffer, 0, bytes);         
			 }      
		 }       
	 private static String contentType(String fileName) {    
		 if (fileName.endsWith(".htm") || fileName.endsWith(".html")) {      
			 return "text/html";        
			 }             
		 if (fileName.endsWith(".gif")) {       
			 return "image/gif";          
			 }          
		 if (fileName.endsWith(".jpg")) {    
			 return "image/jpeg";           
			 }     
		 return "application/octet-stream";      
	}
	 
	   
}
