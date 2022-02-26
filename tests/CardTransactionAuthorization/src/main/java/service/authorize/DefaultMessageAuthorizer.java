package service.authorize;

import dto.DataElement;
import dto.Message;
import dto.builder.DataElementBuilder;
import dto.builder.MessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import service.convert.BinaryToHexConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
@PropertySource(value = "classpath:/authorize/requests.authorize.properties")
public class DefaultMessageAuthorizer implements MessageAuthorizer {

    public static final String RESPONSE_MESSAGE_TYPE_INDICATOR = "0110";
    public static final String YEAR_MONTH_FORMAT = "MMyy";


    @Value("${requests.transaction.authorize.amount.zipcode.provided:20000}")
    private Long transactionAmountWhenZipCodeProvided;

    @Value("${requests.transaction.authorize.amount.zipcode.not.provided:10000}")
    private Long transactionAmountWhenZipCodeNotProvided;

    private BinaryToHexConverter binaryToHexConverter;

    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageAuthorizer.class);


    @Autowired
    public DefaultMessageAuthorizer(
            @Qualifier(value = "binaryToHexConverter")
                    BinaryToHexConverter binaryToHexConverter) {

        this.binaryToHexConverter = binaryToHexConverter;
    }

    @Override
    public Message authorize(Message message) {

        DataElement dataElement = message.getDataElement();
        DataElementBuilder dataElementBuilder = new DataElementBuilder();
        MessageBuilder messageBuilder = new MessageBuilder();
        MessageStatus messageStatus = MessageStatus.OK;

        if (!isTransactionAuthorized(message)) {
            messageStatus = MessageStatus.DE;
        }
        if(dataElement.getPanNumber() == null
                || dataElement.getExpirationDate() == null
                || dataElement.getTransactionAmount() == null ) {
            messageStatus = MessageStatus.ER;
        }
        if (dataElement.getPanCardLength() != null)
            dataElementBuilder.withPanCardLength(dataElement.getPanCardLength());
        if (dataElement.getPanNumber() != null) dataElementBuilder.withPanNumber(dataElement.getPanNumber());
        if (dataElement.getExpirationDate() != null)
            dataElementBuilder.withExpirationDate(dataElement.getExpirationDate());
        if (dataElement.getTransactionAmount() != null)
            dataElementBuilder.withTransactionAmount(dataElement.getTransactionAmount());
        dataElementBuilder.withResponseCode(messageStatus);
        if (dataElement.getNameLength() != null) dataElementBuilder.withNameLength(dataElement.getNameLength());
        if (dataElement.getCardHolderName() != null)
            dataElementBuilder.withCardHolderName(dataElement.getCardHolderName());
        if (dataElement.getZipCode() != null) dataElementBuilder.withZipCode(dataElement.getZipCode());

        return messageBuilder
                .withMessageTypeIndicator(RESPONSE_MESSAGE_TYPE_INDICATOR)
                .withBinaryValue(binaryToHexConverter.convert(message.getBinaryValue()))
                .withDataElement(dataElementBuilder.build()).build();
    }

    private boolean isTransactionAuthorized(Message message) {

        DataElement dataElement = message.getDataElement();
        Long transactionAmount = dataElement.getTransactionAmount();
        String zipCode = dataElement.getZipCode();
        String expirationDate = dataElement.getExpirationDate();

        boolean isValid = false;

         if (expirationDate != null) {
             SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YEAR_MONTH_FORMAT);
             simpleDateFormat.setLenient(false);
             Date expiry = null;
             try {
                 expiry = simpleDateFormat.parse(expirationDate);
             } catch (ParseException e) {
                 logger.error("Could not parse due to " + e.getLocalizedMessage());
             }
             if(expiry != null ) isValid = expiry.after(new Date());
             if(!isValid) return false;
         }

         isValid = false;

         if (zipCode != null && !zipCode.isBlank() && transactionAmount != null) {

            if (transactionAmount < transactionAmountWhenZipCodeProvided) {
                isValid = true;
            }
        } else if ((zipCode == null || zipCode.isBlank()) && transactionAmount != null) {

            if (transactionAmount < transactionAmountWhenZipCodeNotProvided) {
               isValid = true;
            }
        }
        return isValid;
    }
}
