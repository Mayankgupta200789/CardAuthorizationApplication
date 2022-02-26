package service.extract;

import dto.DataElement;
import dto.builder.DataElementBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import service.parse.DataElementMessageParser;

import java.util.List;

public class DefaultDataElementMessageExtractor implements DataElementMessageExtractor {


    private List<DataElementMessageParser> messageParsers;

    @Autowired
    public DefaultDataElementMessageExtractor(List<DataElementMessageParser> messageParsers) {

        this.messageParsers = messageParsers;
    }

    @Override
    public DataElement extract(String inputDataElement, String binaryBitMap) {

        DataElementBuilder dataElementBuilder = new DataElementBuilder();

        if(binaryBitMap == null || inputDataElement == null ) {
            return null;
        }

        int index = 0;

        for(DataElementMessageParser messageParser : messageParsers ){
            index = messageParser.parse(inputDataElement, binaryBitMap, dataElementBuilder,index );
        }
        return dataElementBuilder.build();
    }
}
