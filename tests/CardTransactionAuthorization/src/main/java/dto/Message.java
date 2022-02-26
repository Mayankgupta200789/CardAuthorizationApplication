package dto;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1493489521105008433L;
    private String messageTypeIndicator;
    private String binaryValue;
    private DataElement dataElement;

    public String getMessageTypeIndicator() {
        return messageTypeIndicator;
    }

    public void setMessageTypeIndicator(String messageTypeIndicator) {
        this.messageTypeIndicator = messageTypeIndicator;
    }

    public String getBinaryValue() {
        return binaryValue;
    }

    public void setBinaryValue(String binaryValue) {
        this.binaryValue = binaryValue;
    }

    public DataElement getDataElement() {
        return dataElement;
    }

    public void setDataElement(DataElement dataElement) {
        this.dataElement = dataElement;
    }

    @Override
    public String toString() {
        return messageTypeIndicator + binaryValue + dataElement;
    }
}
