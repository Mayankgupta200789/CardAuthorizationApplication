package service.parse;

import dto.builder.DataElementBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import service.authorize.MessageStatus;

@Configuration
@PropertySource(value = "classpath:/context/requests.file.properties",ignoreResourceNotFound = true)
public class ResponseCodeParser implements DataElementMessageParser {

    public static final int RESPONSE_CODE_INDEX = 3;
    public static final char BIT_SET = '1';
    @Value("${request.input.response.code.length:2}")
    private Integer responseCodeLength;

    @Override
    public Integer parse(String dataElement,
                         String bitMap,
                         DataElementBuilder dataElementBuilder,
                         int nextIndex) {

        if(bitMap.charAt(RESPONSE_CODE_INDEX) == BIT_SET) {
            String responseCode = dataElement.substring(nextIndex, nextIndex + 2);
            MessageStatus messageStatus = null;
            if(responseCode.equalsIgnoreCase(MessageStatus.OK.toString())) {
                messageStatus = MessageStatus.OK;
            } else if(responseCode.equalsIgnoreCase(MessageStatus.DE.toString())) {
                messageStatus = MessageStatus.DE;
            } else if(responseCode.equalsIgnoreCase(MessageStatus.ER.toString())) {
                messageStatus = MessageStatus.ER;
            }
            dataElementBuilder.withResponseCode(messageStatus);
            nextIndex += 2;
        }
        return nextIndex;
    }
}
