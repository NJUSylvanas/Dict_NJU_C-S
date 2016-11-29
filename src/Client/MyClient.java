package Client;

import java.io.*;
import java.net.*;

public class MyClient {
	public static void main(String[] arges) {

		  try {

		  // InetAddress addr = InetAddress.getByName(null);
		   InetAddress addr = InetAddress.getLocalHost();
		   Socket sk = new Socket(InetAddress.getLocalHost(), 9999);

		 

		   BufferedReader in = new BufferedReader(new InputStreamReader(sk.getInputStream()));

		   PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sk.getOutputStream())), true);

		    InputStreamReader str=new InputStreamReader(System.in);
		                       char[] a=new char[100];
		                       str.read(a);
		                      out.println(new String(a));

		   System.out.println(in.readLine());

		  } catch (Exception e) {

		   System.out.println(e);

		  }

		 }
}
