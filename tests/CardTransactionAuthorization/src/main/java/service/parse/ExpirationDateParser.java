package service.parse;

import dto.builder.DataElementBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/context/requests.file.properties",ignoreResourceNotFound = true)
public class ExpirationDateParser implements DataElementMessageParser {

    public static final int EXPIRATION_DATE_INDEX = 1;
    public static final char BIT_SET = '1';
    public static final int INCREMENT_INDEX = 4;
    @Value("${request.input.expiration.date.length:4}")
    private Integer expirationDateLength;
    @Override
    public Integer parse(String dataElement, String bitMap, DataElementBuilder dataElementBuilder, int nextIndex) {

        if(bitMap.charAt(EXPIRATION_DATE_INDEX) == BIT_SET) {
            String expirationDate = dataElement.substring(nextIndex, nextIndex + INCREMENT_INDEX);
            nextIndex += INCREMENT_INDEX;
            dataElementBuilder.withExpirationDate(expirationDate);
        }
        return nextIndex;
    }
}
