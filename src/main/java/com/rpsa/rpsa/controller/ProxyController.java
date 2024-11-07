package com.rpsa.rpsa.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rpsa.rpsa.service.TransportService;
import com.rpsa.rpsa.service.VahanService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class ProxyController {

    @Autowired
    private VahanService vahanService;
    @Autowired
    private TransportService transportService;




    @Scheduled(cron = "0 10 0 * * *")
    @PostMapping("/secure/api/totalCount")
    public void proxyTotalCount() {
        String API_URL = "https://parivahan.gov.in/cmdashboard/totalCount";

        // Set headers (including any necessary cookies)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Cookie", "JSESSIONID=131778C4D52E4EAC7875A46363DC6076");
        String requestPayload = "{ \"stateCd\": \"ML\" }";

        // Create a new HttpEntity with the request payload and headers
        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);

        // Make the request to the external API
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            // Log the response
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response.getBody());

                    JsonNode apiArray = rootNode.path("api");
                    if (apiArray.isArray() && apiArray.size() > 0) {
                        JsonNode firstApiObject = apiArray.get(0);

                        // Extract relevant fields
                        String applied = firstApiObject.path("applied").asText();
                        String approved = firstApiObject.path("approved").asText();
                        String pending = firstApiObject.path("pending").asText();
                        String rejected = firstApiObject.path("rejected").asText();

                        // Call service to save the data
                        vahanService.saveOrUpdateTotalDashboardData(applied, approved, pending, rejected);

                        System.out.println("Data saved successfully to the database.");
                    } else {
                        System.out.println("No data found in the 'api' array.");
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    System.out.println("Error while parsing the JSON response.");
                }
            } else {
                System.out.println("API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
        }
    }


    @Scheduled(cron = "0 10 0 * * *")
    @PostMapping("/secure/api/serviceWiseCount")
    public void proxyServiceWiseCount() {
        String API_URL = "https://parivahan.gov.in/cmdashboard/serviceWiseCount";

        // Set headers (including any necessary cookies)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Cookie", "JSESSIONID=131778C4D52E4EAC7875A46363DC6076");
        String requestPayload = "{ \"stateCd\": \"ML\" }";

        // Create a new HttpEntity with the request payload and headers
        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);

        // Make the request to the external API
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            // Log the response
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response.getBody());

                    JsonNode apiArray = rootNode.path("api");
                    if (apiArray.isArray() && apiArray.size() > 0) {
                        String res = vahanService.saveOrUpdateServiceWiseDashboardData(apiArray);

                        if(Objects.equals(res, "saved")){
                            System.out.println("Data saved successfully to the database.");
                        }
                        else{
                            System.out.println("Data not saved to the database.");
                        }
                    } else {
                        System.out.println("No data found in the 'api' array.");
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    System.out.println("Error while parsing the JSON response.");
                }
            } else {
                System.out.println("API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
        }
    }



    @PostMapping("/secure/api/officeWiseCount")
    public ResponseEntity<String> proxyOfficeWiseCount(@RequestBody String requestPayload) {
        String url = "https://parivahan.gov.in/cmdashboard/offWiseCount";

        // Set headers (including any necessary cookies)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Cookie", "JSESSIONID=131778C4D52E4EAC7875A46363DC6076");

        // Create a new HttpEntity with the request payload and headers
        HttpEntity<String> entity = new HttpEntity<>(requestPayload, headers);

        // Make the request to the external API
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, String.class);

        // Return the response from the external API
        return response;
    }


    @GetMapping("/secure/viewKhadcApplicationDetails")
    public void viewKhadcApplicationDetails(HttpServletResponse response,
                                            @RequestParam("referenceNo") String referenceNo,
                                            @RequestParam("submissionDate") @DateTimeFormat(pattern = "dd/MM/yyyy") String submissionDate) {

        String API_URL = "https://megedistrict.gov.in/getApplicationDetailPdf";

        // Trust manager that trusts all SSL certificates (not recommended for production)
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {}
                }
        };

        try {
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Open a connection to the external API URL
            HttpsURLConnection httpClient = (HttpsURLConnection) new URL(API_URL).openConnection();

            // Set request method and headers
            httpClient.setRequestMethod("POST");
            httpClient.setRequestProperty("Content-Type", "application/json");
            httpClient.setRequestProperty("Accept", "application/pdf");

            // Prepare the JSON payload
            String requestPayload = "{\"referenceNo\":\"" + referenceNo + "\",\"submissionDate\":\"" + submissionDate + "\"}";

            // Enable output for POST request
            httpClient.setDoOutput(true);

            // Write the request payload
            try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
                byte[] input = requestPayload.getBytes("utf-8");
                wr.write(input);
                wr.flush();
            }

            // Check the response code
            int responseCode = httpClient.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Process the PDF response if successful
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedInputStream in = new BufferedInputStream(httpClient.getInputStream())) {
                    // Set the response content type and headers
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename=\"application.pdf\"");

                    // Write the PDF content to the servlet output stream
                    try (OutputStream outStream = response.getOutputStream()) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = in.read(buffer)) != -1) {
                            outStream.write(buffer, 0, bytesRead);
                        }
                        outStream.flush(); // Ensure all data is sent
                    }
                }
            } else {
                // Handle failed request by sending a 400 error
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Failed to fetch PDF. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle server error by sending a 500 error
//            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while fetching PDF: " + e.getMessage());
        }
    }

    //Transport DataDump
    @PostMapping("/public/transportDataDump")
    public String transportdataDump(){
        String res = "iniitated";
        String URL = "https://sarathi.parivahan.gov.in/SarathiWebServices/rsServices/DMDashboard/getSarathiCountsForML";

//         Set headers (including any necessary cookies)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");


        // Create a new HttpEntity with the request payload and headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String userName = "mspsdc_ML";
        String password = "0dfc285e3beef3022277f944bea15538";
        String fromDate = "01-01-2024";
        String toDate = "01-10-2024";

        String encodedUserName = bas64Converter(userName);
        String encodedPassword = bas64Converter(password);
        String encodedfromDate = bas64Converter(fromDate);
        String encodedtoDate = bas64Converter(toDate);

        String API_URL = URL + "/" + encodedUserName + "/" + encodedPassword + "/" + encodedfromDate + "/" + encodedtoDate;
//        System.out.println(API_URL);

        // Make the request to the external API
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            System.out.println("API Response Body: " + response.getBody());

            // Log the response
            if (response.getStatusCode() == HttpStatus.CREATED) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response.getBody());

                    JsonNode apiArray = rootNode.path("api");
                    System.out.println("API Body 2: " + apiArray);
                    if (apiArray.isArray() && apiArray.size() > 0) {
                        res = transportService.saveOrUpdateTransportData(apiArray);

                        if(Objects.equals(res, "saved")){
                            System.out.println("Data saved successfully to the database.");
                        }
                        else{
                            System.out.println("Data not saved to the database.");
                        }
                    } else {
                        System.out.println("No data found in the 'api' array.");
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    System.out.println("Error while parsing the JSON response.");
                }
            } else {
                System.out.println("API call failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            System.err.println("Error calling API: " + e.getMessage());
        }
        return res;
    }



    public String bas64Converter(String data){
        byte[] encodedBytes = Base64.encodeBase64(data.getBytes());
        String encodedString = new String(encodedBytes);
        return encodedString;
    }


}
