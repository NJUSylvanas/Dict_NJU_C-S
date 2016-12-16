package translate;

import translate.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.*;

import Database.*;

public class Translate {
	public static String Trans(String[] op){
		String result="";
		String select = op[1];
		String word = op[2];
		String[] trans = new String[3];
		Data data = new Data();
		data.InsertWord(word);
		if(select.charAt(0)=='1')
			trans[0]= JinshanTranslate.JinshanSearch(word);
		else
			trans[0]="@NULL";
		if(select.charAt(1)=='1')
			trans[1]=YoudaoTranslate.YoudaoSearch(word);
		else
			trans[1]="@NULL";
		if(select.charAt(2)=='1')
			trans[2]=BingTranslate.BingSearch(word);
		else
			trans[2]="@NULL";
		if(select.equals("000")){
			trans[0]= JinshanTranslate.JinshanSearch(word);
			trans[1]=YoudaoTranslate.YoudaoSearch(word);
			trans[2]=BingTranslate.BingSearch(word);
		}
		//result =trans[0]+"#"+trans[1]+"#"+trans[2];
		int[] temp = WordSort(word);
		result =trans[0]+"#"+Integer.toString(temp[0])+"#"+trans[1]+"#"+Integer.toString(temp[1])+
				"#"+trans[2]+"#"+Integer.toString(temp[2]);
		return result;
	}
	
	public static int[] WordSort(String word){
		int JS=0,YD=0,Bing=0;
		Data data = new Data();
		String sql = "SELECT * FROM Words WHERE word='"+word+"'";
		ResultSet rs = data.SearchWord(sql);
		try {
			while(rs.next()){
				JS = rs.getInt("JS");
				YD = rs.getInt("YD");
				Bing = rs.getInt("Bing");
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
		}
		int[] result=new int[3];
		result[0]=JS;
		result[1]=YD;
		result[2]=Bing;
		return result;
	}
}
