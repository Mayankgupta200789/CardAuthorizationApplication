package service.authorize;

import dto.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Default implementation of credit card
 * authorizer.
 */
public class DefaultCreditCardAuthorizer implements CreditCardAuthorizer {


    private MessageAuthorizer messageAuthorizer;

    @Autowired
    public DefaultCreditCardAuthorizer(@Qualifier(value = "messageAuthorizer")
                                                   MessageAuthorizer messageAuthorizer) {

        this.messageAuthorizer = messageAuthorizer;
    }


    @Override
    public List<Message> authorize(List<Message> messages) {

        List<Message> result = new ArrayList<>();
        if(messages != null ) {
            for (Message message : messages) {
                result.add(messageAuthorizer.authorize(message));
            }
        }
        return result;
    }
}
