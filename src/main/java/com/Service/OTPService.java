package com.Service;

import com.dto.RequestDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;
import java.text.DecimalFormat;
import java.util.Random;

@Service
public class OTPService {

    private static final Integer EXPIRE_MINS = 0;

    //method to send OTP
    public void send(RequestDto sms, String trialNumber) {
        var otp = generateOTP();
        String otpMessage = "Dear Customer, Your OTP is " + otp + ". Please contact our service, if you have not requested for this. Do not share your OTP to anyone. Thank you!";
        Message message = Message.creator(new PhoneNumber(sms.getPhoneNumber()),
                new PhoneNumber(trialNumber),
                otpMessage)
                .create();
    }

    // 6 digit OTP
    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

} //ENDCLASS
