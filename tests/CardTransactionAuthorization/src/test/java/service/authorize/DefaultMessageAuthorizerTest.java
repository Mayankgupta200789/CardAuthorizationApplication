package service.authorize;

import dto.DataElement;
import dto.Message;
import dto.builder.DataElementBuilder;
import dto.builder.MessageBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.convert.BinaryToHexConverter;
import service.convert.DefaultBinaryToHexConverter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DefaultMessageAuthorizer.class,
        DefaultBinaryToHexConverter.class
})
@TestPropertySource(locations = {"/authorize/requests.authorize.properties"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DefaultMessageAuthorizerTest {

    public static final String ANY_NAME = "AnyName";
    public static final String NAME_LENGTH = "6";
    public static final String PAN_CARD_LENGTH = "15";
    public static final String EXPIRATION_DATE = "1225";
    public static final long TRANSACTION_AMOUNT = 20000L;
    public static final String PAN_NUMBER = "123456789012345";
    public static final String MESSAGE_TYPE_INDICATOR = "0100";
    public static final String BINARY_VALUE = "11101100";
    @Autowired
    private BinaryToHexConverter binaryToHexConverter;

    @Autowired
    private MessageAuthorizer messageAuthorizer;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void authorizeWhenZipCodeIsProvided() {

        MessageBuilder messageBuilder = new MessageBuilder();
        DataElement dataElement = new DataElementBuilder()
                .withPanCardLength("15")
                .withNameLength("6")
                .withCardHolderName("AnyName")
                .withExpirationDate("1225")
                .withTransactionAmount(10000L)
                .withZipCode("98908")
                .withPanNumber("123456789012345").build();

        Message message = messageBuilder
                .withDataElement(dataElement)
                .withMessageTypeIndicator("0100")
                .withBinaryValue("11101100").build();
        Message result = messageAuthorizer.authorize(message);

        Assert.assertEquals("fc",result.getBinaryValue());
        Assert.assertEquals(MessageStatus.OK,result.getDataElement().getResponseCode());
        Assert.assertEquals("0110",result.getMessageTypeIndicator());
        Assert.assertEquals("15",result.getDataElement().getPanCardLength());
        Assert.assertEquals("6",result.getDataElement().getNameLength());
        Assert.assertEquals("AnyName",result.getDataElement().getCardHolderName());
        Assert.assertEquals("10000",result.getDataElement().getTransactionAmount() + "");
        Assert.assertEquals("98908",result.getDataElement().getZipCode());
        Assert.assertEquals("123456789012345",result.getDataElement().getPanNumber() );
    }

    @Test
    public void authorizeWhenZipCodeIsNotProvided() {

        MessageBuilder messageBuilder = new MessageBuilder();
        DataElement dataElement = new DataElementBuilder()
                .withPanCardLength("15")
                .withNameLength("6")
                .withCardHolderName("AnyName")
                .withExpirationDate("1225")
                .withTransactionAmount(5000L)
                .withPanNumber("123456789012345").build();

        Message message = messageBuilder
                .withDataElement(dataElement)
                .withMessageTypeIndicator("0100")
                .withBinaryValue("11101100").build();
        Message result = messageAuthorizer.authorize(message);

        Assert.assertEquals("fc",result.getBinaryValue());
        Assert.assertEquals(MessageStatus.OK,result.getDataElement().getResponseCode());
        Assert.assertEquals("0110",result.getMessageTypeIndicator());
        Assert.assertEquals("15",result.getDataElement().getPanCardLength());
        Assert.assertEquals("6",result.getDataElement().getNameLength());
        Assert.assertEquals("AnyName",result.getDataElement().getCardHolderName());
        Assert.assertEquals("5000",result.getDataElement().getTransactionAmount() + "");
        Assert.assertNull(result.getDataElement().getZipCode());
        Assert.assertEquals("123456789012345",result.getDataElement().getPanNumber() );
    }

    @Test
    public void authorizeWhenExpirationDateBeforeCurrentDate() {

        MessageBuilder messageBuilder = new MessageBuilder();
        DataElement dataElement = new DataElementBuilder()
                .withPanCardLength("15")
                .withNameLength("6")
                .withCardHolderName("AnyName")
                .withExpirationDate("1220")
                .withTransactionAmount(5000L)
                .withPanNumber("123456789012345").build();

        Message message = messageBuilder
                .withDataElement(dataElement)
                .withMessageTypeIndicator("0100")
                .withBinaryValue("11101100").build();
        Message result = messageAuthorizer.authorize(message);

        Assert.assertEquals("fc",result.getBinaryValue());
        Assert.assertEquals(MessageStatus.DE,result.getDataElement().getResponseCode());
        Assert.assertEquals("0110",result.getMessageTypeIndicator());
        Assert.assertEquals("15",result.getDataElement().getPanCardLength());
        Assert.assertEquals("6",result.getDataElement().getNameLength());
        Assert.assertEquals("AnyName",result.getDataElement().getCardHolderName());
        Assert.assertEquals("5000",result.getDataElement().getTransactionAmount() + "");
        Assert.assertNull(result.getDataElement().getZipCode());
        Assert.assertEquals("123456789012345",result.getDataElement().getPanNumber() );
    }

    @Test
    public void authorizeFailureWhenZipCodeIsProvided() {

        MessageBuilder messageBuilder = new MessageBuilder();
        DataElement dataElement = new DataElementBuilder()
                .withPanCardLength("15")
                .withNameLength("6")
                .withCardHolderName("AnyName")
                .withExpirationDate("1225")
                .withZipCode("98985")
                .withTransactionAmount(30000L)
                .withPanNumber("123456789012345").build();

        Message message = messageBuilder
                .withDataElement(dataElement)
                .withMessageTypeIndicator("0100")
                .withBinaryValue("11101100").build();
        Message result = messageAuthorizer.authorize(message);

        Assert.assertEquals("fc",result.getBinaryValue());
        Assert.assertEquals(MessageStatus.DE,result.getDataElement().getResponseCode());
        Assert.assertEquals("0110",result.getMessageTypeIndicator());
        Assert.assertEquals("15",result.getDataElement().getPanCardLength());
        Assert.assertEquals("6",result.getDataElement().getNameLength());
        Assert.assertEquals("AnyName",result.getDataElement().getCardHolderName());
        Assert.assertEquals("30000",result.getDataElement().getTransactionAmount() + "");
        Assert.assertEquals("98985",result.getDataElement().getZipCode());
        Assert.assertEquals("123456789012345",result.getDataElement().getPanNumber() );
    }

    @Test
    public void authorizeFailureWhenZipCodeIsNotProvided() {

        MessageBuilder messageBuilder = new MessageBuilder();
        DataElement dataElement = new DataElementBuilder()
                .withPanCardLength(PAN_CARD_LENGTH)
                .withNameLength(NAME_LENGTH)
                .withCardHolderName(ANY_NAME)
                .withExpirationDate(EXPIRATION_DATE)
                .withTransactionAmount(TRANSACTION_AMOUNT)
                .withPanNumber(PAN_NUMBER).build();

        Message message = messageBuilder
                .withDataElement(dataElement)
                .withMessageTypeIndicator(MESSAGE_TYPE_INDICATOR)
                .withBinaryValue(BINARY_VALUE).build();
        Message result = messageAuthorizer.authorize(message);

        Assert.assertEquals("fc",result.getBinaryValue());
        Assert.assertEquals(MessageStatus.DE,result.getDataElement().getResponseCode());
        Assert.assertEquals("0110",result.getMessageTypeIndicator());
        Assert.assertEquals("15",result.getDataElement().getPanCardLength());
        Assert.assertEquals("6",result.getDataElement().getNameLength());
        Assert.assertEquals("AnyName",result.getDataElement().getCardHolderName());
        Assert.assertEquals("20000",result.getDataElement().getTransactionAmount() + "");
        Assert.assertEquals("123456789012345",result.getDataElement().getPanNumber() );
    }

    @Test
    public void authorizeFailureERResponseCode() {

        MessageBuilder messageBuilder = new MessageBuilder();
        DataElement dataElement = new DataElementBuilder()
                .withPanCardLength(PAN_CARD_LENGTH)
                .withNameLength(NAME_LENGTH)
                .withCardHolderName(ANY_NAME)
                .withTransactionAmount(TRANSACTION_AMOUNT)
                .withExpirationDate(EXPIRATION_DATE)
                .build();

        Message message = messageBuilder
                .withDataElement(dataElement)
                .withMessageTypeIndicator(MESSAGE_TYPE_INDICATOR)
                .withBinaryValue(BINARY_VALUE).build();
        Message result = messageAuthorizer.authorize(message);

        Assert.assertEquals("fc",result.getBinaryValue());
        Assert.assertEquals(MessageStatus.ER,result.getDataElement().getResponseCode());
        Assert.assertEquals("0110",result.getMessageTypeIndicator());
        Assert.assertEquals("15",result.getDataElement().getPanCardLength());
        Assert.assertEquals("6",result.getDataElement().getNameLength());
        Assert.assertEquals("AnyName",result.getDataElement().getCardHolderName());
        Assert.assertEquals("20000",result.getDataElement().getTransactionAmount() + "");
        Assert.assertEquals(EXPIRATION_DATE,result.getDataElement().getExpirationDate());

    }

    @Test
    public void authorizeFailureERResponseCodeWhenExpirationIsMissing() {

        MessageBuilder messageBuilder = new MessageBuilder();
        DataElement dataElement = new DataElementBuilder()
                .withPanCardLength(PAN_CARD_LENGTH)
                .withNameLength(NAME_LENGTH)
                .withCardHolderName(ANY_NAME)
                .withPanNumber(PAN_NUMBER)
                .withTransactionAmount(TRANSACTION_AMOUNT)
                .build();

        Message message = messageBuilder
                .withDataElement(dataElement)
                .withMessageTypeIndicator(MESSAGE_TYPE_INDICATOR)
                .withBinaryValue(BINARY_VALUE).build();
        Message result = messageAuthorizer.authorize(message);

        Assert.assertEquals("fc",result.getBinaryValue());
        Assert.assertEquals(MessageStatus.ER,result.getDataElement().getResponseCode());
        Assert.assertEquals("0110",result.getMessageTypeIndicator());
        Assert.assertEquals("15",result.getDataElement().getPanCardLength());
        Assert.assertEquals("6",result.getDataElement().getNameLength());
        Assert.assertEquals("AnyName",result.getDataElement().getCardHolderName());
        Assert.assertEquals("20000",result.getDataElement().getTransactionAmount() + "");
    }

    public void authorizeFailureERResponseCodeWhenTransactionAmountIsMissing() {

        MessageBuilder messageBuilder = new MessageBuilder();
        DataElement dataElement = new DataElementBuilder()
                .withPanCardLength(PAN_CARD_LENGTH)
                .withNameLength(NAME_LENGTH)
                .withCardHolderName(ANY_NAME)
                .withPanNumber(PAN_NUMBER)
                .withExpirationDate(EXPIRATION_DATE)
                .build();

        Message message = messageBuilder
                .withDataElement(dataElement)
                .withMessageTypeIndicator(MESSAGE_TYPE_INDICATOR)
                .withBinaryValue(BINARY_VALUE).build();
        Message result = messageAuthorizer.authorize(message);

        Assert.assertEquals("ec",result.getBinaryValue());
        Assert.assertEquals(MessageStatus.ER,result.getDataElement().getResponseCode());
        Assert.assertEquals("0110",result.getMessageTypeIndicator());
        Assert.assertEquals("15",result.getDataElement().getPanCardLength());
        Assert.assertEquals("6",result.getDataElement().getNameLength());
        Assert.assertEquals("AnyName",result.getDataElement().getCardHolderName());
    }
}