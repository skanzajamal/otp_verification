package com.controller;

import com.Service.OTPService;
import com.config.TwilioConfig;
import com.dto.RequestDto;
import com.twilio.Twilio;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import com.twilio.rest.verify.v2.service.VerificationCheck;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/sms")
public class OTPController {

    @Autowired
    private SimpMessagingTemplate websocket;

    @Autowired
    private OTPService otpService;

    @Autowired
    private TwilioConfig twilioConfig;

    private final String TOPIC_DESTINATION = "/lesson/sms";

    @PostConstruct
    public void init(){
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }

    private String getTimeStamp() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    @Operation(summary = "send OTP")
    @RequestMapping("/mobileNo")
    public void send(@RequestBody RequestDto sms) {
        try {
            otpService.send(sms, twilioConfig.getTrialNumber());
            websocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + " Your OTP has been sent to your verified phone number" + sms.getPhoneNumber());
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Operation(summary = "verify OTP")
    @GetMapping("/verifyOTP")
    public ResponseEntity<?> verifyUserOTP() throws Exception {

        try {
            VerificationCheck verificationCheck = VerificationCheck.creator(
                    twilioConfig.getAccountSid())
                    .setTo("+12407433274")
                    .setCode("840463")
                    .create();

            System.out.println(verificationCheck.getStatus());

        } catch (Exception e) {
            return new ResponseEntity<>("Verification failed.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("This user's verification has been completed successfully", HttpStatus.OK);
    }

} //ENDCLASS
