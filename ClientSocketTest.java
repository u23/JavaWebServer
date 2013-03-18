package javaServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class ClientSocketTest {
	
	public static void main(String[] args) {
		try {
			InetAddress local=InetAddress.getLocalHost();
			String ip=local.getHostAddress();
			Socket sc=new Socket(ip,9901);		//�����Ǳ��ص�ip��ַ�򿪶˿�
			DataInputStream din=new DataInputStream(sc.getInputStream());
			DataOutputStream dout=new DataOutputStream(sc.getOutputStream());
			dout.writeUTF("hello");
			System.out.println(din.readUTF());
			din.close();
			dout.close();
			sc.close();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

}
