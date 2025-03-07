package com.poly.app.domain.client.controller;


import com.poly.app.domain.client.service.ZaloPayService;
import com.poly.app.infrastructure.config.ZaloPayConfig;
import com.poly.app.infrastructure.util.HMACUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/zalopay")
public class ZaloPayPaymentController {
    private final ZaloPayConfig zaloPayConfig;
    @Autowired
    ZaloPayService zaloPayService;

    public ZaloPayPaymentController(ZaloPayConfig zaloPayConfig) {
        this.zaloPayConfig = zaloPayConfig;
    }

    public static String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

//    @PostMapping(value = "/create-order")
//    public Map<String, Object> createPayment(@RequestParam(name = "appuser") String appuser,
//                                             @RequestParam(name = "amount") Long amount,
//                                             @RequestParam(name = "order_id") Long order_id) throws Exception {
//
//        return zaloPayService.createPayment(appuser, amount, order_id);
//    }

    @GetMapping(value = "/getstatusbyapptransid")
    public Map<String, Object> getStatusByApptransid(
            @RequestParam(name = "apptransid") String apptransid)
            throws Exception {
       return zaloPayService.getStatusByApptransid(apptransid);
    }

}
