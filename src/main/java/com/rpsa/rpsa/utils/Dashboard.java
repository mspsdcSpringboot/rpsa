package com.rpsa.rpsa.utils;

import jakarta.servlet.http.HttpServletResponse;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Dashboard {


    private final int BUFFER_CAPACITY = 10000;

    public String getalldata() throws MalformedURLException, IOException, Exception {
        String url = "https://investmeghalaya.gov.in/configure/getInstanceConsolidationData";
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        //Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");

        httpClient.setRequestProperty("Content-Type", "application/json");
        httpClient.setRequestProperty("Accept", "application/json");
//        httpClient.setRequestProperty("X-API-KEY", apikey);
//        httpClient.setRequestProperty("X-REQUEST-SIGNATURE", signature);

//        try (OutputStream os = httpClient.getOutputStream()) {
//            byte[] input = content.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        // Send post request
        httpClient.setDoOutput(true);

//        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
//            byte[] input = content.getBytes("utf-8");
//            wr.write(input);
//            wr.flush();
//        }
        int responseCode = httpClient.getResponseCode();
//        //System.out.println("\nSending 'POST' request to URL : " + url);
//        //System.out.println("Post parameters : " + content);
//        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()));
        StringBuilder response = new StringBuilder(BUFFER_CAPACITY);
        try {
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } finally {
            if (in != null) {
                safeClose(in);

            }
        }

        //print result
//            //System.out.println(response.toString());
        return response.toString();

    }

    public String deptwise() throws MalformedURLException, IOException, Exception {
        String url = "https://investmeghalaya.gov.in/configure/getDepartmentWiseData";
//        String apikey = "0bca4cd1-a552-4a67-8d3a-31c8d87e1b34";
//        String apiSecret = "1qc1wxKciDx99oiqa08yeaxKwVAM7CdH";

//        String password = "Srinivas@123";
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        //Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        String content = "{\"departmentId\": \"\"}";
//        String signature = generateCurrentSignature(apiSecret, content);
//        //System.out.println("signature "+signature);

        httpClient.setRequestProperty("Content-Type", "application/json");
        httpClient.setRequestProperty("Accept", "application/json");
//        httpClient.setRequestProperty("X-API-KEY", apikey);
//        httpClient.setRequestProperty("X-REQUEST-SIGNATURE", signature);

//        try (OutputStream os = httpClient.getOutputStream()) {
//            byte[] input = content.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        // Send post request
        httpClient.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream());
        byte[] input = content.getBytes("utf-8");
        wr.write(input);
        wr.flush();

        int responseCode = httpClient.getResponseCode();
//        //System.out.println("\nSending 'POST' request to URL : " + url);
//        //System.out.println("Post parameters : " + content);
//        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()));

        String line;
        StringBuilder response = new StringBuilder(BUFFER_CAPACITY);

        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        //print result
//            //System.out.println(response.toString());
        return response.toString();

    }

    public String servicewise(String deptid) throws MalformedURLException, IOException, Exception {
        String url = "https://investmeghalaya.gov.in/configure/getServiceWiseData";
//        String apikey = "0bca4cd1-a552-4a67-8d3a-31c8d87e1b34";
//        String apiSecret = "1qc1wxKciDx99oiqa08yeaxKwVAM7CdH";

//        String password = "Srinivas@123";
        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        String content = "{\"serviceId\":\"\",\"departmentId\": \"" + deptid + "\"}";
//        String signature = generateCurrentSignature(apiSecret, content);
//        //System.out.println("signature "+signature);

        httpClient.setRequestProperty("Content-Type", "application/json");
        httpClient.setRequestProperty("Accept", "application/json");
//        httpClient.setRequestProperty("X-API-KEY", apikey);
//        httpClient.setRequestProperty("X-REQUEST-SIGNATURE", signature);

//        try (OutputStream os = httpClient.getOutputStream()) {
//            byte[] input = content.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        // Send post request
        httpClient.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream());
        byte[] input = content.getBytes("utf-8");
        wr.write(input);
        wr.flush();

        int responseCode = httpClient.getResponseCode();
//        //System.out.println("\nSending 'POST' request to URL : " + url);
//        //System.out.println("Post parameters : " + content);
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
                safeClose(in);

            }
        }

        //print result
