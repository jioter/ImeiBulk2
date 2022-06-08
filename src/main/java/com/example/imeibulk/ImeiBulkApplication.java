package com.example.imeibulk;

import com.example.imeibulk.service.ParseInputImei;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ImeiBulkApplication {

    public static void main(String[] args) throws IOException, ParseException {

        SpringApplication.run(ImeiBulkApplication.class, args);

        ParseInputImei.processInputFile();

//        CallDRS.callDRS();
    }

}
