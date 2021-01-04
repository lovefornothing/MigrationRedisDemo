package norway.main;

public class Environment {
    public static final String ENV_local = "local";
    public static final String ENV_staging = "staging";
    public static final String ENV_production = "production";
    
    // The environment this application is running on
    // The possible value could be "local", "staging", and "production"
    public static final String ENV_NAME = SystemUtil.getOrDefault("ENV_NAME", ENV_local);

    public static final Integer DOMAIN_ID = SystemUtil.getOrDefault("DOMAIN_ID", 1244);
    public static final String ZK_PROD_ENV_TAG = SystemUtil.getOrDefault("ZK_PROD_ENV_TAG", "ENVX");
    public static final String ZK_PROD_ENV_INFO = SystemUtil.getOrDefault("ZK_PROD_ENV_INFO","[]");
    
    private static final String GIC_ANALYTIC_URL = SystemUtil.getOrDefault("GIC_ANALYSIC_URL","http://analyticapi-stage.everymatrix.com");
    public static final String LAST_PLAYED_GAMES_URL = GIC_ANALYTIC_URL+"/widgetsApi/lastplayedgames/";
    public static final String MOST_PLAYED_GAMES_URL = GIC_ANALYTIC_URL+"/widgetsApi/mostplayedgames/";
    
    public static final String CASINO_API_URL = SystemUtil.getOrDefault("CASINO_API_URL","https://mavibet-api.stage.norway.everymatrix.com/");
    public static final String GAME_URL = CASINO_API_URL + "v1/casino/games/";
    //public static final String REDIS_CONNECTION = SystemUtil.getOrDefault("REDIS_CONNECTION", "redis://10.3.238.20:6379/8");
    public static final String REDIS_CONNECTION = SystemUtil.getOrDefault("REDIS_CONNECTION", "redis://10.3.238.20:6379/8");
    public static final String GM_STAGE_BASE_URL = "https://core-gm-stage.everymatrix.com";
    public static final String PARTNER_ID = SystemUtil.getOrDefault("PARTNER_ID", "JetId");	 					// for Jetbull (domainId = 6)
	public static final String PARTNER_KEY = SystemUtil.getOrDefault("PARTNER_KEY", "JetCode");	      			// for Jetbull (domainId = 6)
	
	private static final String SLIM_GM2_URL = SystemUtil.getOrDefault("SLIM_GM2_URL","http://slim.stage.gm2.local");
	public static final String SLIM_PLAYER_URL = SLIM_GM2_URL+"/api/v1/users/";
	public static final String PLAYER_BALANCE = "/balance";
}
