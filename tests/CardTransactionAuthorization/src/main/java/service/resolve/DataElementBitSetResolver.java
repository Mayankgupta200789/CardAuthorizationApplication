package service.resolve;


/**
 * This interface is responsible to resolve
 * hexa decimal to binary
 */
public interface DataElementBitSetResolver {

    /**
     * This method converts bitMap in hexadecimal
     * to binary value
     * @param bitMap
     * @return
     */
    String resolve(String bitMap);
}
