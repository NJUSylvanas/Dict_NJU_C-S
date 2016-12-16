package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import translate.*;

import Database.*;

public class MyServer {
	public HashMap<String, Socket> pool;
	//public Vector<Socket> p;
	
	public static void main(String[] args){
		 new MyServer();
	}
	
	public MyServer(){
		pool = new HashMap<>();
	//	p = new Vector<>();
		try{
			 int port = 9999;
			 int clientno = 0;
			 ServerSocket server = new ServerSocket(port);
			 while(true){		 
				 Socket socket = server.accept();
			//	 p.add(socket);
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
			String name="";
			// TODO 自动生成的方法存根
			try{
				BufferedReader in;
				PrintWriter out;
				
				while(true){
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				String ss = in.readLine();//接收客户端数据
				{
					System.out.println(ss);
					String[] op =ss.split("#");
					name = op[1];
					switch(Integer.parseInt(op[0])){
					case 101://注册
						Data data1 = new Data();
						String[] user1 = new String[2];
						user1[0]=op[1];
						user1[1]=op[2];
						String result1 = data1.Register(user1);
						try {
							data1.close();
						} catch (Exception e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						out.println(result1);
						break;
					case 102://登录
						String[] user2 = new String[2];
						user2[0]=op[1];
						user2[1]=op[2];
						Data data2 = new Data();
						String result2= data2.Login(user2);
						if(result2.equals("107"))
							pool.put(user2[0], socket);
						data2.close();
						out.println(result2);
						break;
					case 103://修改密码
						String[] user3=new String[3];
						user3[0]=op[1];
						user3[1]=op[2];
						user3[2]=op[3];
						Data data3 = new Data();
						String result3 = data3.ChangePassword(user3);
						data3.close();
						out.println(result3);
						break;
					case 104://退出
						Data data4= new Data();
						data4.Exit(op[1]);
						pool.remove(socket);
						data4.close();
						break;
					case 201://非登录状态下查询
						//out.println(Translate.Trans(op));
						String result5 = Translate.Trans(op);
						result5 =  "202"+"#"+result5;
						out.println(result5);
						break;
					case 203://登录状态下查询
						String result6 = Translate.Trans(op);
						Data data11 = new Data();
						data11.UserInsertWord(op[3], op[2]);
						result6 = "204"+"#"+result6+"#"+data11.GetLiked(op[3], op[2]);
						out.println(result6);
						break;
					case 301://金山点赞
						Data data5= new Data();
						data5.Like(op[1], op[2], "JS");
						data5.close();
						break;
					case 302://金山取消赞
						Data data6= new Data();
						data6.UnLike(op[1], op[2], "JS");
						data6.close();
						break;
					case 303://有道点赞
						Data data7= new Data();
						data7.Like(op[1], op[2], "YD");
						data7.close();
						break;
					case 304://有道取消赞
						Data data8= new Data();
						data8.UnLike(op[1], op[2], "YD");
						data8.close();
						break;
					case 305://必应点赞
						Data data9= new Data();
						data9.Like(op[1], op[2], "Bing");
						data9.close();
						break;
					case 306://必应取消赞
						Data data10= new Data();
						data10.UnLike(op[1], op[2], "Bing");
						data10.close();
						break;
					case 401://分享
						break;
					
					}
					
					//String temp = new String("Hi!\nI'm Server.");
					//out.println(temp);//返回数据给客户端
					}
				}
			}
			catch(IOException e){
				System.out.println(e);
				if(e.getMessage().equals("Connection reset")){
					try {
						Data data4= new Data();
						data4.Exit(name);
						pool.remove(socket);
						data4.close();
						socket.close();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				}
			}
			
		}
		
	}
}
