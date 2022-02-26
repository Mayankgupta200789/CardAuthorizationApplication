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
        CardHolderNameParser.class)
@TestPropertySource(locations = "/context/requests.file.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CardHolderNameParserTest {

    private CardHolderNameParser cardHolderNameParser;

    @Before
    public void setUp() throws Exception {

        cardHolderNameParser = new CardHolderNameParser();
    }

    @Test
    public void parse1() {
        String dataElementString = "1651051051051051001225000001100011MASTER YODA90089";
        String bitMap = "11111100";
        DataElementBuilder dataElementBuilder = new DataElementBuilder();

        int nextIndex = 32;

        cardHolderNameParser.parse(dataElementString, bitMap, dataElementBuilder, nextIndex);

        DataElement dataElement = dataElementBuilder.build();

        Assert.assertEquals("MASTER YODA",dataElement.getCardHolderName());
        Assert.assertEquals("11",dataElement.getNameLength());
    }
}