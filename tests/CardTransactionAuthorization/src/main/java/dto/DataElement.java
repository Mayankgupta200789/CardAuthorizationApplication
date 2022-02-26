package dto;

import service.authorize.MessageStatus;

import java.io.Serializable;
import java.time.YearMonth;

public class DataElement implements Serializable {

    private static final long serialVersionUID = 1L;
    private String panCardLength;
    private String panNumber;
    private String expirationDate;
    private Long transactionAmount;
    private MessageStatus responseCode;
    private String nameLength;
    private String cardHolderName;
    private String zipCode;

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public MessageStatus getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(MessageStatus responseCode) {
        this.responseCode = responseCode;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getPanCardLength() {
        return panCardLength;
    }

    public void setPanCardLength(String panCardLength) {
        this.panCardLength = panCardLength;
    }

    public String getNameLength() {
        return nameLength;
    }

    public void setNameLength(String nameLength) {
        this.nameLength = nameLength;
    }

    @Override
    public String toString() {
        return panCardLength
                + panNumber
                + expirationDate
                + transactionAmount
                + responseCode
                + nameLength
                + cardHolderName
                + zipCode;
    }
}
