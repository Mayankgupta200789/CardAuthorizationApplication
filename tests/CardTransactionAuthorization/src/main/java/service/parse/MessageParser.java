package service.parse;

import dto.Message;

import java.util.List;

public interface MessageParser {

    List<Message> parse(String fileName);

}
