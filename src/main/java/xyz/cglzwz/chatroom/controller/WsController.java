package xyz.cglzwz.chatroom.controller;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.cglzwz.chatroom.domain.Message;
import xyz.cglzwz.chatroom.domain.SysUser;
import xyz.cglzwz.chatroom.service.RegisterService;

import java.security.Principal;

@Controller
public class WsController {
    private static final Log log = LogFactory.getLog(WsController.class);

    @Autowired
    private RegisterService registerService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping(value = "/chat")
    public void handleChat(Principal principal, Message message) {
        log.info("The server is fording message--------------------");
        log.info("Massage Sender: " + principal.getName());
        log.info("Massage Recipient: " + message.getReceiver());
        log.info("The content of message: " + message.getText());
        if (message.getReceiver().isEmpty()) {
            log.info("Don't fill in the receiver, this is a broadcast");
            simpMessagingTemplate.convertAndSendToUser("", "/notification",
                    principal.getName() + ": " + message.getText());
            System.out.println(message.getText());
        } else {
            simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/notification",
                    principal.getName() + ": " + message.getText());
            System.out.println("m 2");
        }
        log.info("The server completes the forwarding service-------------------------");
    }

    @RequestMapping(value = "/index")
    public String toChat() {
        log.info("Jump to the chat interface");
        return "index";
    }

    @GetMapping(value = "/login")
    public String toLogin() {
        log.info("\n" +
                "Jump to login page");
        return "login";
    }

    @Deprecated
    @PostMapping("/user/login")
    @ResponseBody
    public String userLogin(SysUser sysUser) {
        log.info("\n" +
                "The front-end username and password received by the registration username:" + sysUser.getUsername() + ", password: " + sysUser.getPassword());
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/user/register")
    @ResponseBody
    public boolean UserRegister(SysUser sysUser) {
        log.info("\n" +
                "The front-end username and password received by the registration username:" + sysUser.getUsername() + ", password: " + sysUser.getPassword());
        return registerService.userRegister(sysUser);
    }

}