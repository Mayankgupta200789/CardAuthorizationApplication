
package service.parse;

import dto.Message;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.context.RequestFileInputContext;
import service.extract.DefaultDataElementMessageExtractor;
import service.resolve.DefaultDataElementBitSetResolver;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RequestFileInputContext.class)
@TestPropertySource(locations = "/context/requests.file.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DefaultMessageParserTest {

    @Autowired
    private RequestFileInputContext requestFileInputContext;

    private MessageParser messageParser;


    @Before
    public void setUp() throws Exception {
        messageParser = new DefaultMessageParser(requestFileInputContext,
                        new DefaultDataElementBitSetResolver(),
                        new DefaultDataElementMessageExtractor(new ArrayList<>()));

    }

    @Test
    public void parse() {

        List<Message> messages = messageParser.parse("request1.txt");

        for(Message message : messages ) {

            Assert.assertNotNull(message);
        }
    }

    @Test
    public void parse2() {

        try {
            List<Message> messages = messageParser.parse(null);
            Assert.assertNull(messages);
        } catch (Exception e ) {
            Assert.assertTrue(true);
        }
    }
}