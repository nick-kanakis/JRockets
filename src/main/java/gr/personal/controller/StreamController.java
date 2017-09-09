package gr.personal.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * Created by Nick Kanakis on 9/9/2017.
 */
@Controller
public class StreamController {

    @MessageMapping("/commentStream")
    @SendTo("/topic/comments")
    public String comments(){
        return "New comments...";
    }

    @MessageMapping("/postStream")
    @SendTo("/topic/posts")
    public String posts(){
        return "New posts...";
    }
}