//            //System.out.println(response.toString());
        return response.toString();

    }

    public String officewise(String serviceid) throws MalformedURLException, IOException, Exception {
        String url = "https://investmeghalaya.gov.in/configure/getLocationWiseData";
//        String apikey = "0bca4cd1-a552-4a67-8d3a-31c8d87e1b34";
//        String apiSecret = "1qc1wxKciDx99oiqa08yeaxKwVAM7CdH";

//        String password = "Srinivas@123";
        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();
//        serviceid = serviceid.substring(0, serviceid.length() - 4);
        //add reuqest header
        httpClient.setRequestMethod("POST");
        String content = "{\"serviceId\":\"" + serviceid + "\",\"departmentId\": \"\"}";
//        String signature = generateCurrentSignature(apiSecret, content);
//        //System.out.println("signature "+signature);

        httpClient.setRequestProperty("Content-Type", "application/json");
        httpClient.setRequestProperty("Accept", "application/json");
//        httpClient.setRequestProperty("X-API-KEY", apikey);
//        httpClient.setRequestProperty("X-REQUEST-SIGNATURE", signature);

//        try (OutputStream os = httpClient.getOutputStream()) {
//            byte[] input = content.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        // Send post request
        httpClient.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream());
        byte[] input = content.getBytes("utf-8");
        wr.write(input);
        wr.flush();

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
                safeClose(in);

            }
        }

        //print result
        //System.out.println(response.toString());
        return response.toString();

    }

    public String getrefnos(String locationId, String serviceid) throws MalformedURLException, IOException, Exception {
        String url = "https://investmeghalaya.gov.in/configure/getApplicationReferenceNumber";
//        String apikey = "0bca4cd1-a552-4a67-8d3a-31c8d87e1b34";
//        String apiSecret = "1qc1wxKciDx99oiqa08yeaxKwVAM7CdH";

//        String password = "Srinivas@123";
        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();
//        serviceid = serviceid.substring(0, serviceid.length() - 5);
        //add reuqest header
        httpClient.setRequestMethod("POST");
        String content = "{\"serviceId\":\"" + serviceid + "\",\"locationId\": \"" + locationId + "\"}";
//        String signature = generateCurrentSignature(apiSecret, content);
        System.out.println("content " + content);

        httpClient.setRequestProperty("Content-Type", "application/json");
        httpClient.setRequestProperty("Accept", "application/json");
//        httpClient.setRequestProperty("X-API-KEY", apikey);
//        httpClient.setRequestProperty("X-REQUEST-SIGNATURE", signature);

//        try (OutputStream os = httpClient.getOutputStream()) {
//            byte[] input = content.getBytes("utf-8");
//            os.write(input, 0, input.length);
//        }
//        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
        // Send post request
        httpClient.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream());
        byte[] input = content.getBytes("utf-8");
        wr.write(input);
        wr.flush();

        int responseCode = httpClient.getResponseCode();
//        //System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post parameters : " + content);
//        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));

        String line;
        StringBuilder response = new StringBuilder(BUFFER_CAPACITY);

        try {

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } finally {
            if (in != null) {
                safeClose(in);

            }
        }

        //print result
//            //System.out.println(response.toString());
        return response.toString();

    }

    public String getpendingrefnos(String referenceNo) throws MalformedURLException, IOException, Exception {
        String url = "https://investmeghalaya.gov.in/configure/getApplicationPendingDetails";
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        //Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        String content = "{\"referenceNo\":\"" + referenceNo + "\"}";

        System.out.println("content " + content);

        httpClient.setRequestProperty("Content-Type", "application/json");
        httpClient.setRequestProperty("Accept", "application/json");

        httpClient.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream());
        byte[] input = content.getBytes("utf-8");
        wr.write(input);
        wr.flush();

        int responseCode = httpClient.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));

        String line;
        StringBuilder response = new StringBuilder(BUFFER_CAPACITY);

        try {

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
        } finally {
            if (in != null) {
                safeClose(in);

            }
        }

        return response.toString();

    }

    public void viewapplicationdetails(HttpServletResponse response, String referenceNo, String submissionDate) throws MalformedURLException, IOException, Exception {
        String url = "https://megedistrict.gov.in/getApplicationDetailPdf";
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
        };
        //Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        String content = "{\"referenceNo\":\"" + referenceNo + "\",\"submissionDate\":\"" + submissionDate + "\"}";

        System.out.println("content " + content);

        httpClient.setRequestProperty("Content-Type", "application/json");
        httpClient.setRequestProperty("Accept", "application/json");

        httpClient.setDoOutput(true);

        DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream());
        byte[] input = content.getBytes("utf-8");
        wr.write(input);
        wr.flush();

        int responseCode = httpClient.getResponseCode();
        System.out.println("responseCode " + responseCode);
//
//        BufferedInputStream in = new BufferedInputStream(httpClient.getInputStream());
//
//        byte[] bytearray = new byte[1048576];
//        int size = 0;
//        InputStream is = new BufferedInputStream(new ByteArrayInputStream(in));

//        BufferedInputStream in = new BufferedInputStream(httpClient.getInputStream());
//        System.out.println("in "+in);
//        byte[] bytearray = new byte[1048576];
//        int size = in.read(bytearray);  // Read data into the bytearray
//
//        InputStream is = new ByteArrayInputStream(bytearray);
//
//        System.out.println("contenttype " + URLConnection.guessContentTypeFromStream(is));
//
//        response.setContentType("application/pdf");
//
//        response.getOutputStream().write();
        try ( BufferedInputStream in = new BufferedInputStream(httpClient.getInputStream())) {
            // Assuming you are using a HttpServletResponse object named 'response'

            // Set the content type for the response
            response.setContentType("application/pdf"); // Set to a default binary type

            // Get the output stream of the response
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[1024]; // Adjust buffer size as needed
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead); // Write the buffer to the output stream
            }

            outStream.flush(); // Flush the output stream to ensure all data is sent
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

    }

    public static void safeClose(BufferedReader fis) throws IOException {
        if (fis != null) {
//            try {
            fis.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
