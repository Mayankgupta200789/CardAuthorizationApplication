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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.convert.DefaultBinaryToHexConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        DefaultMessageAuthorizer.class,
        DefaultBinaryToHexConverter.class
})
@TestPropertySource(locations = {"/authorize/requests.authorize.properties"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DefaultCreditCardAuthorizerTest {


    private CreditCardAuthorizer creditCardAuthorizer;

    @Autowired
    private MessageAuthorizer messageAuthorizer;


    @Before
    public void setUp() throws Exception {

        creditCardAuthorizer = new DefaultCreditCardAuthorizer(messageAuthorizer);
    }

    @Test
    public void authorize() {

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

        List<Message> messages = creditCardAuthorizer.authorize(Collections.singletonList(message));

        Assert.assertNotNull(messages);
    }

    @Test
    public void authorizeWithNull() {

        try {
            List<Message> messages = creditCardAuthorizer.authorize(null);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void authorizeEmpty() {

        List<Message> messages = creditCardAuthorizer.authorize(new ArrayList<>());

        Assert.assertNotNull(messages);
    }
}