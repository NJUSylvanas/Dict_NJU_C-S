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


public class YoudaoTranslate {
	
	public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url ;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("encoding", "UTF-8");
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            int start = result.indexOf("\"explains\":")+12;
            if(start==11){
            	result ="";
            }
            else{
            	int end = result.indexOf("\"query\":")-3;
            	result=result.substring(start, end);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
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
	
	public static String YoudaoSearch(String word){
		String url = "http://fanyi.youdao.com/openapi.do?keyfrom=dict-nju&key=171155550&type=data&doctype=json&version=1.1&q="
				+ word;
		String undo_result = sendGet(url);
		
		String result = "";
		Pattern p = Pattern.compile("\"(.*?)\"");
		Matcher m = p.matcher(undo_result);
		while(m.find()){
			result+=m.group(1)+"\n";
		}
		return result;
	}
	public static void main(String []args){
		Scanner s = new Scanner(System.in);
		String word = s.nextLine();
		System.out.print(YoudaoTranslate.YoudaoSearch(word));
		
	}

}
