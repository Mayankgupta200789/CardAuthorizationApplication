package dto.builder;

import dto.DataElement;
import dto.Message;

public class MessageBuilder {

    private Message message = new Message();

    public MessageBuilder withMessageTypeIndicator(String messageTypeIndicator) {
        this.message.setMessageTypeIndicator(messageTypeIndicator);
        return this;
    }

    public MessageBuilder withBinaryValue(String binaryValue) {
        this.message.setBinaryValue(binaryValue);
        return this;
    }

    public MessageBuilder withDataElement(DataElement dataElement) {
        this.message.setDataElement(dataElement);
        return this;
    }

    public Message build() {
        return message;
    }
}