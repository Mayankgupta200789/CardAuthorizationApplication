package service.response.write;

import com.google.common.base.Preconditions;
import dto.DataElement;
import dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Configuration
@PropertySource(value = "classpath:service.response.file.properties", ignoreResourceNotFound = true)
public class DefaultMessageResponseWriter implements MessageResponseWriter {

    public static final String DOT = ".";
    public static final String LINE_FEED = "\n";
    public static final String ZEROS = "0";
    @Value("${service.response.file.path:.\\classes\\response\\}")
    private String responseFilePath;

    @Value("${service.response.file.name:response}")
    private String responseFileName;

    @Value("${service.response.file.name.extension:txt}")
    private String responseFileNameExtension;

    @Value("${service.response.file.transaction.amount.length:10}")
    private Integer transactionAmountLength;

    private static final Logger logger = LoggerFactory.getLogger(DefaultMessageResponseWriter.class);


    @Override
    public void write(List<Message> messages, int sequence)  {

        File responseDirectory = new File(responseFilePath);

        boolean isParentDirectoryCreated = responseDirectory.isDirectory();

        if(!isParentDirectoryCreated) {
            isParentDirectoryCreated = responseDirectory.mkdirs();
        }

        if(!isParentDirectoryCreated) {
            logger.error("the response home directory cannot be created");
        }

        File file = new File(responseFilePath + responseFileName + sequence +  DOT + responseFileNameExtension);
        boolean isFileCreated = false;
        if (!file.exists()) {
            try {
                isFileCreated = file.createNewFile();
            } catch (IOException e) {
                logger.error("Output file cannot be created due to " + e.getLocalizedMessage());
            }
        } else {
            isFileCreated = true;
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            if (isFileCreated) {

                if (messages != null) {

                    for (Message message : messages) {

                        try {

                            bufferedWriter.write(message.getMessageTypeIndicator());
                            bufferedWriter.write(message.getBinaryValue());
                            if (message.getDataElement() != null) {
                                DataElement dataElement = message.getDataElement();

                                if (dataElement != null) {
                                    if (dataElement.getPanCardLength() != null)
                                        bufferedWriter.write(dataElement.getPanCardLength());
                                    if (dataElement.getPanNumber() != null)
                                        bufferedWriter.write(dataElement.getPanNumber());
                                    if (dataElement.getExpirationDate() != null)
                                        bufferedWriter.write(dataElement.getExpirationDate());
                                    if (dataElement.getTransactionAmount() != null)
                                        bufferedWriter.write(
                                                ZEROS.repeat(transactionAmountLength
                                                        - dataElement.getTransactionAmount()
                                                        .toString().length())
                                                        + dataElement.getTransactionAmount().toString());
                                    if (dataElement.getResponseCode() != null)
                                        bufferedWriter.write(dataElement.getResponseCode().toString());
                                    if (dataElement.getNameLength() != null)
                                        bufferedWriter.write(dataElement.getNameLength());
                                    if (dataElement.getCardHolderName() != null)
                                        bufferedWriter.write(dataElement.getCardHolderName());
                                    if (dataElement.getZipCode() != null)
                                        bufferedWriter.write(dataElement.getZipCode());
                                }
                            }
                            bufferedWriter.write(LINE_FEED);
                        } catch (FileNotFoundException e) {
                            logger.error("File output stream cannot be written due to " + e.getLocalizedMessage());
                        } catch (IOException e) {
                            logger.error("IO Exception happened while converting fileoutput stream to object input stream due to "
                                    + e.getLocalizedMessage());
                        }
                    }
                }
            }
            bufferedWriter.close();
            logger.info("Successfully processed all the messages and written to " + responseFileName);

        } catch (Exception e) {
            logger.error("Buffered writer stream for writing response data failed due to " + e.getLocalizedMessage());
        }


    }
}
