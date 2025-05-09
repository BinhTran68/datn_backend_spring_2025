package com.poly.app.domain.client.service;

import com.poly.app.domain.admin.bill.request.BillDetailRequest;
import com.poly.app.infrastructure.config.ZaloPayConfig;
import com.poly.app.infrastructure.util.HMACUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class ZaloPayService {
    private final ZaloPayConfig zaloPayConfig;

    public ZaloPayService(ZaloPayConfig zaloPayConfig) {
        this.zaloPayConfig = zaloPayConfig;
    }

    private String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }


    public Map<String, Object> createPayment(String appuser, Long amount, Long order_id) throws Exception {
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item = new HashMap<>();
        item.put("itemid", "knb");
        item.put("itemname", "kim nguyen bao");
        item.put("itemprice", 198400);
        item.put("itemquantity", 1);
        items.add(item);
        Map<String, Object> zalopay_Params = new HashMap<>();

        zalopay_Params.put("appid", zaloPayConfig.APP_ID);
        zalopay_Params.put("apptransid", getCurrentTimeString("yyMMdd") + "_" + new Date().getTime());
        zalopay_Params.put("apptime", System.currentTimeMillis());
        zalopay_Params.put("appuser", appuser);
        zalopay_Params.put("amount", amount);

        zalopay_Params.put("description", "Thanh toan don hang #" + order_id);
        zalopay_Params.put("bankcode", "");
        zalopay_Params.put("item", new JSONObject(item).toString());


        // embeddata
        // Trong trường hợp Merchant muốn trang cổng thanh toán chỉ hiện thị danh sách
        // các ngân hàng ATM,
        // thì Merchant để bankcode="" và thêm bankgroup = ATM vào embeddata như ví dụ
        // bên dưới
        // embeddata={"bankgroup": "ATM"}
        // bankcode=""
        Map<String, String> embeddata = new HashMap<>();
        embeddata.put("merchantinfo", "eshop123");
        embeddata.put("promotioninfo", "");
        embeddata.put("redirecturl", zaloPayConfig.REDIRECT_URL);

        Map<String, String> columninfo = new HashMap<String, String>();
        columninfo.put("store_name", "E-Shop");

        embeddata.put("columninfo", new JSONObject(columninfo).toString());
        zalopay_Params.put("embeddata", new JSONObject(embeddata).toString());

        String data = zalopay_Params.get("appid") + "|" + zalopay_Params.get("apptransid") + "|"
                      + zalopay_Params.get("appuser") + "|" + zalopay_Params.get("amount") + "|"
                      + zalopay_Params.get("apptime") + "|" + zalopay_Params.get("embeddata") + "|"
                      + zalopay_Params.get("item");
        zalopay_Params.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, zaloPayConfig.KEY1, data));
