package com.example.imeibulk.controller;

import com.example.imeibulk.service.ParseInputImei;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/")
public class ImeiBulkController {

    @Autowired
    private ParseInputImei imeiBulkService;

    @RequestMapping(value = "/insertFile", method = RequestMethod.POST)
    @ResponseBody
    public String insertEirFile(@RequestParam(value = "imeiFile") MultipartFile file, HttpServletRequest request) throws IOException {
        String insertResult = "";
//        ParseInputImei.processInputFile();
        return insertResult;
    }


}
