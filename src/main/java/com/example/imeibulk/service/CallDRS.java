package com.example.imeibulk.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CallDRS {
    private static final String HTTP_DRS_API_SUFFIX = "/characteristics/1_1/generic/groups?filter=GENERATION.4G,%20GENERATION.3G,%20GENERATION.2G&imei=";
    private static final String apiURL = "http://localhost:8180/drs/";
    private static final String urlToCall = apiURL + HTTP_DRS_API_SUFFIX;

//    private static final Logger IMEI_BULK_LOGGER = LoggerFactory.getLogger("IMEI_BULK_LOG");

//    private static String testImei = "865260042084578";

    public static JSONArray callDRS(ArrayList<String> imei) throws IOException, ParseException {
        StringBuilder stringBuilder = new StringBuilder();
        for (String el : imei) {
            stringBuilder.append(el);
            stringBuilder.append(",");
        }

//        IMEI_BULK_LOGGER.info("Received async response from CEIR-N for triplet: " + triplet + " with reqId: " + reqId + ". For action: " + asyncCEIRData.getAction() + " and List: " + asyncCEIRData.getList());
// - valid: Start work with file: filename. Input count___. IMEI, code, errorMessage

// - Finish work with file: input count:___ ; failed count:___

        HttpGet request = new HttpGet(urlToCall + stringBuilder.substring(0, stringBuilder.length() - 1));
//        HttpGet request = new HttpGet(urlToCall + testImei);h

        HttpClient client = new DefaultHttpClient();

        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();

        if ((statusCode >= 400)) {
            // TODO: add exception handling "FAILED"
            return null;
        }

        BufferedReader rd;
        rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";

        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(result.toString());
        JSONObject result1 = (JSONObject) json.get("result");
        return (JSONArray) result1.get("devices");
    }
}

