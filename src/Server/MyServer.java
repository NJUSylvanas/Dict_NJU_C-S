package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {
	public static void main(String[] args){
		 try{

			 int port = 9999;
			 ServerSocket mySocket = new ServerSocket(port);
			 Socket sk = mySocket.accept();
			 BufferedReader in = new BufferedReader (new InputStreamReader(sk.getInputStream ()));
			 PrintWriter out = new PrintWriter (new BufferedWriter(new OutputStreamWriter(
			 sk.getOutputStream ())), true);
			 String ss;
			 ss=in.readLine();
			 System.out.println("客户端信息:"+ss);
			 String str[] = ss.split(" ");
			 int a[] = new int[str.length];
			 for(int i=0;i<str.length;i++){
			    a[i]=Integer.parseInt(str[i]);
			 }
			 int sum=0,j;
			 for(j=0;j<a.length;j++){
			    sum=sum+a[j];
			 }    
			 out.println("你好，我是服务器。我使用的端口号： "+port+"得到的数据总和为："+sum);
			 sk.close();
			 }catch(Exception e){
			 System.out.println(e);
			 }
	}
}
