package com.group4.chatapp.controllers;

import com.group4.chatapp.dtos.TypingNotificationDto;
import com.group4.chatapp.services.TypingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TypingController {
  private final TypingService typingService;

  @PostMapping("/typing")
  public void typing(@RequestBody TypingNotificationDto dto) {
    if (dto == null) return;
    typingService.handleTyping(dto);
  }
}
