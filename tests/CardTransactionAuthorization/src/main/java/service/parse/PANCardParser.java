package service.parse;

import dto.builder.DataElementBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/context/requests.file.properties",ignoreResourceNotFound = true)
public class PANCardParser implements DataElementMessageParser {

    public static final int PAN_CARD_INDEX = 0;
    public static final char BIT_SET = '1';
    @Value("${request.input.pan.length.flag:2}")
    private Integer panCardLength;


    @Override
    public Integer parse(String dataElement,
                         String bitMap,
                         DataElementBuilder dataElementBuilder,
                         int nextIndex) {

        if(bitMap.charAt(PAN_CARD_INDEX) == BIT_SET) {
            String panCardValueString = dataElement.substring(0, panCardLength);
            int panCardValue = Integer.parseInt(panCardValueString);
            nextIndex += 2 + panCardValue;
            dataElementBuilder.withPanCardLength(panCardValueString);
            String panNumber = dataElement.substring(2, nextIndex);
            dataElementBuilder.withPanNumber(panNumber);
        }
        return nextIndex;
    }
}
