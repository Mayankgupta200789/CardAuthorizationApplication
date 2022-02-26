package service.parse;

import dto.builder.DataElementBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/context/requests.file.properties",ignoreResourceNotFound = true)
public class ZipCodeHolderParser implements DataElementMessageParser {

    public static final int ZIP_CODE_INDEX = 5;
    public static final char BIT_SET = '1';
    @Value("${request.input.zip.code.length:5}")
    private Integer zipCodeLength;


    @Override
    public Integer parse(String dataElement, String bitMap, DataElementBuilder dataElementBuilder, int nextIndex) {
        if(bitMap.charAt(ZIP_CODE_INDEX) == BIT_SET) {
            String zipCode = dataElement.substring(nextIndex, nextIndex + 5);
            dataElementBuilder.withZipCode(zipCode);
            nextIndex += 5;
        }
        return nextIndex;
    }
}
