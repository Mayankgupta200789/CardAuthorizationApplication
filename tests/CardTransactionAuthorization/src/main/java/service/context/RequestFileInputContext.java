package service.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Request input file context
 */
@Configuration(value = "requestFileInputContext")
@PropertySource(value = "classpath:/context/requests.file.properties",ignoreResourceNotFound = true)
public class RequestFileInputContext {

    @Value("${request.input.file.home:/requests/}")
    private String inputHomePath;

    @Value("${request.input.messageTypeIndicator.start:0}")
    private Integer messageTypeIndicatorStartIndex;

    @Value("${request.input.messageTypeIndicator.end:4}")
    private Integer messageTypeIndicatorEndIndex;

    @Value("${request.input.bitmap.start:4}")
    private Integer bitMapStart;

    @Value("${request.input.bitmap.end:6}")
    private Integer bitMapEnd;

    @Value("${request.input.dataElement.start:6}")
    private Integer dataElementStart;

    public String getInputHomePath() {
        return inputHomePath;
    }

    public Integer getMessageTypeIndicatorStartIndex() {
        return messageTypeIndicatorStartIndex;
    }

    public Integer getMessageTypeIndicatorEndIndex() {
        return messageTypeIndicatorEndIndex;
    }

    public Integer getBitMapStart() {
        return bitMapStart;
    }

    public Integer getBitMapEnd() {
        return bitMapEnd;
    }

    public Integer getDataElementStart() {
        return dataElementStart;
    }
}
