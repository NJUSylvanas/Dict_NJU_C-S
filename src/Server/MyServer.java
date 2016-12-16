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
			// TODO �Զ����ɵĹ��캯�����
			this.socket = socket;
		}
		
		@Override
		public void run() {
			String name="";
			// TODO �Զ����ɵķ������
			try{
				BufferedReader in;
				PrintWriter out;
				
				while(true){
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
				String ss = in.readLine();//���տͻ�������
				{
					System.out.println(ss);
					String[] op =ss.split("#");
					name = op[1];
					switch(Integer.parseInt(op[0])){
					case 101://ע��
						Data data1 = new Data();
						String[] user1 = new String[2];
						user1[0]=op[1];
						user1[1]=op[2];
						String result1 = data1.Register(user1);
						try {
							data1.close();
						} catch (Exception e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						}
						out.println(result1);
						break;
					case 102://��¼
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
					case 103://�޸�����
						String[] user3=new String[3];
						user3[0]=op[1];
						user3[1]=op[2];
						user3[2]=op[3];
						Data data3 = new Data();
						String result3 = data3.ChangePassword(user3);
						data3.close();
						out.println(result3);
						break;
					case 104://�˳�
						Data data4= new Data();
						data4.Exit(op[1]);
						pool.remove(socket);
						data4.close();
						break;
					case 201://�ǵ�¼״̬�²�ѯ
						//out.println(Translate.Trans(op));
						String result5 = Translate.Trans(op);
						result5 =  "202"+"#"+result5;
						out.println(result5);
						break;
					case 203://��¼״̬�²�ѯ
						String result6 = Translate.Trans(op);
						Data data11 = new Data();
						data11.UserInsertWord(op[3], op[2]);
						result6 = "204"+"#"+result6+"#"+data11.GetLiked(op[3], op[2]);
						out.println(result6);
						break;
					case 301://��ɽ����
						Data data5= new Data();
						data5.Like(op[1], op[2], "JS");
						data5.close();
						break;
					case 302://��ɽȡ����
						Data data6= new Data();
						data6.UnLike(op[1], op[2], "JS");
						data6.close();
						break;
					case 303://�е�����
						Data data7= new Data();
						data7.Like(op[1], op[2], "YD");
						data7.close();
						break;
					case 304://�е�ȡ����
						Data data8= new Data();
						data8.UnLike(op[1], op[2], "YD");
						data8.close();
						break;
					case 305://��Ӧ����
						Data data9= new Data();
						data9.Like(op[1], op[2], "Bing");
						data9.close();
						break;
					case 306://��Ӧȡ����
						Data data10= new Data();
						data10.UnLike(op[1], op[2], "Bing");
						data10.close();
						break;
					case 401://����
						break;
					
					}
					
					//String temp = new String("Hi!\nI'm Server.");
					//out.println(temp);//�������ݸ��ͻ���
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
						// TODO �Զ����ɵ� catch ��
						e1.printStackTrace();
					}
				}
			}
			
		}
		
	}
}
