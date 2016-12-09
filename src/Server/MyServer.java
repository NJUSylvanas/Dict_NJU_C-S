package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import translate.*;

public class MyServer {
	public static void main(String[] args){
		 new MyServer();
	}
	
	public MyServer(){
		try{
			 int port = 9999;
			 int clientno = 0;
			 ServerSocket server = new ServerSocket(port);
			 while(true){
				 Socket socket = server.accept();
				 HandleClient task = new HandleClient(socket);
				 new Thread(task).start();
				 clientno++;
			 }
			 }catch(Exception e){
			 System.out.println(e);
			 }
	}
	
	class HandleClient implements Runnable {
		private Socket socket;			
		public HandleClient(Socket socket) {
			// TODO �Զ����ɵĹ��캯�����
			this.socket = socket;
		}
		
		@Override
		public void run() {
			// TODO �Զ����ɵķ������
			try{
				BufferedReader in;
				PrintWriter out;
				while(true){
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				String ss = in.readLine();//���տͻ�������
				System.out.println(ss);
				String[] op =ss.split("#");
				switch(Integer.parseInt(op[0])){
				case 101://ע��
					break;
				case 102:
					break;
				case 103:
					break;
				case 104:
					break;
				case 201:
					out.println(Translate.Trans(op));
					break;
				case 301:
					break;
				case 302:
					break;
				case 303:
					break;
				case 304:
					break;
				case 305:
					break;
				case 306:
					break;
				case 401:
					break;
				
				}
				
				//String temp = new String("Hi!\nI'm Server.");
				//out.println(temp);//�������ݸ��ͻ���
				}
			}
			catch(IOException e){
				System.out.println(e);
			}
			
		}
		
	}
}
