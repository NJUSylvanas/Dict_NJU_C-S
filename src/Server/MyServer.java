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
			 ServerSocket share = new ServerSocket(8000);
			 while(true){		 
				 Socket socket = server.accept();
				 Socket share_socket = share.accept();
			//	 p.add(socket);
				 HandleClient task = new HandleClient(socket,share_socket);
				 new Thread(task).start();
				 clientno++;
			 }
			 }catch(Exception e){
			 System.out.println(e);
			 }
	}
	
	class HandleClient implements Runnable {
		private Socket socket;
		private Socket share_socket;
		public HandleClient(Socket socket,Socket share_socket) {
			// TODO �Զ����ɵĹ��캯�����
			this.socket = socket;
			this.share_socket = share_socket;
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
							pool.put(user2[0], share_socket);
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
						pool.remove(share_socket);
						out.println("109");
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
						data11.close();
						break;
					case 301://��ɽ����
						Data data5= new Data();
						data5.Like(op[1], op[2], "JS");
						data5.close();
						out.println("301");
						break;
					case 302://��ɽȡ����
						Data data6= new Data();
						data6.UnLike(op[1], op[2], "JS");
						data6.close();
						out.println("302");
						break;
					case 303://�е�����
						Data data7= new Data();
						data7.Like(op[1], op[2], "YD");
						data7.close();
						out.println("303");
						break;
					case 304://�е�ȡ����
						Data data8= new Data();
						data8.UnLike(op[1], op[2], "YD");
						data8.close();
						out.println("304");
						break;
					case 305://��Ӧ����
						Data data9= new Data();
						data9.Like(op[1], op[2], "Bing");
						data9.close();
						out.println("305");
						break;
					case 306://��Ӧȡ����
						Data data10= new Data();
						data10.UnLike(op[1], op[2], "Bing");
						data10.close();
						out.println("306");
						break;
					case 401://����
						String[] users=new String[op.length-2];
						for(int i=0;i<op.length-2;i++)
							users[i]=op[i+2];
						out.println("402");
						Socket my_socket = pool.get(op[1]);
						DataInputStream dis = null;
					    FileOutputStream fos = null;
						byte[] inputByte = new byte[102400000];
						dis = new DataInputStream(socket.getInputStream());
						fos = new FileOutputStream(new File("D:\\test.jpg"));
						int length=0;
						length = dis.read(inputByte, 0, inputByte.length);
						while (length > 0) {
		//					 System.out.println(length);
			                 fos.write(inputByte, 0, length);
			                 fos.flush();
			                 length = dis.read(inputByte, 0, inputByte.length);
			            }
						for(int i=0;i<users.length;i++){
							Socket temp_socket = pool.get(users[i]);
							
						    DataOutputStream dos = null;
						    FileInputStream fis = null;
						   
						    dos = new DataOutputStream(temp_socket.getOutputStream());
						    fis = new FileInputStream(new File("D:\\test.jpg"));
						    byte[] sendBytes = new byte[1024];
						    while ((length = fis.read(sendBytes, 0, sendBytes.length)) > 0) {
			                    dos.write(sendBytes, 0, length);
			                    dos.flush();
			                }
					        
						}
						break;
					case 501:
						Data data12 = new Data();
						String result12 = data12.GetOnline(op[1]);
						result12 = "502"+result12;
						out.println(result12);
						data12.close();
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
						pool.remove(share_socket);
						data4.close();
						PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
						out.println("109");
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
