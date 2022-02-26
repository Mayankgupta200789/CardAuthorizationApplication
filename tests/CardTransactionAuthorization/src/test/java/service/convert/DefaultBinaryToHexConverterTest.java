package service.convert;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.parse.PANCardParser;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =
        DefaultBinaryToHexConverter.class)
@TestPropertySource(locations = "/authorize/requests.authorize.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DefaultBinaryToHexConverterTest {

    @Autowired
    private BinaryToHexConverter binaryToHexConverter;


    @Test
    public void convert() {
        Assert.assertEquals("f1",binaryToHexConverter.convert("11100001"));
        Assert.assertEquals("10",binaryToHexConverter.convert("00000000"));
    }

    @Test
    public void convertDoesNotThrowNullPointerException() {
        try {
            binaryToHexConverter.convert(null);
            binaryToHexConverter.convert("");
            Assert.assertTrue(true);
        } catch (Exception e ) {
            fail("Exception thrown due to " + e.getLocalizedMessage());
        }
    }
}