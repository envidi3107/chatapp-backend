package com.group4.chatapp.services;

import com.group4.chatapp.dtos.TypingNotificationDto;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypingService implements DisposableBean {
  private final SimpMessagingTemplate messagingTemplate;
  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
  private final ConcurrentMap<String, ScheduledFuture<?>> expiryTasks = new ConcurrentHashMap<>();
  private static final long DEFAULT_TTL_MS = 5000L; // server-side TTL for typing indicator

  public void handleTyping(TypingNotificationDto dto) {
    long ttl = dto.getTtlMs() != null ? dto.getTtlMs() : DEFAULT_TTL_MS;
    String dest = "/queue/typing.room" + dto.getRoomId();

    dto.setTimestamp(System.currentTimeMillis());
    messagingTemplate.convertAndSend(dest, dto);

    String key = "room" + dto.getRoomId() + ":" + dto.getSenderId();

    ScheduledFuture<?> prev = expiryTasks.remove(key);
    if (prev != null) {
      prev.cancel(false);
    }

    if (dto.isTyping()) {
      ScheduledFuture<?> f =
          scheduler.schedule(
              () -> {
                TypingNotificationDto clear = new TypingNotificationDto();
                clear.setSenderId(dto.getSenderId());
                clear.setAvatar(dto.getAvatar());
                clear.setRoomId(dto.getRoomId());
                clear.setTyping(false);
                clear.setTimestamp(System.currentTimeMillis());
                messagingTemplate.convertAndSend(dest, clear);
                expiryTasks.remove(key);
              },
              ttl,
              TimeUnit.MILLISECONDS);
      expiryTasks.put(key, f);
    } else {
      messagingTemplate.convertAndSend(dest, dto);
    }
  }

  @Override
  public void destroy() {
    for (ScheduledFuture<?> f : expiryTasks.values()) {
      if (f != null && !f.isDone()) {
        f.cancel(false);
      }
    }
    expiryTasks.clear();
    scheduler.shutdownNow();
  }
}
