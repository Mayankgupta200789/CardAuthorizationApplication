package service.parse;

import dto.DataElement;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.authorize.MessageStatus;
import service.config.ServiceConfiguration;
import service.extract.DataElementMessageExtractor;
import service.extract.DefaultDataElementMessageExtractor;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {service.parse.PANCardParser.class,
                ExpirationDateParser.class,
                TransactionAmountParser.class,
                CardHolderNameParser.class,
                ResponseCodeParser.class,
                ZipCodeHolderParser.class})
@TestPropertySource(locations = "/context/requests.file.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DefaultDataElementMessageExtractorTest {

    @Autowired
    public List<DataElementMessageParser> dataElementMessageParsers;

    
    private DataElementMessageExtractor dataElementMessageExtractor;

    @Before
    public void setUp() throws Exception {
        dataElementMessageExtractor = new DefaultDataElementMessageExtractor(dataElementMessageParsers);
    }

    @Test
    public void parse1() {

        String dataElementString = "1651051051051051001225000001100011MASTER YODA90089";
        String bitMap = "11101100";
        DataElement dataElement = dataElementMessageExtractor.extract(dataElementString, bitMap);

        Assert.assertEquals("5105105105105100",dataElement.getPanNumber());
        Assert.assertEquals("1225",dataElement.getExpirationDate());
        Assert.assertEquals("11000",dataElement.getTransactionAmount() + "");
        Assert.assertEquals("MASTER YODA",dataElement.getCardHolderName());
        Assert.assertEquals("90089",dataElement.getZipCode());


    }

    @Test
    public void parse2() {

        String dataElementString = "16411111111111111112180000001000";
        String bitMap = "11100000";
        DataElement dataElement = dataElementMessageExtractor.extract(dataElementString, bitMap);

        Assert.assertEquals("4111111111111111",dataElement.getPanNumber());
        Assert.assertEquals("1218",dataElement.getExpirationDate());
        Assert.assertEquals("1000",dataElement.getTransactionAmount() + "");
        Assert.assertNull(dataElement.getCardHolderName());
        Assert.assertNull(dataElement.getZipCode());
        Assert.assertNull(dataElement.getResponseCode());


    }

    @Test
    public void parse3() {

        String dataElementString = "12250000001000";
        String bitMap = "01100000";
        DataElement dataElement = dataElementMessageExtractor.extract(dataElementString, bitMap);

        Assert.assertNull(dataElement.getPanNumber());
        Assert.assertEquals("1225",dataElement.getExpirationDate());
        Assert.assertEquals("1000",dataElement.getTransactionAmount() + "");
        Assert.assertNull(dataElement.getCardHolderName());
        Assert.assertNull(dataElement.getZipCode());
        Assert.assertNull(dataElement.getResponseCode());


    }

    @Test
    public void parse4() {

        String dataElementString = "16411111111111111112250000001000OK";
        String bitMap = "11110000";
        DataElement dataElement = dataElementMessageExtractor.extract(dataElementString, bitMap);

        Assert.assertEquals("4111111111111111",dataElement.getPanNumber());
        Assert.assertEquals("1225",dataElement.getExpirationDate());
        Assert.assertEquals("1000",dataElement.getTransactionAmount() + "");
        Assert.assertNull(dataElement.getCardHolderName());
        Assert.assertNull(dataElement.getZipCode());
        Assert.assertEquals(MessageStatus.OK,dataElement.getResponseCode());



    }

    @Test
    public void parse5() {

        DataElement dataElement = dataElementMessageExtractor.extract(null, null);
        Assert.assertNull(dataElement);



    }
}