package com.group4.chatapp.dtos.comment;

import com.group4.chatapp.dtos.user.UserWithAvatarDto;
import com.group4.chatapp.models.Comment;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
  private Long id;
  private String content;
  private UserWithAvatarDto user;
  private Timestamp commentedAt;

  public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.content = comment.getContent();
    this.user = new UserWithAvatarDto(comment.getUser());
    this.commentedAt = comment.getCommentedAt();
  }
}
