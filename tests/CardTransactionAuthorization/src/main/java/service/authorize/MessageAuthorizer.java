package service.authorize;

import dto.DataElement;
import dto.Message;

/**
 * This method takes input incoming message
 * and construct an output message based
 * on authentication results
 */
public interface MessageAuthorizer {

    /**
     * Input messages are authorize based on following conditoins
     * @param message
     *
     * When Zip code is provided, a transaction is approved if amount is less than $200
     * When Zip code is not provided, a transaction is approved if amount is less than $100
     * Expiration Date is greater than the current dat
     * @return outputMessage
     */
    Message authorize(Message message);

}
