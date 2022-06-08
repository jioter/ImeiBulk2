package com.example.imeibulk.service;


import com.example.imeibulk.entity.ResponseIMEICharacteristicsVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;


@Service
public class ParseInputImei {
    private static final Integer IMEI_LENGTH = 15;

    public static void processInputFile() throws IOException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();

        FileReader fr = new FileReader("/home/ohreshchuk/Projects/ImeiBulk/src/main/resources/IMEI.csv");

        BufferedReader br = new BufferedReader(fr);

        ArrayList<String> imeiList = new ArrayList<>();

        String line;

        while ((line = br.readLine()) != null) {
            if (line.equals("")) {
                continue;
            } else {
                String trimmedLine = line.trim();
                imeiList.add(trimmedLine);
            }
        }
        br.close();

        Instant start = Instant.now();

        int j = 0;
        FileWriter fileWriter = new FileWriter("/home/ohreshchuk/Projects/ImeiBulk/src/main/resources/rez_100k.csv");

        JSONArray responseList;
        ArrayList IMEIsToSend = new ArrayList<>();
        responseList = new JSONArray();
        for (int i = 0; i < imeiList.size(); i++) {
            IMEIsToSend.add(imeiList.get(i));
            if (j - i == -100 || i - imeiList.size() > -100) {
                if (!validateIMEI(imeiList.get(i))) {
                    fileWriter.append(imeiList.get(i)).append(",unknown,unknown,unknown,unknown,unknown");
                    fileWriter.append("\n");
                    i++;
                }
                    JSONArray jsonArray1 = CallDRS.callDRS(IMEIsToSend);
                    responseList.addAll(jsonArray1);

                    IMEIsToSend.clear();
                    j += 100;
            }
        }

        // TODO: Remove when tested under load ready.
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);

        System.out.println("Time taken: " + timeElapsed.toMillis() + " milliseconds");
        System.out.println("Size of responseList list = " + responseList.size());


        for (Object el : responseList) {

            ResponseIMEICharacteristicsVO responseImeiDeviceCharacteristics = null;
            if (el instanceof String) {
                System.out.println(el);
            } else if (el instanceof JSONObject && ((JSONObject) el).get("errorCode") != null) {
                String wrongImeiResponse = ((JSONObject) el).get("imei").toString();
                String wrongImeiResponseRez = wrongImeiResponse + ",unknown,unknown,unknown,unknown,unknown";
                fileWriter.append(wrongImeiResponseRez);
                fileWriter.append("\n");
            } else {
                responseImeiDeviceCharacteristics = objectMapper.readValue(el.toString(), ResponseIMEICharacteristicsVO.class);
            }

            if (responseImeiDeviceCharacteristics != null) {
                fileWriter.append((responseImeiDeviceCharacteristics).toString());
                fileWriter.append("\n");
            }
        }
        fileWriter.close();
    }

    public static boolean isNumber(String value) {
        if (value == null) {
            return false;
        }
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i)))
                return false;
        }
        return true;
    }


    public static boolean validateIMEI(String imei) {
        if (!isNumber(imei))
            return false;
        return imei.length() == IMEI_LENGTH;
    }
}
