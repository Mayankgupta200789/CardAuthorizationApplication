package service.resolve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DefaultDataElementBitSetResolver implements DataElementBitSetResolver {

    private static final Logger logger = LoggerFactory.getLogger(DefaultDataElementBitSetResolver.class);

    // initializing the HashMap class
    //TODO: This needs to be extracted in a property
    private static final Map<Character, String> hexToBinaryMap
            = new HashMap<>();

    // storing the key value pairs
    static {
        hexToBinaryMap.put('0', "0000");
        hexToBinaryMap.put('1', "0001");
        hexToBinaryMap.put('2', "0010");
        hexToBinaryMap.put('3', "0011");
        hexToBinaryMap.put('4', "0100");
        hexToBinaryMap.put('5', "0101");
        hexToBinaryMap.put('6', "0110");
        hexToBinaryMap.put('7', "0111");
        hexToBinaryMap.put('8', "1000");
        hexToBinaryMap.put('9', "1001");
        hexToBinaryMap.put('A', "1010");
        hexToBinaryMap.put('a', "1010");
        hexToBinaryMap.put('B', "1011");
        hexToBinaryMap.put('b', "1011");
        hexToBinaryMap.put('C', "1100");
        hexToBinaryMap.put('c', "1100");
        hexToBinaryMap.put('D', "1101");
        hexToBinaryMap.put('d', "1101");
        hexToBinaryMap.put('E', "1110");
        hexToBinaryMap.put('e', "1110");
        hexToBinaryMap.put('F', "1111");
        hexToBinaryMap.put('f', "1111");
    }


    @Override
    public String resolve(String bitMap) {

        StringBuilder binary = new StringBuilder();
        char ch;
        for(int i = 0  ; i < bitMap.length(); i++ ) {

            ch = bitMap.charAt(i);

            if(hexToBinaryMap.containsKey(ch)) {
                binary.append(hexToBinaryMap.get(ch));
            } else {
                logger.error("Input value inserted is not a valid hex character");
                binary.setLength(0);
                break;
            }
        }

        return binary.toString();
    }
}
