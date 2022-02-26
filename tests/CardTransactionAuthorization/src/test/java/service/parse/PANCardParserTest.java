package service.parse;

import dto.DataElement;
import dto.builder.DataElementBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
        PANCardParser.class)
@TestPropertySource(locations = "/context/requests.file.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PANCardParserTest {

    @Autowired
    private PANCardParser panCardParser;

    @Test
    public void parse() {
        String dataElementString = "1651051051051051001225000001100011MASTER YODA90089";
        String bitMap = "11111100";
        DataElementBuilder dataElementBuilder = new DataElementBuilder();

        panCardParser.parse(dataElementString,bitMap,dataElementBuilder,0);
        DataElement dataElement = dataElementBuilder.build();
        Assert.assertEquals("16",dataElement.getPanCardLength());
        Assert.assertEquals("5105105105105100",dataElement.getPanNumber() + "");
    }
}