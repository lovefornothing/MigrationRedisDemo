package norway.main.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

public class HttpPostHelper {
	private static HttpPostHelper instance = new HttpPostHelper();
	
	public static HttpPostHelper getInstance( ) {
		return HttpPostHelper.instance;
	}
	
    public int sendGet(String url) throws Exception{
        JSONObject jsonObject = null;
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{

            client = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(url);
            //uriBuilder.addParameters(nameValuePairList);
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            httpGet.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
            httpGet.setHeader(new BasicHeader("Accept", "application/json;charset=utf-8"));
            //httpGet.setHeader(new BasicHeader("Host", "mavibet.nwacdn.com"));
            httpGet.setHeader(new BasicHeader("Cf-IpCountry", "RO"));
            response = client.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity,"UTF-8");

            System.out.println(result);
            
            if (200 == statusCode){
               
                try{
                    //jsonObject = JSONObject.parseObject(result);
                    return 200;
                }catch (Exception e){
                    return 200;
                }
            }else{
                return statusCode;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            response.close();
            client.close();
        }
        return 404;
    }
    
    public int sendPost(String url, List<String> gameIdList) throws Exception{
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try{

            client = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(url);
            //uriBuilder.addParameters(nameValuePairList);
            HttpPost httpPost = new HttpPost(uriBuilder.build());
            		
            //String encoderJson = URLEncoder.encode(jsonStr, HTTP.UTF_8);
            
            httpPost.addHeader(new BasicHeader("Content-Type", "application/json"));
            httpPost.setHeader(new BasicHeader("Accept", "application/json;"));
            
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("items", gameIdList);
            StringEntity s = new StringEntity(jsonObject.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpPost.setEntity(s);
            
            
            response = client.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            HttpEntity entity = response.getEntity();

            String result = EntityUtils.toString(entity,"UTF-8");

            //System.out.println(result);
            
            if (200 == statusCode){
               
                try{
                    //jsonObject = JSONObject.parseObject(result);
                    return 200;
                }catch (Exception e){
                    return 200;
                }
            }else{
                return statusCode;
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            response.close();
            client.close();
        }
        return 404;
    }
    
    public static void main(String [] args) {
    	String url = "https://kavbet-api.stage.norway.everymatrix.com/v1/player/3484078/favorites";
    	
    	try {
			//int statusCode = HttpPostHelper.getInstance().sendPost(url, jsonStr);
    		int statusCode = HttpPostHelper.getInstance().sendGet(url);
			System.out.println("status code:" + statusCode);
			
			List<String> gameIdList = new ArrayList<String>();
			gameIdList.add("36312");
			statusCode = HttpPostHelper.getInstance().sendPost(url, gameIdList);
			System.out.println("status code:" + statusCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
