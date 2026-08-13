package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBUtil {
    public static String driver;
    public static String url;
    public static String root;
    public static String pwd;

    static{
        try{
            Properties prop=new Properties();
            prop.load(new FileInputStream("src/config/db.properties"));

            driver=prop.getProperty("driver");
            url=prop.getProperty("url");
            root=prop.getProperty("root");
            pwd=prop.getProperty("pwd");
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
