package service.response.write;

import dto.DataElement;
import dto.Message;
import dto.builder.DataElementBuilder;
import dto.builder.MessageBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.authorize.MessageStatus;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
        DefaultMessageResponseWriter.class)
@TestPropertySource(locations = "/context/requests.file.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DefaultMessageResponseWriterTest {

    public static final String ANY_NAME = "AnyName";
    public static final String NAME_LENGTH = "6";
    public static final String PAN_CARD_LENGTH = "15";
    public static final String EXPIRATION_DATE = "1225";
    public static final long TRANSACTION_AMOUNT = 20000L;
    public static final String PAN_NUMBER = "123456789012345";
    public static final String MESSAGE_TYPE_INDICATOR = "0110";
    public static final String BINARY_VALUE = "f0";

    @Autowired
    private MessageResponseWriter messageResponseWriter;

    @Test
    public void write() {

        Message message1 = getMessage(PAN_CARD_LENGTH, NAME_LENGTH, ANY_NAME, PAN_NUMBER, EXPIRATION_DATE, TRANSACTION_AMOUNT, MessageStatus.OK, MESSAGE_TYPE_INDICATOR, BINARY_VALUE,"12345");
        Message message2 = getMessage(PAN_CARD_LENGTH, "11", "MASTER YODA", "111111111111111", "1225", 10000L, MessageStatus.OK, "0110", "f0","90089");

        List<Message> messages = Arrays.asList(message1,message2);
        try {
            messageResponseWriter.write(messages, 1);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.fail("Test failed due to exception thrown");
        }
    }

    private Message getMessage(String panCardLength, String nameLength,
                               String anyName, String panNumber, String expirationDate,
                               long transactionAmount, MessageStatus responseCode,
                               String messageTypeIndicator,
                               String hexValue,
                                String zipCode) {
        MessageBuilder messageBuilder = new MessageBuilder();
        DataElement dataElement = new DataElementBuilder()
                .withPanCardLength(panCardLength)
                .withNameLength(nameLength)
                .withCardHolderName(anyName)
                .withPanNumber(panNumber)
                .withExpirationDate(expirationDate)
                .withTransactionAmount(transactionAmount)
                .withResponseCode(responseCode)
                .withZipCode(zipCode)
                .build();

        return messageBuilder
                .withDataElement(dataElement)
                .withMessageTypeIndicator(messageTypeIndicator)
                .withBinaryValue(hexValue).build();
    }
}