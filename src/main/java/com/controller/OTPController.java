package com.controller;

import com.Service.OTPService;
import com.dto.RequestDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/sms")
public class OTPController {

    @Autowired
    private SimpMessagingTemplate websocket;

    @Autowired
    private OTPService otpService;

    private final String TOPIC_DESTINATION = "/lesson/sms";

    private String getTimeStamp() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    @Operation(summary = "send OTP")
    @RequestMapping("/mobileNo")
    public void send(@RequestBody RequestDto sms) {
        try {
            otpService.send(sms);
            websocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ":SMS has been sent" + sms.getPhoneNumber());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

} //ENDCLASS
