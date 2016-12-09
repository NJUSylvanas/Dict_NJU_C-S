package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
			// TODO 自动生成的构造函数存根
			this.socket = socket;
		}
		
		@Override
		public void run() {
			// TODO 自动生成的方法存根
			try{
				BufferedReader in;
				PrintWriter out;
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				while(true){
				String ss = in.readLine();//接收客户端数据
				System.out.println(ss);

				out.println("I've got it.");//返回数据给客户端
				}
			}
			catch(IOException e){
				System.out.println(e);
			}
			
		}
		
	}
}
