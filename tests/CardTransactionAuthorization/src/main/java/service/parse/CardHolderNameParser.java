package service.parse;

import dto.builder.DataElementBuilder;

public class CardHolderNameParser implements DataElementMessageParser {


    public static final int CARD_HOLDER_INDEX = 4;
    public static final char BIT_SET = '1';
    public static final int INCREMENT_INDEX = 2;

    @Override
    public Integer parse(String dataElement,
                         String bitMap,
                         DataElementBuilder dataElementBuilder,
                         int nextIndex) {

        if(bitMap.charAt(CARD_HOLDER_INDEX) == BIT_SET) {
            String nameLengthString = dataElement.substring(nextIndex, nextIndex + INCREMENT_INDEX);
            int nameLength = Integer.parseInt(nameLengthString);
            dataElementBuilder.withNameLength(nameLengthString);
            nextIndex += INCREMENT_INDEX;
            String name = dataElement.substring(nextIndex, nextIndex + nameLength);
            dataElementBuilder.withCardHolderName(name);
            nextIndex += nameLength;
        }
        return nextIndex;
    }
}
