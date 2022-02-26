package service.parse;

import dto.DataElement;
import dto.builder.DataElementBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
        ZipCodeHolderParser.class)
@TestPropertySource(locations = "/context/requests.file.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ZipCodeHolderParserTest {

    private ZipCodeHolderParser zipCodeHolderParser;

    @Before
    public void setUp() throws Exception {

        zipCodeHolderParser = new ZipCodeHolderParser();
    }

    @Test
    public void parse() {

        String dataElementString = "1651051051051051001225000001100011MASTER YODA90089";
        String bitMap = "11111100";
        DataElementBuilder dataElementBuilder = new DataElementBuilder();

        zipCodeHolderParser.parse(dataElementString,bitMap,dataElementBuilder,45);
        DataElement dataElement = dataElementBuilder.build();
        Assert.assertEquals("90089",dataElement.getZipCode());
    }
}