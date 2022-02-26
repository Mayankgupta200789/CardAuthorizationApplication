package dto.builder;

import dto.DataElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import service.authorize.MessageStatus;
import service.resolve.DataElementBitSetResolver;

import java.time.YearMonth;
import java.util.Map;

public class DataElementBuilder {

    private DataElement dataElement = new DataElement();


    public DataElementBuilder withPanNumber(String panNumber) {
        dataElement.setPanNumber(panNumber);
        return this;
    }

    public DataElementBuilder withExpirationDate(String expirationDate) {
        dataElement.setExpirationDate(expirationDate);
        return this;
    }

    public DataElementBuilder withTransactionAmount(Long transactionAmount) {
        dataElement.setTransactionAmount(transactionAmount);
        return this;
    }

    public DataElementBuilder withResponseCode(MessageStatus responseCode) {
        dataElement.setResponseCode(responseCode);
        return this;
    }

    public DataElementBuilder withNameLength(String nameLength) {
        dataElement.setNameLength(nameLength);
        return this;
    }

    public DataElementBuilder withCardHolderName(String cardHolderName) {
        dataElement.setCardHolderName(cardHolderName);
        return this;
    }

    public DataElementBuilder withZipCode(String zipCode) {
        dataElement.setZipCode(zipCode);
        return this;
    }

    public DataElementBuilder withPanCardLength(String panCardLength ){
        dataElement.setPanCardLength(panCardLength);
        return this;
    }

    public  DataElementBuilder withDataElement(DataElement dataElement) {
        this.dataElement = dataElement;
        return this;
    }


    public DataElement build() {

        return this.dataElement;
    }
}