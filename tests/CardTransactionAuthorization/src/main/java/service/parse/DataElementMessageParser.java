package service.parse;

import dto.builder.DataElementBuilder;

/**
 * This interface is responsible to
 * go through different sets of data element
 * parser and create a result and pass it to next
 * parser through chain of responsibility
 *
 */
public interface DataElementMessageParser {


    /**
     * This method is responsible to parse the data
     * element and create an output index.
     * This will used eventually to compute next data
     * elements
     * @param dataElement
     * @param bitMap
     * @param dataElementBuilder
     * @param nextIndex
     * @return
     */
    Integer parse(String dataElement, String bitMap, DataElementBuilder dataElementBuilder, int nextIndex);
}
