package com.rpsa.rpsa.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class BharatVC {
    private final int BUFFER_CAPACITY = 10000;
    public String sendVCReq(String title,String desc,String hdate,String starttime,String endtime) throws MalformedURLException, IOException, Exception {
        //String url = "https://164.100.250.92/external/api/v1/schedules/cc-mspsdc@meghalaya.gov.in";
        String url = "https://bharatvc.nic.in/external/api/v1/schedules/cc-mspsdc%40meghalaya.gov.in";
        String apikey = "0bca4cd1-a552-4a67-8d3a-31c8d87e1b34";
        String apiSecret = "1qc1wxKciDx99oiqa08yeaxKwVAM7CdH";

//        String password = "Srinivas@123";
        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        String content = "{\"audioMuted\": true,\"needsContentPermission\": true,\"title\": \""+title+"\",\"waitingRoom\": true,\"passiveWaitingRoom\": \"true\",\"description\": \""+desc+"\",\"startTime\": \""+hdate+"T"+starttime+":00+05:30\",\"endTime\": \""+hdate+"T"+endtime+":00+05:30\",\"passiveEnabled\": true,\"notBeforeMinutes\": 60}";
        String signature = HmacSHA256.generateCurrentSignature(apiSecret, content);
//        //System.out.println("signature "+signature);

        httpClient.setRequestProperty("Content-Type", "application/json");
        httpClient.setRequestProperty("Accept", "application/json");
        httpClient.setRequestProperty("X-API-KEY", apikey);
        httpClient.setRequestProperty("X-REQUEST-SIGNATURE", signature);

//        try (OutputStream os = httpClient.getOutputStream()) {
//            byte[] input = content.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        // Send post request
        httpClient.setDoOutput(true);

        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            byte[] input = content.getBytes("utf-8");
            wr.write(input);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();
//        //System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post parameters : " + content);
//        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()));

        String line;
        StringBuilder response = new StringBuilder(BUFFER_CAPACITY);

        try {



            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } finally {
            if (in != null) {
                Dashboard.safeClose(in);
            }
        }

        //print result
//            //System.out.println(response.toString());
        return response.toString();


    }
}
