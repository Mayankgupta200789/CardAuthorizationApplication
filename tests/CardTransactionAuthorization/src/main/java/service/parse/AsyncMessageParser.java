package service.parse;


import dto.DataElement;
import dto.Message;
import dto.builder.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import service.context.RequestFileInputContext;
import service.extract.DataElementMessageExtractor;
import service.resolve.DataElementBitSetResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class AsyncMessageParser {

    private MessageParser messageParser;

    @Autowired
   public AsyncMessageParser(MessageParser messageParser) {

        this.messageParser = messageParser;
    }

    @Async
    public CompletableFuture<List<Message>> parse(String fileName) {
        List<Message> messages = messageParser.parse(fileName);
        return CompletableFuture.completedFuture(messages);

    }
}
