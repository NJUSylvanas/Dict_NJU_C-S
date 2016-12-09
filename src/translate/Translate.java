package translate;

import translate.*;

public class Translate {
	public static String Trans(String[] op){
		String result="";
		String select = op[1];
		String word = op[2];
		String[] trans = new String[3];
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
		result = "202"+"#"+WordSort(word)+"#"+trans[0]+"#"+trans[1]+"#"+trans[2];
		//result.replace('\n', '$');
		return result;
	}
	
	public static String WordSort(String word){
		String result = "123";
		
		return result;
	}
}
