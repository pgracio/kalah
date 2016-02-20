package com.pgracio.kalah.service;

import com.pgracio.kalah.engine.GameEngine;
import com.pgracio.kalah.engine.Player;
import com.pgracio.kalah.util.MessageUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Log
@Service
@Async
public class AsyncTaskExecutorServiceImpl implements AsyncTaskExecutorService {

  @Autowired
  private SimpMessagingTemplate template;

  public void startGame() throws InterruptedException {
    log.info("Starting game...");
    Thread.sleep(300); // simulated delay
    final GameEngine engine = GameEngine.getInstance();
    final Player player = engine.start();
    template.convertAndSend("/topic/events",
        MessageUtil.createEventMessage("Starting Game. " + player.getName() + " you start..."));
  }
}
