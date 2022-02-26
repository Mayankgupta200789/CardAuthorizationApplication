package service.convert;

/**
 * This interface is responsible to
 * convert binary to hex value
 *
 */
public interface BinaryToHexConverter {

    /**
     * This interface is responsible to convert
     * binary numbers to hexa decimal numbers
     * @param binary
     * @return hexadecimal string
     */
    String convert(String binary);
}
