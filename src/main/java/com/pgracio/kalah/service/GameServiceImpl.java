package com.pgracio.kalah.service;

import com.pgracio.kalah.engine.Board;
import com.pgracio.kalah.engine.GameEngine;
import com.pgracio.kalah.message.BoardMessage;
import com.pgracio.kalah.message.EventMessage;
import com.pgracio.kalah.engine.Player;
import com.pgracio.kalah.message.PlayMessage;
import com.pgracio.kalah.util.MessageUtil;
import com.pgracio.kalah.validators.PlayMessageValidator;
import com.pgracio.kalah.validators.PlayerValidator;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log
public class GameServiceImpl implements GameService {

  @Autowired
  private AsyncTaskExecutorService asyncTaskExecutor;

  @Autowired
  private SimpMessagingTemplate template;

  @Override
  public EventMessage addPlayer(Player player) throws InterruptedException {
    final GameEngine engine = GameEngine.getInstance();
    if (engine.addPlayer(player)) {
      if (engine.getPlayersNames().size() == 2) {
        asyncTaskExecutor.startGame();
      }
      return MessageUtil.createEventMessage("Welcome to the Game " + player.getName() + "!");
    } else {
      return MessageUtil.createEventMessage(player.getName() + " you have to wait for another round.");
    }
  }

  @Override
  public Errors validate(Player player) {
    return validate(player, GameEngine.getInstance().getPlayersNames());
  }

  @Override
  public boolean validate(PlayMessage playMessage) {
    final GameEngine engine = GameEngine.getInstance();
    final int stones = engine.getBoard().getStones(playMessage.getPit());
    final int playerPosition = engine.playerPosition(playMessage.getPlayer().getName());
    final Errors errors = validate(playMessage, engine.getNextMove(), stones, playerPosition);

    if(errors.getErrorCount() > 0) {
      template.convertAndSend("/topic/events", MessageUtil.createEventMessage(errors));
      return false;
    } else {
      return true;
    }
  }

  @Override
  public BoardMessage play(PlayMessage message) {
    final GameEngine engine = GameEngine.getInstance();
    return engine.play(message);
  }

  private Errors validate(Player player, List<String> otherNames) {
    Map<String, String> map = new HashMap<>();
    MapBindingResult errors = new MapBindingResult(map, Player.class.getName());

    PlayerValidator validator = new PlayerValidator(otherNames);
    validator.validate(player, errors);
    return errors;
  }

  private Errors validate(PlayMessage playMessage, Player nextMove, int stones, int playerPosition) {
    Map<String, String> map = new HashMap<>();
    MapBindingResult errors = new MapBindingResult(map, PlayMessage.class.getName());

    PlayMessageValidator validator = new PlayMessageValidator(nextMove, stones, playerPosition);
    validator.validate(playMessage, errors);
    return errors;
  }
}
