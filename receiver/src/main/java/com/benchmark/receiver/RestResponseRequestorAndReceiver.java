package com.benchmark.receiver;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class RestResponseRequestorAndReceiver {

    public void requestAndReceive(int numberOfRequests) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for(int i = 0 ; i < numberOfRequests ; i++) {
            int finalI = i;
            executorService.submit(() -> {

                URL url = null;
                try {
                    url = new URL("http://localhost:8080/exampleAddress?name=" + finalI);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                HttpURLConnection con = null;
                try {
                    con = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    con.setRequestMethod(HttpMethod.GET.name());
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }

                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);

                log.info("Request nr " + finalI + " requested");

                BufferedReader in = null;
                try {
                    in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String inputLine = null;
                StringBuilder content = new StringBuilder();
                while (true) {
                    try {
                        if (!((inputLine = in.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    content.append(inputLine);
                }
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                log.info("Rest response nr " + finalI + " received");
            });
        }

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
