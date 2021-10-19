package com.college_management_system.backend;
/*
This class use to send otp to the respected user's mobile number for authentication.
We are authenticating admin by otp method.
i.e. Two step authentication by default
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;

public class SendSMS {

    public static void sendSms(String message,String number) {
        try {
            String apiKey = "ikYcFbRUadpxgmGEVOInQLfKP7Bo8uM5WrtzSeXJAH0lCD2TZwK4s5bGxWRCh2Nv7qH0gpI61rjwuDfe";
            String sendId = "FSTSMS";

            message = URLEncoder.encode(message,"UTF-8");
            String language = "english";
            String route = "p";

            String myUrl = "https://www.fast2sms.com/dev/bulk?authorization="+apiKey+"&sender_id="+sendId+"&message="+message+"&language="+language+"&route="+route+"&numbers="+number;
            //sending get request using java..
            URL url = new URL(myUrl);
            //establish connectiron to the server
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("cache-control", "no-cache");
            int code = con.getResponseCode();
            StringBuffer response = new StringBuffer();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while(true) {
                String line = br.readLine();
                if(line == null) {
                    break;
                }
                response.append(line);
            }
        }catch (Exception e) {
            //e.printStackTrace();
            System.out.println("SMS not sent!");
        }
    }
}