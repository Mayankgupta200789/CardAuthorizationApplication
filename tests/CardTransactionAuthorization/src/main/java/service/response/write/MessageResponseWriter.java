package service.response.write;

import dto.Message;

import java.io.IOException;
import java.util.List;

/**
 * This interface is responsible to write
 * messages in output file.
 *
 */
public interface MessageResponseWriter {

    void write(List<Message> messages,int sequence);
}
