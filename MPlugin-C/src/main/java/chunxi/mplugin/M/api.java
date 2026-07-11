package chunxi.mplugin.M;
//这个类是记录服务器ip+启动时间的
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class api {
    public static void callIPAPI() {
        try {
            URL url = new URL("https://api.shwgij.com/api/ip/ip?key=bhYoy7sOy9L2H7ZV8H1dT7eroi");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

            // 设置请求方式
            connection.setRequestMethod("GET");
            connection.connect();

            // 获取响应码
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                }
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}