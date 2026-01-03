package com.group4.chatapp.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomDto {
  private String chatRoomName;
  private List<String> members;
}
