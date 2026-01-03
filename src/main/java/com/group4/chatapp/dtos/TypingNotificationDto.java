package com.group4.chatapp.dtos;

import lombok.Data;

@Data
public class TypingNotificationDto {
  private long senderId;
  private String avatar;
  private long roomId;
  private boolean typing;
  private long timestamp;
  private Integer ttlMs;
}
