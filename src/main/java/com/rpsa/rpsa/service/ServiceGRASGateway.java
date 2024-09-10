package com.rpsa.rpsa.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import com.rpsa.rpsa.model.T_userlogin;
import com.rpsa.rpsa.repository.TAppealsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceGRASGateway {

    @Autowired
    public TAppealsRepository appealRepo;

    private String MERCHANT_URL_PAY;
    private String DEPT_CODE;
    private String GRASOFFICE_CODE;
    private String PAYMENT_TYPE;
    private String REC_FIN_YEAR;
    private String MAJOR_HEAD;
    private String HOA1;
    private String AMOUNT1;
    private String CHALLAN_AMOUNT;
    private String PARTY_NAME;
    private String MOBILE_NO;
    private String TRANSACTIONCODE;
    private String SUB_SYSTEM;
    private String FORM_ID;
    private String TREASURY_CODE;
    private String TAX_ID;
    private String PERIOD;

    private final String ADDRESS1;
    private final String FROM_DATE;
    private final String TO_DATE;
    private final String ADDRESS2;
    private final String ADDRESS3;
    private final String PIN_NO;
//	private final Environment environment;

    final String cryptoSalt = "Salt_4_Payment";

    //    @Autowired
    public ServiceGRASGateway() {

        this.FROM_DATE = "31/07/2017";
        this.TO_DATE = "28/11/2099";
        this.ADDRESS1 = "";
        this.ADDRESS2 = "";
        this.ADDRESS3 = "";
        this.PIN_NO = "";
        this.REC_FIN_YEAR = "2022-2023";
    }

//    public static HttpSession session() {
//        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//        return attr.getRequest().getSession(true); // true == allow create
//    }

    public String generateRedirectURI(T_userlogin user, Integer amount, String appealcode) throws JsonProcessingException {

//		String sql = "select config as value1,(select paymenttype from masters.feetypes where feetypecode=? ) as value2,op.officecode as value3 "
//				+ "from masters.officespaymentmodes op, masters.paymentmodes p,nicobps.applications app "
//				+ "where p.paymentmodecode=op.paymentmodecode "
//				+ "and op.officecode=app.officecode and p.paymentmodeclass='GRAS' and app.appealcode=? ";
//		List<CommonMap> configList = serviceUtilInterface.listCommonMap(sql,
//				new Object[] { Integer.parseInt(feecode), appealcode });
//		Map<String, Object> config = new ObjectMapper().readValue(configList.get(0).getValue1(),
//				new TypeReference<HashMap<String, Object>>() {
//				});
        this.DEPT_CODE = "MSP";
        this.SUB_SYSTEM = "MSPSDC";
        this.TREASURY_CODE = "20";
        this.FORM_ID = "TR04A";
        this.GRASOFFICE_CODE = "MSP000";
        this.AMOUNT1 = amount.toString();
        this.CHALLAN_AMOUNT = amount.toString();
        this.TAX_ID = appealcode;
        this.TRANSACTIONCODE = Long.valueOf(((new Date().getTime() / 1000L) % Integer.MAX_VALUE)).toString();
//        this.TRANSACTIONCODE = "28804543756730543945";
        this.PAYMENT_TYPE = "01";
        this.MAJOR_HEAD = "0070";

        this.PARTY_NAME = user.getFullname().toString();
        this.MOBILE_NO = user.getContact();

//		Map<String, Object> paymentType = ((Map<String, Object>) ((Map<String, Object>) config.get("modes"))
//				.get(configList.get(0).getValue2()));
        this.PERIOD = "O";
        this.HOA1 = "0070608002000-33";

        String grasParams = getParams();
        ////////////////////////////////////////////////////////////////// Persist
        ////////////////////////////////////////////////////////////////// transaction

        //save here

        ////////////////////////////////////////////////////////////////
        return grasParams;
    }

    public String getParams() throws JsonProcessingException {

        Map<String, Object> json = new HashMap<>();
        json.put("DEPT_CODE", this.DEPT_CODE);
        json.put("SUB_SYSTEM", this.SUB_SYSTEM);
        json.put("TREASURY_CODE", this.TREASURY_CODE);
        json.put("FORM_ID", this.FORM_ID);
        json.put("OFFICE_CODE", this.GRASOFFICE_CODE);
        json.put("AMOUNT1", this.AMOUNT1);
        json.put("CHALLAN_AMOUNT", this.CHALLAN_AMOUNT);
        json.put("TAX_ID", this.TAX_ID);
        json.put("DEPARTMENT_ID", this.TRANSACTIONCODE);
        json.put("PAYMENT_TYPE", this.PAYMENT_TYPE);
        json.put("MAJOR_HEAD", this.MAJOR_HEAD);

        json.put("PARTY_NAME", this.PARTY_NAME);
        json.put("MOBILE_NO", this.MOBILE_NO);

        json.put("PERIOD", this.PERIOD);
        json.put("HOA1", this.HOA1);

        json.put("REC_FIN_YEAR", this.REC_FIN_YEAR);
        json.put("FROM_DATE", this.FROM_DATE);
        json.put("TO_DATE", this.TO_DATE);
        json.put("ADDRESS1", this.ADDRESS1);
        json.put("ADDRESS2", this.ADDRESS2);
        json.put("ADDRESS3", this.ADDRESS3);
        json.put("PIN_NO", this.PIN_NO);

        return new ObjectMapper().writeValueAsString(json);
    }

    public boolean getGRN(String appealcode, String transactioncode, String amount, String entrydate) {

        boolean resp = true;
        if (!callGRN(appealcode, transactioncode, amount, entrydate, false)) {
            if (!callGRN(appealcode, transactioncode, amount, entrydate, true)) {
                resp = false;
            }
        }
        return resp;
    }

    public boolean callGRN(String appealcode, String transactioncode, String amount, String entrydate, boolean GETCIN) {

        boolean resp = false;
//        String sql = "select replace((config::json->'SUB_SYSTEM')::text,'\"','') as subsystem,replace((config::json->'officecode')::text,'\"','') as grasofficecode, t.*,replace((t.responseparameters::json->'TAXID')::text,'\"','') as taxid,replace((t.responseparameters::json->'ENTRY_DATE')::text,'\"','') as entry_date "
//                + ",app.modulecode,app.officecode,app.usercode  " + "from masters.officespaymentmodes op "
//                + "inner join masters.paymentmodes p on p.paymentmodecode=op.paymentmodecode and p.paymentmodeclass='GRAS' "
//                + "inner join nicobps.applications app on op.officecode=app.officecode "
//                + "inner join (select appealcode,max(transactioncode) transactioncode from nicobps.applicationstransactionmap group by appealcode) map  on map.appealcode=app.appealcode "
//                + "inner join nicobps.transactions t on t.transactioncode=map.transactioncode "
//                + "where app.appealcode=? AND op.enabled=true";
//        List<Map<String, Object>> configList = serviceUtilInterface.listGeneric(sql, new Object[]{appealcode});
//        if (configList == null || configList.isEmpty()) {
//            return false;
//        }
        trustEveryone();

        String POST_PARAMS = "OFFICE_CODE=MSP000&DEPARTMENT_ID="
                + transactioncode + "&AMOUNT=" + amount
                + "&ENTRY_DATE=" + entrydate;
        if (GETCIN) {
            POST_PARAMS += "&ACTION_CODE=GETCIN";
            POST_PARAMS += "&SUB_SYSTEM=MSPSDC";
        }

        try {
            String url = null;
            url = "https://megepayment.gov.in/challan/models/frmgetgrn.php";

            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            // POST only - START
            con.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
            con.setRequestProperty("Accept", "*/*");
            con.setDoOutput(true);
            java.io.OutputStream os = con.getOutputStream();
            os.write(POST_PARAMS.getBytes());
            os.flush();
            os.close();

            // For POST only - END
            int responseCode = con.getResponseCode();
//             System.out.println("POST Response Code :: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response1 = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response1.append(inputLine);
                }
                in.close();

                Pattern r = null;
                Matcher m = null;
                if (!GETCIN) {
                    //System.out.println(" GET GRN");
                    r = Pattern.compile("STATUS\\$(.?)\\$");
                } else {
                    //System.out.println(" GET CIN");
                    r = Pattern.compile("name=\"STATUS\" id=\"STATUS\" value=\"(.?)\"");
                }
                System.out.println("response1 " +response1);
                m = r.matcher(response1.toString());
                if (m.find()) {
                    if (m.groupCount() > 0) {
//                        //System.out.println(" Pattern Found ," + configList.get(0).get("transactioncode") + ","
//                                + GrasStatusMap(m.group(1)));

//update database here(payment status)
//                        daoPaymentInterface.UpdatePayment(GrasStatusMap(m.group(1)), null,
//                                Integer.parseInt(configList.get(0).get("transactioncode").toString()), null);
                        resp = true;
                    }
                } else {
                    //System.out.println("NO MATCH");
//	                res = false;
                }

            } else {
                //System.out.println("POST request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("Error " + e);
        }
        //System.out.println("Response String of GETGRN : " + resp);
        return resp;
    }

    private static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }

    // Just add these two functions in your program
    public static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }

    private static void trustAllHttpsCertificates() throws Exception {

        // Create a trust manager that does not validate certificate chains:
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];

        javax.net.ssl.TrustManager tm = new miTM();

        trustAllCerts[0] = tm;

        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");

        sc.init(null, trustAllCerts, null);

        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    }

    public String Grasmessage(String status) {
        String message = "";
        switch (status) {
            case "Y":
                message = "Payment Successful. ";
                break;
            case "N":
                message = "Payment Unsucessful. ";
                break;
            case "A":
                message = "Payment Aborted.";
                break;
            default:
                message = "An error occured";
                break;
        }
        return message;
    }

    public String GrasStatusMap(String status) {
        String message = "";
        switch (status) {
            case "Y":
                message = "S";
                break;
            case "N":
                message = "F";
                break;
            case "A":
                message = "A";
                break;
            default:
                message = "I";
                break;
        }
        return message;
    }
}
