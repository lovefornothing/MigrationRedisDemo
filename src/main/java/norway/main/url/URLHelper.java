package norway.main.url;

public class URLHelper {
	private static URLHelper instance = new URLHelper();
	
	public static URLHelper getInstance() {
		return URLHelper.instance;
	}
	
	public String getFavoritesURL(String operatorBaseURL, String userId) {
		//"https://kavbet-api.stage.norway.everymatrix.com/v1/player/3484078/favorites"
		return operatorBaseURL + "/" + userId + "/favorites";
	}
}
