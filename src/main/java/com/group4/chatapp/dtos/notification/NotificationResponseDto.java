package com.group4.chatapp.dtos.notification;

import com.group4.chatapp.dtos.user.UserWithAvatarDto;
import com.group4.chatapp.models.Enum.NotificationType;
import com.group4.chatapp.models.Enum.TargetType;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseDto {
  private Long id;
  private String title;
  private String content;
  private UserWithAvatarDto sender;
  private UserWithAvatarDto receiver;
  private Timestamp sentOn;
  private Boolean isRead;
  private NotificationType type;
  private Long targetId;
  private TargetType targetType;
}
