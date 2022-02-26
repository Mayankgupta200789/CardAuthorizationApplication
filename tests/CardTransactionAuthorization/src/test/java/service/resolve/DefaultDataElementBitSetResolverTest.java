package service.resolve;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultDataElementBitSetResolverTest {

    private DataElementBitSetResolver dataElementBitSetResolver;

    @Before
    public void setUp() throws Exception {
        dataElementBitSetResolver = new DefaultDataElementBitSetResolver();
    }

    @Test
    public void resolve() {

        Assert.assertEquals("11001000",dataElementBitSetResolver.resolve("c8"));
        Assert.assertEquals("10100100",dataElementBitSetResolver.resolve("a4"));
    }

    @Test
    public void resolveWithCharacterNotFound() {

        Assert.assertEquals("",dataElementBitSetResolver.resolve("z8"));
    }
}