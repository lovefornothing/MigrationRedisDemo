package norway.main.json;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class JSONHelper {
	private static JSONHelper instance = new JSONHelper();
	
	public static JSONHelper getInstance() {
		return JSONHelper.instance;
	}
	
	public void writeJsonToFile(JSONObject dataJSON, String directory) {
		System.out.println("check and clean directory. dir:" + directory);
		checkAndCleanDirectory(directory);
		
		if (null == dataJSON) {
			throw new NullPointerException("dataJSON is null.");
		}
		
		Set<Entry<String, Object>> entrySet = dataJSON.entrySet();
		int domainNum = entrySet.size();
		
		System.out.println("domain number:" + domainNum);
		
		
		Iterator<Entry<String, Object>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			
			if (null == entry.getValue()) {
				throw new NullPointerException("entry's content is null. key:" + entry.getKey());
			}
									
			if (!(entry.getValue() instanceof JSONObject)) {
				throw new RuntimeException("obj is not instance of JSONObject. key:" + entry.getKey());
			}
			
			JSONObject jsonObj = (JSONObject)entry.getValue();
			write(jsonObj, directory + File.separator + entry.getKey());
			System.out.println("domain id:" + entry.getKey() + "-- user count:" + jsonObj.entrySet().size());
		}						
	}
	
	public void checkAndCleanDirectory(String directory) {
		checkDirectory(directory);
		
		File directoryFile = new File(directory);
		
		try {
			FileUtils.cleanDirectory(directoryFile);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
	
	public void write(JSONObject json, String path) {
		try (FileWriter file = new FileWriter(path)) {
			 
            file.write(json.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void checkDirectory(String directory) {
		File directoryFile = new File(directory);
		
		if (false == directoryFile.exists()) {
			throw new RuntimeException("directory is not existed. directory:" + directory);
		}
		
		if (false == directoryFile.isDirectory()) {
			throw new RuntimeException("directory is not a directory. directory:" + directory);
		}
	}
	
	public void checkFile(String filename) {
		File file = new File(filename);
		
		if (false == file.exists()) {
			throw new RuntimeException("file is not existed. file:" + file.getAbsolutePath());
		}
		
		if (false == file.isFile()) {
			throw new RuntimeException("file is not a file. directory:" + file.getAbsolutePath());
		}
	}
	
	public JSONObject read(String path, String domainId) {
		checkDirectory(path);
		
		String domainFilePath = path + File.separator + domainId;
		this.checkFile(domainFilePath);
		
		File domainFile = new File(path + File.separator + domainId);
		
		BufferedReader bufferReader = null;
		String lineText = "";
		long lineCount = 0L;
		try (FileReader file = new FileReader(domainFile)) {
			 
			bufferReader = new BufferedReader(file);
			String line = null;
			while ((line = bufferReader.readLine()) != null) {
				if (null != line) {
					lineText = line;
				}
				
				lineCount++;
			}
 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	if (null != bufferReader) {
        		try {
					bufferReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        		System.out.println("Close buffer reader.");
        	}
        }
		
		if (lineCount > 1) {
			throw new RuntimeException("multiple line existed in data file. path:" + path + "--domainId:" + domainId);
		}
		
		JSONObject rtnObj = null;
		if (null != lineText) {
			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(lineText);
				
				if (false == (obj instanceof JSONObject)) {
					throw new RuntimeException("obj is not instance of JSONObject. ");
				}
				
				rtnObj = (JSONObject)obj;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rtnObj;
	}
	
	public Map<String, List<String>> parseJsonToMap(JSONObject json) {
		Map<String, List<String>> userIdToGameListMap = new HashMap<String, List<String>>();
		
		if (null == json) {
			throw new NullPointerException("json is null.");
		}
		
		try {
			Set<Entry<String, Object>> entrySet = json.entrySet();
			
			
			Iterator<Entry<String, Object>> it = entrySet.iterator();
			while (it.hasNext()) {
				Entry<String, Object> entry = it.next();
				
				if (null == entry.getValue()) {
					throw new NullPointerException("entry's content is null. key:" + entry.getKey());
				}
										
				if (!(entry.getValue() instanceof List)) {
					throw new RuntimeException("obj is not instance of List. key:" + entry.getKey());
				}
			
				userIdToGameListMap.put(entry.getKey(), (List<String>)entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("exception thrown. details:" + e.getMessage());
		}	
		
		return userIdToGameListMap;
	}
	
	public String getJsonStrFromList(List<String> list) {
		StringBuilder buf = new StringBuilder();
		
		//"{ \"items\": [ \"36312\",\"35405\" ]}";
		//{ \"items\": [ \"36312\",\"35405\" ]}
		buf.append("{ \\\"items\\\": [ ");
		
		int size = list.size();
		for (int pos=0; pos<size; pos++) {
			if (pos != (size-1)) {
				buf.append("\\\"").append(list.get(pos)).append("\\\",");
			}
			else {
				buf.append("\\\"").append(list.get(pos)).append("\\\" ]}\"");
			}
		}
		return buf.toString();
	}
	
	public static void main(String [] args) {
		test2();
	}
	
	public static void test1() {
		String path = "F:\\data";
		String domainId = "2228";
		
		try {
			JSONObject jsonObj = JSONHelper.getInstance().read(path, domainId);
			System.out.println(jsonObj.toJSONString());
			
			Map<String, List<String>> map = JSONHelper.getInstance().parseJsonToMap(jsonObj);
			System.out.println("map size:" + map.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void test2() {
		List<String> gameIdList = new ArrayList<String>();
		gameIdList.add("36312");
		gameIdList.add("35405");
		gameIdList.add("35442");
		String rtnStr = JSONHelper.getInstance().getJsonStrFromList(gameIdList);
		System.out.println("result string:" + rtnStr);
		
	}
}
