/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author hp
 */
public class SmsAPI {
    public static void send(String numero, String contenu) {

        final String AUTH_TOKEN = "e5cf98a834c07ce42c416e2fcb423ff3";
        final String ACCOUNT_SID = "AC54fb0b113f7b1db175200b31c4bc92c0";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new PhoneNumber(numero), // TO
                new PhoneNumber("+16204559196"), // FROM
                contenu
        ).create();

        System.out.println(message.getSid());
    }
}
