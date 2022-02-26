package service.parse;

import dto.DataElement;
import dto.Message;
import dto.builder.MessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import service.context.RequestFileInputContext;
import service.extract.DataElementMessageExtractor;
import service.resolve.DataElementBitSetResolver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DefaultMessageParser implements MessageParser{


    private RequestFileInputContext requestFileInputContext;
    private DataElementBitSetResolver dataElementBitSetResolver;
    private DataElementMessageExtractor dataElementMessageExtractor;
    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageParser.class);

    @Autowired
    public DefaultMessageParser(@Qualifier(value = "requestFileInputContext")
                                        RequestFileInputContext requestFileInputContext,
                                @Qualifier(value = "dataElementBitSetResolver")
                                        DataElementBitSetResolver dataElementBitSetResolver,
                                @Qualifier(value = "dataElementMessageExtractor")
                                        DataElementMessageExtractor dataElementMessageExtractor) {

        this.requestFileInputContext = requestFileInputContext;
        this.dataElementBitSetResolver = dataElementBitSetResolver;
        this.dataElementMessageExtractor = dataElementMessageExtractor;
    }

    @Override
    public List<Message> parse(String fileName) {

        String inputFileHomePath = requestFileInputContext.getInputHomePath();
        List<Message> messages = new ArrayList<>();

        if(fileName == null || getClass().getResource( inputFileHomePath + fileName ) == null ) {
            logger.error("Input file name "  + fileName + " does not exist at location " + inputFileHomePath + "." + " Please provide valid input file" );
            return null;
        }

        try {
            InputStream inputStream = getClass().getResourceAsStream(inputFileHomePath + fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (bufferedReader.ready()) {
                MessageBuilder messageBuilder = new MessageBuilder();
                String messageInTheLine = bufferedReader.readLine();

                Integer messageTypeIndicatorEndIndex = requestFileInputContext.getMessageTypeIndicatorEndIndex();
                Integer messageTypeIndicatorStartIndex = requestFileInputContext.getMessageTypeIndicatorStartIndex();
                if (messageInTheLine.length() >= messageTypeIndicatorEndIndex) {
                    String messageTypeIndicator = messageInTheLine.substring(messageTypeIndicatorStartIndex,
                            messageTypeIndicatorEndIndex);
                    messageBuilder.withMessageTypeIndicator(messageTypeIndicator);

                }
                Integer bitMapStart = requestFileInputContext.getBitMapStart();
                Integer bitMapEnd = requestFileInputContext.getBitMapEnd();

                // 2nd input
                String hexValue = messageInTheLine.substring(bitMapStart, bitMapEnd);


                String binaryValue = dataElementBitSetResolver.resolve(hexValue);
                messageBuilder.withBinaryValue(binaryValue);

                Integer dataElementStart = requestFileInputContext.getDataElementStart();

                String dataElementMessage = messageInTheLine.substring(dataElementStart);

                DataElement dataElement = dataElementMessageExtractor.extract(dataElementMessage, binaryValue);
                messageBuilder.withDataElement(dataElement);

                messages.add(messageBuilder.build());

            }
            bufferedReader.close();

        } catch (NullPointerException e) {
            logger.error("Could not find input file due to " + e.getLocalizedMessage());
        } catch (IOException e) {
            logger.error("Could not read the input file " + fileName + " due to " + e.getLocalizedMessage());
        } finally {

        }


        return messages;
    }
}
