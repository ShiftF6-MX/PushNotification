import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class PushNotification {

	//DATOS DE FIREBASE
    public final static String AUTH_KEY_FCM = "AAAA0ZWpvgs:APA91bF6vhcNSAbCb2xxnvqlNo_DeKATKkaZSqrj5PZIjsX6Sb8Hv8A146okW5dLnadtB6kikp5v3d6Zcl1Fh9IEgeLknunpto7gRVUOhwDdagqbmn-Vqfd5dRkJmnRlav6IXz7jZYLJ";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public static void pushFCMNotification(String DeviceIdKey) throws Exception {

        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 
        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        //ELEMENTOS JSON PARA FIREBASE
        JSONObject data = new JSONObject();
        data.put("to", DeviceIdKey.trim());
        JSONObject info = new JSONObject();
        info.put("title", "Nueva Notificacion"); // Notification title
        info.put("text", "Notificacion enviada desde clase Java"); // Notification body
        data.put("notification", info);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data.toString());
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        System.out.println("CODIGO DE RESPUESTA : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

    }

    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
        PushNotification.pushFCMNotification("/topics/dispositivos");
    }
}
