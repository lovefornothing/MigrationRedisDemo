package norway.main;

public class SystemUtil {
    public static String getOrDefault(String envName, String defaultValue){        
        final String value = System.getenv(envName);
        if( value != null && value.length() > 0 ){          
            return value;
        }
        
        return defaultValue;
    }
    
    public static int getOrDefault(final String envName, final int defaultValue){
        final String values = System.getenv(envName);
        if( values != null && values.length() > 0 ){
            return Integer.parseInt(values);
        }

        return defaultValue;
    }

    public static boolean getOrDefault(String envName, boolean defaultValue) {
        final String values = System.getenv(envName);
        if( values != null && values.length() > 0 ){
            return Boolean.parseBoolean(values);
        }

        return defaultValue;
    }   
}
