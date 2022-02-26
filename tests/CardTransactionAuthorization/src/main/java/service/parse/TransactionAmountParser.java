package service.parse;

import dto.builder.DataElementBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:/context/requests.file.properties",ignoreResourceNotFound = true)
public class TransactionAmountParser implements DataElementMessageParser {

    public static final int TRANSACTION_AMOUNT_INDEX = 2;
    public static final char BIT_SET = '1';
    @Value("${request.input.transaction.amount.length:10}")
    private Integer transactionAmountLength;
    @Override
    public Integer parse(String dataElement, String bitMap, DataElementBuilder dataElementBuilder, int nextIndex) {

        if(bitMap.charAt(TRANSACTION_AMOUNT_INDEX) == BIT_SET) {
            String transactionAmountString = dataElement.substring(nextIndex, nextIndex + 10);
            nextIndex += 10;
            long transactionAmount = Long.parseLong(transactionAmountString);
            dataElementBuilder.withTransactionAmount(transactionAmount);
        }
        return nextIndex;
    }
}
