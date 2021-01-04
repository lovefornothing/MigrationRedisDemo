package norway.main.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;


public class DBHelper {
    private String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String url = ""; //"jdbc:sqlserver://gm-core-db-sql-stage.everymatrix.local;databasename=";
    private String dbName = "";//"cm";
    private String username = "";//"cmapp";
    private String password = "";//"cmapp123";
    //private String SQL = "SELECT * FROM dbo.cmCasinoFavoriteGame where DomainId=1999";
    private String sql = "";//"SELECT * FROM dbo.cmCasinoFavoriteGame";
    static private DBHelper instance =  new DBHelper();
	
	public static DBHelper getInstance() {
		return DBHelper.instance;
	}
	
	public void initParameter(String url, String dbName, String username, String password, String sql) {
		this.url = url;
		this.dbName = dbName;
		this.username = username;
		this.password = password;
		this.sql = sql;
	}
	
	public JSONObject queryDB() {
		//Map<String, HashMap<String, List<String>>> dataMap = new HashMap<String, HashMap<String, List<String>>>();
		System.out.println("url:" + this.url);
		System.out.println("db:" + this.dbName);
		System.out.println("username:" + this.username);
		System.out.println("password:" + this.password);
		System.out.println("sql:" + this.sql);
		
		System.out.println();
		System.out.println();
		
		JSONObject dataJSON = new JSONObject();
        try{
            Class.forName( driver );           
            Connection conn = DriverManager.getConnection(url+dbName,username,password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);                         
            
            
            while ( rs.next() ) {                
            	storeData_InJSON(rs.getLong("DomainID"), rs.getLong("UserID"), rs.getString("GameID"), dataJSON);                
            }            
            
            rs.close();
            stmt.close();
            conn.close();          
        }catch(Throwable e){
            e.printStackTrace();
        }
        
        return dataJSON;
    }
	
	public void storeData(Long domainId, Long userId, String gameId, Map<String, HashMap<String, List<String>>> dataMap) {
		HashMap<String, List<String>> domainMap = dataMap.get(domainId.toString());
		if (null == domainMap) {
			domainMap = new HashMap<String, List<String>>();
		}
		
		List<String> gameList = domainMap.get(userId.toString());
		if (null == gameList) {
			gameList = new ArrayList<String>();
		}
		
		if (!gameList.contains(gameId)) {
			gameList.add(gameId);
		}
		
		domainMap.put(userId.toString(), gameList);
		dataMap.put(domainId.toString(), domainMap);		
	}
	
	public void storeData_InJSON(Long domainId, Long userId, String gameId, JSONObject dataJSON) {
		Object domainObject = dataJSON.get(domainId.toString());
		if (null == domainObject) {
			domainObject = new JSONObject();
		}
		
		if (false == (domainObject instanceof JSONObject)) {
			throw new RuntimeException("domainObject is not an instance of JSONObject.");
		}
		
		JSONObject domainJSON = (JSONObject)domainObject;
		Object gameObject = domainJSON.get(userId.toString());
		
		if (null == gameObject) {
			gameObject = new ArrayList<String>();
		}
		
		List<String> gameList = (List<String>)gameObject;
		if (!gameList.contains(gameId)) {
			gameList.add(gameId);
		}
		
		domainJSON.put(userId.toString(), gameList);
		dataJSON.put(domainId.toString(), domainObject);
	}
}
