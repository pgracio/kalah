package com.pgracio.kalah.controller;

import com.pgracio.kalah.message.EventMessage;
import com.pgracio.kalah.engine.Player;
import com.pgracio.kalah.message.BoardMessage;
import com.pgracio.kalah.message.PlayMessage;
import com.pgracio.kalah.service.GameService;
import com.pgracio.kalah.util.MessageUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;

@Controller
@Log
public class GameController {

  @Autowired
  private GameService gameService;

  @MessageMapping("/join")
  @SendTo("/topic/events")
  public EventMessage join(Player player) throws InterruptedException {
    final Errors errors = gameService.validate(player);
    if(errors.getErrorCount() == 0) {
      return  gameService.addPlayer(player);
    } else {
      return MessageUtil.createEventMessage(errors);
    }
  }

  @MessageMapping("/play")
  @SendTo("/topic/board")
  public BoardMessage play(PlayMessage message) {
    final boolean valid = gameService.validate(message);
    if(valid) {
      return MessageUtil.createBoardMessage(gameService.play(message));
    } else{
      return null;
    }
  }
}
