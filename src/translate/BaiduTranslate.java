package translate;

import java.io.BufferedReader;
import java.util.*;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class BaiduTranslate {
	
	public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            URL realUrl = new URL(urlNameString);
           
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("encoding", "UTF-8");
            connection.connect();
            
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            int start = result.indexOf("<div class=\"en-content\">");
            if(start==-1){
            	result ="";
            }
            else{
            	result = result.substring(start);
            	start = result.indexOf("<p><strong>");
            	result = result.substring(start);
            	int end = result.indexOf("</div>");
            	result=result.substring(0, end);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
       
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        
        return result;
    }
	
	public static String BaiDuSearch(String word){
	
		String result="";
		String undo_result = sendGet("http://dict.baidu.com/s?wd="+word);
		Pattern p1 = Pattern.compile("<strong>(.*?)</strong>");
		Pattern p2 = Pattern.compile("<span>(.*?)</span>");
		Matcher m1 = p1.matcher(undo_result);
		Matcher m2 = p2.matcher(undo_result);
		while(m1.find()&&m2.find()){
			result += (m1.group(1)+" "+m2.group(1)+"\n");
		}
		return result;
	}

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		Scanner in = new Scanner(System.in);
		String word = in.nextLine();
		String s = BaiduTranslate.BaiDuSearch(word);
		System.out.println(s);

	}

}
