package com.sendsms.sendsms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SendsmsApplication {
    
	public static void sendSms(String message, String number) {
	    System.out.println("Sending SMS with message: " + message);
	    System.out.println("To number: " + number);
	    System.out.println("------------");

	    String apiKey = "vyGQW1Ap4EPjRwq8knHINxTlz29Vbt7reDU5FOmBgdZLoMahY3p1NXkBr3YwZ2j58WLCDhiKyQdngs0u";
	    String senderId = "FSTSMS";

	    try {
	        message = URLEncoder.encode(message, "UTF-8");
	        System.out.println("Encoded message: " + message);

	        String route = "dlt";
	        String myUrl = "https://www.fast2sms.com/dev/bulkV2?authorization=" + apiKey +
	                       "&sender_id=" + senderId + "&message=" + message +
	                       "&variables_values=&route=" + route + "&numbers=" + number;
	        System.out.println("Request URL: " + myUrl);

	        URL url = new URL(myUrl);
	        System.out.println("Sending request...");

	        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
	        con.setRequestMethod("GET");
	        con.setRequestProperty("User-Agent", "Mozilla/5.0");
	        con.setRequestProperty("cache-control", "no-cache");

	        int code = con.getResponseCode();
	        System.out.println("Response Code: " + code);

	        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        StringBuffer sb = new StringBuffer();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line);
	        }
	        br.close();

	        System.out.println("Response Body: " + sb.toString());

	    } catch (MalformedURLException e) {
	        System.err.println("Malformed URL: " + e.getMessage());
	        e.printStackTrace();
	    } catch (IOException e) {
	        System.err.println("IO Exception: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
    
    public static void main(String[] args) {
        SpringApplication.run(SendsmsApplication.class, args);
        System.out.println("This is my test for sending SMS!!");
        
        try {
            sendSms("Hello I'm sending this message from Java using \n"
                    + "fast2sms " + new java.util.Date().toLocaleString(), "+919527828684");
        } catch (Exception e) {
            System.err.println("Error occurred while sending SMS: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
