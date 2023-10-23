package com.Service;

import com.config.TwilioConfig;
import com.dto.RequestDto;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {

    @Autowired
    private TwilioConfig twilioConfig;

    private static final Integer EXPIRE_MINS = 0;

    private Cache<Object, Object> optCache;

    public OTPService() {
        super();
        optCache = CacheBuilder.newBuilder().expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build();
    }

    public void send(RequestDto sms) {
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
        var otp = generateOTP();
        String otpMessage = "Dear Customer, Your OTP is " + otp + ". Please contact our service, if you have not requested for this. Do not share your OTP to anyone. Thank you!";
        Message message = Message.creator(new PhoneNumber(sms.getPhoneNumber()),
                new PhoneNumber(twilioConfig.getTrialNumber()),
                otpMessage)
                .create();
    }

    // 6 digit OTP
    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

} //ENDCLASS
