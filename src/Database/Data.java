package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Data {
	static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=Java_Dict";
	static String userName="chengshuo";		
	static String userPwd="123456";
	
	static Connection dbConn;
	static Statement statement;
	
	public  Data(){	
		try {
			Class.forName(driverName);
			dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
		    //System.out.println("连接数据库成功");
		    statement = dbConn.createStatement();
			//dbConn.close();
		    } catch(Exception e) {
		    	e.printStackTrace();
		    	System.out.print("连接失败");
		    }
	}
	
	public String Register(String[] user){
		String result="";
		String sql;
		sql = "INSERT INTO Users VALUES('"+user[0]+"','"+user[1]+"',0)";
		try {
			boolean rs = statement.execute(sql);
			result = "103";
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
			result = "104";
		}
		String table = user[0]+"_Word";
		String sql2 = "CREATE TABLE "+table+"( Word char(30) NOT NULL, JS int NOT NULL, YD int NOT NULL, Bing int NOT NULL,PRIMARY KEY(Word))";
		try {
			statement.executeUpdate(sql2);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
		}
		
		return result;
	}
	
	public String Login(String[] user){
		String result="108";
		String sql;
		sql="SELECT * FROM Users WHERE Username='"+user[0]+"'";
		try {
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				String Password = rs.getString("Password").trim();
				if(Password.equals(user[1])){
					result = "107";
					String set = "UPDATE Users SET Online=1 WHERE Username='"+user[0]+"'";
					try{
						statement.executeUpdate(set);
					}
					catch (SQLException e){
						e.printStackTrace();
					}
				}
				else
					result ="108";
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
			//result ="108";
			//int a =0;
		}
		
		return result;
	}
	public void close() {
		try {
			dbConn.close();
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	public void Exit(String user){
		String sql;
		sql="UPDATE Users SET Online=0 WHERE Username='"+user+"'";
		try{
			statement.executeUpdate(sql);
		}catch (SQLException e){
			
		}
	}
	public String ChangePassword(String[] user){
		String result = "105";
		String sql;
		sql = "UPDATE Users SET Password='"+user[2]+"' WHERE Username='"+user[0]+"'"+" AND Password='"+user[1]+"'";
		try{
			statement.executeUpdate(sql);
			ResultSet rs = statement.executeQuery("SELECT * FROM Users WHERE Username='"+user[0]+"'"+" AND Password='"+user[2]+"'");
			if(rs.next())
				result = "106";
		}
		catch (SQLException e){
			
		};
		return result;
	}
	public ResultSet SearchWord(String sql){
		ResultSet rs = null;
		try {
			rs = statement.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
		}
		return rs;
		
	}
	public void InsertWord(String word){
		String sql = "INSERT INTO Words VALUES('"+word+"',0,0,0)";
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
		}
	}
	public void Like(String user,String word,String type){
		String sql1 = "UPDATE Words SET "+type+"="+type+"+1 WHERE word='"+word+"'";
		String sql2 = "UPDATE "+user+"_Word SET "+type+"=1 WHERE Word='"+word+"'";
		try {
			statement.executeUpdate(sql1);
			statement.executeUpdate(sql2);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void UnLike(String user,String word,String type){
		String sql1 = "UPDATE Words SET "+type+"="+type+"-1 WHERE word='"+word+"'";
		String sql2 = "UPDATE "+user+"_Word SET "+type+"=0 WHERE word='"+word+"'";
		try {
			statement.executeUpdate(sql1);
			statement.executeUpdate(sql2);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
		}
	}
	
	public void UserInsertWord(String user,String word){
		String sql = "INSERT INTO "+user+"_Word VALUES('"+word+"',0,0,0)";
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			//e.printStackTrace();
		}
		
	}
	
	public String GetLiked(String user,String word){
		String result="";
		
		String sql="SELECT* FROM "+user+"_Word WHERE Word='"+word+"'";
		try {
			ResultSet rs = statement.executeQuery(sql);
			int a =0;
			while(rs.next()){
				int JS=rs.getInt("JS");
				int YD=rs.getInt("YD");
				int Bing =rs.getInt("Bing");
				result=Integer.toString(JS)+Integer.toString(YD)+Integer.toString(Bing);
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return result;
	}
	
	public String GetOnline(String user){
		String result="";
		String sql="SELECT * FROM Users WHERE Online = 1 AND Username !='"+user+"'";
		try {
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				String temp=rs.getString("Username").trim();
				result = result+"#"+temp;
			}
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String[] args) throws SQLException{
		Data database=new Data();
		String[] user = {"Bob","password"};
		database.Register(user);
		database.Login(user);
		database.close();
	}

}
