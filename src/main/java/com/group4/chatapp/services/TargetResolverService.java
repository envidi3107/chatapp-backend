package com.group4.chatapp.services;

import com.group4.chatapp.models.Attachment;
import com.group4.chatapp.models.Enum.TargetType;
import com.group4.chatapp.models.Post;
import com.group4.chatapp.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TargetResolverService {
  private final PostService postService;
  private final AttachmentService attachmentService;

  public User getAuthor(Long targetId, TargetType targetType) {
    User targetAuthor = null;
    if (targetType.equals(TargetType.POST)) {
      System.out.println("post id: " + targetId);
      Post post = postService.getPost(targetId);
      targetAuthor = post.getUser();
    } else if (targetType.equals(TargetType.ATTACHMENT)) {
      Attachment attachment = attachmentService.getAttachment(targetId);
      targetAuthor = attachment.getPost().getUser();
    }
    return targetAuthor;
  }
}
