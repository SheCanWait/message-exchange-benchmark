package com.benchmark.receiver;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RestResponseRequestorAndReceiver {

    @GetMapping("/exampleAddress")
    public String requestAndReceive(String requestNumber) throws IOException {
        URL url = new URL("http://localhost:8080/exampleAddress?name=" + requestNumber);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(HttpMethod.GET.name());

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        return content.toString();
    }

//    private static String getParamsString(Map<String, String> params)
//            throws UnsupportedEncodingException {
//        StringBuilder result = new StringBuilder();
//
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
//            result.append("=");
//            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
//            result.append("&");
//        }
//
//        String resultString = result.toString();
//        return resultString.length() > 0
//                ? resultString.substring(0, resultString.length() - 1)
//                : resultString;
//    }

}