//		zalopay_Params.put("phone", order.getPhone());
//		zalopay_Params.put("email", order.getEmail());
//		zalopay_Params.put("address", order.getAddress());
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(zaloPayConfig.CREATE_ORDER_URL);

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : zalopay_Params.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }


        post.setEntity(new UrlEncodedFormEntity(params));
        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;
        log.info("JSON gửi đi: " + params);
        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }
        JSONObject result = new JSONObject(resultJsonStr.toString());
        Map<String, Object> kq = new HashMap<String, Object>();
        kq.put("returnmessage", result.get("returnmessage"));
        kq.put("orderurl", result.get("orderurl"));
        kq.put("returncode", result.get("returncode"));
        kq.put("zptranstoken", result.get("zptranstoken"));
        kq.put("apptransid", zalopay_Params.get("apptransid"));
        kq.put("amount", amount);


        log.info(kq.toString());
        return kq;
    }

    public Map<String, Object> getStatusByApptransid(String apptransid) throws Exception {
        String appid = zaloPayConfig.APP_ID;
        String key1 = zaloPayConfig.KEY1;
        String data = appid + "|" + apptransid + "|" + key1; // appid|apptransid|key1
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appid", appid));
        params.add(new BasicNameValuePair("apptransid", apptransid));
        params.add(new BasicNameValuePair("mac", mac));

        URIBuilder uri = new URIBuilder("https://sandbox.zalopay.com.vn/v001/tpe/getstatusbyapptransid");
        uri.addParameters(params);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet(uri.build());

        CloseableHttpResponse res = client.execute(get);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        JSONObject result = new JSONObject(resultJsonStr.toString());
        Map<String, Object> kq = new HashMap<String, Object>();
        kq.put("returncode", result.get("returncode"));
        kq.put("returnmessage", result.get("returnmessage"));
        kq.put("isprocessing", result.get("isprocessing"));
        kq.put("amount", result.get("amount"));
        kq.put("discountamount", result.get("discountamount"));
        kq.put("zptransid", result.get("zptransid"));
        return kq;
    }


    // Phương thức refund
    public Map<String, Object> refund(Long zptransid, int amount, String description) throws Exception {
        // Kiểm tra đầu vào
        if (zptransid == null || amount <= 0 || description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid refund parameters");
        }

        // Tạo mrefundid
        long timestamp = System.currentTimeMillis();
        Random rand = new Random();
        String uid = timestamp + "" + (111 + rand.nextInt(888)); // Tạo chuỗi ngẫu nhiên để đảm bảo tính duy nhất
        String mrefundid = getCurrentTimeString("yyMMdd") + "_" + zaloPayConfig.APP_ID + "_" + uid;

        // Tạo dữ liệu cho request
        Map<String, Object> order = new HashMap<>();
        order.put("appid", zaloPayConfig.APP_ID);
        order.put("zptransid", zptransid);
        order.put("mrefundid", mrefundid);
        order.put("timestamp", timestamp);
        order.put("amount", amount);
        order.put("description", description);

        // Tạo chuỗi dữ liệu để mã hóa HMAC
        String data = order.get("appid") + "|" + order.get("zptransid") + "|" + order.get("amount")
                      + "|" + order.get("description") + "|" + order.get("timestamp");
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, zaloPayConfig.KEY1, data);
        order.put("mac", mac);

        // Gửi yêu cầu HTTP POST
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("https://sandbox.zalopay.com.vn/v001/tpe/partialrefund"); // ENDPOINT là URL refund từ ZaloPayConfig

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }
        post.setEntity(new UrlEncodedFormEntity(params));

        // Log request
        log.info("Refund request params: {}", params);

        // Thực hiện yêu cầu và nhận phản hồi
        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        // Parse JSON phản hồi
        JSONObject result = new JSONObject(resultJsonStr.toString());
        log.info("Refund response co ca mrefundid: {}", result.toString());

        // Tạo Map kết quả trả về
        Map<String, Object> response = new HashMap<>();
        for (String key : result.keySet()) {
            response.put(key, result.get(key));
        }

        // Kiểm tra trạng thái hoàn tiền
        int returnCode = result.getInt("returncode");
//        if (returnCode != 1) {
//            log.warn("Refund failed with return_code: {}, message: {}", returnCode, result.get("returnmessage"));
//            throw new Exception("Refund failed: " + result.get("returnmessage"));
//        }
        response.put("mrefundid", order.get("mrefundid"));
        return response;
    }

    public Map<String, Object> getRefundStatus(String mrefundid) throws Exception {
        if (mrefundid == null || mrefundid.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid mrefundid");
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        String data = zaloPayConfig.APP_ID + "|" + mrefundid + "|" + timestamp;
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, zaloPayConfig.KEY1, data);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("appid", zaloPayConfig.APP_ID));
        params.add(new BasicNameValuePair("mrefundid", mrefundid));
        params.add(new BasicNameValuePair("timestamp", timestamp));
        params.add(new BasicNameValuePair("mac", mac));

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            URIBuilder uri = new URIBuilder("https://sandbox.zalopay.com.vn/v001/tpe/getpartialrefundstatus");
            uri.addParameters(params);
            HttpGet get = new HttpGet(uri.build());

            log.info("Get refund status request params: {}", params);
            try (CloseableHttpResponse res = client.execute(get)) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
                StringBuilder resultJsonStr = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    resultJsonStr.append(line);
                }

                String responseBody = resultJsonStr.toString();
                log.info("Get refund status response: {}", responseBody);
                JSONObject result = new JSONObject(responseBody);
                Map<String, Object> response = new HashMap<>();
                for (String key : result.keySet()) {
                    response.put(key, result.get(key));
                }

                if (!result.has("returncode")) {
                    log.error("Get refund status response does not contain 'return_code': {}", responseBody);
                    throw new Exception("Invalid response from ZaloPay: 'return_code' not found");
                }

                int returnCode = result.getInt("returncode");
//                if (returnCode != 1) {
//                    String returnMessage = result.has("returnmessage") ? result.getString("returnmessage") : "Unknown error";
//                    log.warn("Get refund status failed with return_code: {}, message: {}", returnCode, returnMessage);
//                    throw new Exception("Get refund status failed: " + returnMessage);
//                }
                return response;
            }
        } catch (Exception e) {
            log.error("Error getting refund status: {}", e.getMessage(), e);
            throw new Exception("Error getting refund status: " + e.getMessage(), e);
        }
    }
}
