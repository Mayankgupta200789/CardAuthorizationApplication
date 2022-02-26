package service.extract;

import dto.DataElement;

/**
 * This interface is responsible to extract
 * and accumulate all the data element parsers
 * and create a single data element
 */
public interface DataElementMessageExtractor {

    /**
     * This method is responsible to create data
     * element after going through all the parsers.
     * @param dataElement
     * @param bitMap
     * @return
     */
    DataElement extract(String dataElement, String bitMap);
}
