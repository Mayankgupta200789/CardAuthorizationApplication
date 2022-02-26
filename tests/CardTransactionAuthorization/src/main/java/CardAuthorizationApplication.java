import dto.Message;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.authorize.CreditCardAuthorizer;
import service.config.ServiceConfiguration;
import service.parse.MessageParser;
import service.response.write.MessageResponseWriter;

import java.util.List;

/**
 * This is entry class for card authorization application.
 *
 *
 *
 */

public class CardAuthorizationApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(ServiceConfiguration.class);

        MessageParser messageParser = (MessageParser)annotationConfigApplicationContext
                .getBean("messageParser");

        CreditCardAuthorizer creditCardAuthorizer = (CreditCardAuthorizer) annotationConfigApplicationContext
                .getBean("creditCardAuthorizer");

        MessageResponseWriter messageResponseWriter =
                (MessageResponseWriter)annotationConfigApplicationContext
                        .getBean("messageResponseWriter");

        if(args.length > 0 ) {
            int i = 1;
            for(String inputFileName : args ) {
                List<Message> messages = messageParser.parse(inputFileName);
                if(messages != null ) {
                    List<Message> outputMessages = creditCardAuthorizer.authorize(messages);
                    if(outputMessages != null ) messageResponseWriter.write(outputMessages, i++);
                }
            }
        }

    }
}
