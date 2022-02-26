package service.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.authorize.CreditCardAuthorizer;
import service.authorize.DefaultCreditCardAuthorizer;
import service.authorize.DefaultMessageAuthorizer;
import service.authorize.MessageAuthorizer;
import service.context.RequestFileInputContext;
import service.convert.BinaryToHexConverter;
import service.convert.DefaultBinaryToHexConverter;
import service.extract.DataElementMessageExtractor;
import service.extract.DefaultDataElementMessageExtractor;
import service.parse.*;
import service.resolve.DataElementBitSetResolver;
import service.resolve.DefaultDataElementBitSetResolver;
import service.response.write.DefaultMessageResponseWriter;
import service.response.write.MessageResponseWriter;

import java.util.ArrayList;
import java.util.List;


/**
 * This class contains all the bean defined
 * in spring context
 */
@Configuration
public class ServiceConfiguration {

    @Bean(name = "messageParser")
    public MessageParser messageParser(
            @Qualifier(value = "requestFileInputContext") RequestFileInputContext requestFileInputContext,
            @Qualifier(value = "dataElementBitSetResolver")
                    DataElementBitSetResolver dataElementBitSetResolver,
            @Qualifier(value = "dataElementMessageExtractor")
                    DataElementMessageExtractor dataElementMessageExtractor) {

        return new DefaultMessageParser(requestFileInputContext,
                dataElementBitSetResolver,
                dataElementMessageExtractor);
    }

    @Bean(name = "dataElementBitSetResolver")
    public DataElementBitSetResolver dataElementBitSetResolver() {
        return new DefaultDataElementBitSetResolver();
    }

    @Bean(name = "dataElementMessageExtractor")
    public DataElementMessageExtractor dataElementMessageExtractor(

            @Qualifier(value = "panCardHolderParser")  DataElementMessageParser panCardHolderParser,
            @Qualifier(value = "expirationDateParser") DataElementMessageParser expirationDateParser,
            @Qualifier(value = "responseCodeParser") DataElementMessageParser responseCodeParser,
            @Qualifier(value = "transactionAmountParser") DataElementMessageParser transactionAmountParser,
            @Qualifier(value = "customCardHolderNameParser") DataElementMessageParser customCardHolderNameParser,
            @Qualifier(value = "zipCodeParser") DataElementMessageParser zipCodeParser

    ) {

        List<DataElementMessageParser>  messageParsers = new ArrayList<>();
        messageParsers.add(panCardHolderParser);
        messageParsers.add(expirationDateParser);
        messageParsers.add(transactionAmountParser);
        messageParsers.add(responseCodeParser);
        messageParsers.add(customCardHolderNameParser);
        messageParsers.add(zipCodeParser);
        return new DefaultDataElementMessageExtractor(messageParsers);
    }

    @Bean(name = "panCardHolderParser")
    public DataElementMessageParser panCardHolderParser() {

        return new PANCardParser();
    }

    @Bean(name = "expirationDateParser")
    public DataElementMessageParser expirationDateParser() {

        return new ExpirationDateParser();
    }

    @Bean(name = "responseCodeParser")
    public DataElementMessageParser responseCodeParser() {

        return new ResponseCodeParser();
    }

    @Bean(name = "transactionAmountParser")
    public DataElementMessageParser transactionAmountParser() {

        return new TransactionAmountParser();
    }

    @Bean(name = "zipCodeParser")
    public DataElementMessageParser zipCodeParser() {

        return new ZipCodeHolderParser();
    }

    @Bean(name = "customCardHolderNameParser")
    public DataElementMessageParser customCardHolderNameParser() {

        return new CardHolderNameParser();
    }

    @Bean(name = "binaryToHexConverter")
    public BinaryToHexConverter binaryToHexConverter() {
        return new DefaultBinaryToHexConverter();
    }

    @Bean(name = "messageAuthorizer")
    public MessageAuthorizer messageAuthorizer(@Qualifier(value = "binaryToHexConverter")
                                                BinaryToHexConverter binaryToHexConverter) {
        return new DefaultMessageAuthorizer(binaryToHexConverter);
    }

    @Bean(name = "creditCardAuthorizer")
    public CreditCardAuthorizer creditCardAuthorizer(@Qualifier(value = "messageAuthorizer")
                                                                 MessageAuthorizer messageAuthorizer) {
        return new DefaultCreditCardAuthorizer(messageAuthorizer);
    }

    @Bean(name = "requestFileInputContext")
    public RequestFileInputContext requestFileInputContext() {
        return new RequestFileInputContext();
    }


    @Bean(name = "messageResponseWriter")
    public MessageResponseWriter messageResponseWriter() {
        return new DefaultMessageResponseWriter();
    }
}
