package com.group4.chatapp.dtos.callInvitation;

import com.group4.chatapp.dtos.user.UserWithAvatarDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CallInvitationResponseDto {
  private Long channelId;
  private UserWithAvatarDto caller;
  private List<UserWithAvatarDto> members;
  private Boolean isUseVideo;
}
