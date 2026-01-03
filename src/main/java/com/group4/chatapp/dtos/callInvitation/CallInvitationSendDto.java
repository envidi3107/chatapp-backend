package com.group4.chatapp.dtos.callInvitation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CallInvitationSendDto {
  private Long channelId;
  private Boolean isUseVideo;
}
