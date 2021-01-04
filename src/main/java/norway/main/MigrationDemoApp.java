package norway.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.json.simple.JSONObject;

import com.microsoft.sqlserver.jdbc.StringUtils;

import norway.main.db.DBHelper;
import norway.main.http.HttpPostHelper;
import norway.main.json.JSONHelper;
import norway.main.url.URLHelper;

public class MigrationDemoApp{
    //private static final Map<String, CasinoGame> gameList = new HashMap<String, CasinoGame>();
    
    private final Map<Long, List<String>> userIdToGameIdListMap = new ConcurrentHashMap<Long, List<String>>();
    private final List<String> gameIdList = new ArrayList<String>();
    
    //private static final Map<String, List<CasinoGame>> finalPlayerIdToGameListMap = new ConcurrentHashMap<String, List<CasinoGame>>();
    
    //private static LettuceRedisClient redisClient = new LettuceRedisClient();
    
    public Map<String, String> domainIdToDomainMap = new HashMap<String, String>();
    private long gameCount_Total = 0L;
    
    
    public static void main(String args[]){  
    	if (args.length == 0) {
    		System.out.println("invalid args. the args length is 0. exit.");
    		System.exit(0);
    	}
    	
    	if (false == StringUtils.isNumeric(args[0])) {
    		System.out.println("invalid args. The first args is not numeric. exit.");
    		System.exit(0);
    	}
    	
    	long mode = Long.valueOf(args[0]);
    	
    	if (mode == 1) {
    		if (args.length != 7) {
        		System.out.println("mode 1: invalid args. the args number is not equal 7. exit.");
        		System.exit(0);
        	}
    		
    		runMode1_queryDBAndGenerateJSON(args[1], args[2], args[3], args[4], args[5], args[6]);
    	}
    	else if (mode == 2) {
    		if (args.length != 4) {
        		System.out.println("mode 2: invalid args. The args number is not equal 4. exit.");
        		System.exit(0);
        	}
    		
    		runModel2_httpPostFavorites(args[1], args[2], args[3]);
    	}
    	else {
    		System.out.println("invalid args. mode is invalid. exit. mode:" + mode);
    		System.exit(0);
    	}
    	
    	

    	
    	//fillGame();    
    	//app.testSendSingle();
    	//System.out.println(obj.toJSONString());
    }
    
    public static void runModel2_httpPostFavorites(String path, String domainId, String url) {
    	//url = "https://kavbet-api.stage.norway.everymatrix.com/v1/player/3484078/favorites";
    	try {
    		JSONObject json =JSONHelper.getInstance().read(path, domainId);
    		System.out.println("JSON result for domainId:" + domainId + ", content:" + json);
    		Map<String, List<String>> userToGameIdList = JSONHelper.getInstance().parseJsonToMap(json);
    		
    		Iterator<Entry<String, List<String>>> userIterator =userToGameIdList.entrySet().iterator();
    		while (userIterator.hasNext()) {
    			System.out.println();
    			long starttime = System.currentTimeMillis();
    			Entry<String, List<String>> entry = userIterator.next();
    			String postURL = URLHelper.getInstance().getFavoritesURL(url, entry.getKey());
    			
    			System.out.println("Processing post favorites. domainId: " + domainId + " -- userId:" + entry.getKey() + "--gameId:" + JSONHelper.getInstance().getJsonStrFromList(entry.getValue()));
    			int statusCode = HttpPostHelper.getInstance().sendPost(postURL, entry.getValue());
    			System.out.println();
    			
    			System.out.println("statucCode:" + statusCode + "--spentTime:" + (System.currentTimeMillis()-starttime));
    			System.out.println();
    			System.out.println();
    			System.out.println();
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void runMode1_queryDBAndGenerateJSON(String url, String dbName, String username, String password, String sql, String directory) {    	    	
    	DBHelper.getInstance().initParameter(url, dbName, username, password, sql);
    	
    	JSONObject obj = new JSONObject();    	    	
    	obj = DBHelper.getInstance().queryDB();
    	JSONHelper.getInstance().writeJsonToFile(obj, directory);
    }    
}