package service.authorize;

import dto.Message;

import java.util.List;

/**
 * This interface is responsible to
 * authorize incoming messages and
 * generate output messages
 */
public interface CreditCardAuthorizer {

    /**
     * This method accepts list of input messages
     * and authorize it based on the requirements
     *
     * @param messages
     * @return outputMessages
     */
    List<Message> authorize(List<Message> messages);
}

