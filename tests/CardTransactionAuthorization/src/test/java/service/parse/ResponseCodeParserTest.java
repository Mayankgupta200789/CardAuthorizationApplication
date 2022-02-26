package service.parse;

import dto.DataElement;
import dto.builder.DataElementBuilder;
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

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
        ResponseCodeParser.class)
@TestPropertySource(locations = "/context/requests.file.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ResponseCodeParserTest {

    @Autowired
    private ResponseCodeParser responseCodeParser;

    @Test
    public void parse() {
        String dataElementString = "16411111111111111112250000001000OK";
        String bitMap = "11110000";
        DataElementBuilder dataElementBuilder = new DataElementBuilder();

        responseCodeParser.parse(dataElementString,bitMap,dataElementBuilder,32);
        DataElement dataElement = dataElementBuilder.build();
        Assert.assertEquals(MessageStatus.OK,dataElement.getResponseCode());
    }
}