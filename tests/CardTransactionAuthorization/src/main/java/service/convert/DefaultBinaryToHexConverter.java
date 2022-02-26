package service.convert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component(value = "binaryToHexConverter")
@Configuration
@PropertySource(value = "classpath:/authorize/requests.authorize.properties",ignoreResourceNotFound = true)
public class DefaultBinaryToHexConverter implements BinaryToHexConverter {

    public static final int GROUP = 4;
    public static final int INSERT_AT_TOP = 0;
    public static final int RESPONSE_INDEX = 3;
    @Value("${request.input.messageTypeIndicator.binary.length:8}")
    private Integer messageTypeIndicatorLength;

    private static final Map<String, Character> binaryToHexMap
            = new HashMap<>();
    static {
        binaryToHexMap.put("0000",'0');
        binaryToHexMap.put("0001",'1');
        binaryToHexMap.put("0010",'2');
        binaryToHexMap.put("0011",'3');
        binaryToHexMap.put("0100",'4');
        binaryToHexMap.put("0101",'5');
        binaryToHexMap.put("0110",'6');
        binaryToHexMap.put("0111",'7');
        binaryToHexMap.put("1000",'8');
        binaryToHexMap.put("1001",'9');
        binaryToHexMap.put("1010",'a');
        binaryToHexMap.put("1011",'b');
        binaryToHexMap.put("1100",'c');
        binaryToHexMap.put("1101",'d');
        binaryToHexMap.put("1110",'e');
        binaryToHexMap.put("1111",'f');

    }


    @Override
    public String convert(String binary) {

        StringBuilder hex = new StringBuilder();

        if( binary != null && !binary.isBlank() && binary.length() == messageTypeIndicatorLength) {
            StringBuilder binaryBuilder = new StringBuilder(binary);
            int i =binary.length();
            binaryBuilder.setCharAt(RESPONSE_INDEX,'1');
            binary = binaryBuilder.toString();
            while(i >= GROUP) {
                // Take four characters to resolve hex value
                String hexInput = binary.substring(i - GROUP, i);
                Character hexValue = binaryToHexMap.get(hexInput);
                hex.insert(INSERT_AT_TOP,hexValue);
                i -= GROUP;
            }
        }
        return hex.toString();
    }
}
