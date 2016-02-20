package com.pgracio.kalah.service;

import com.pgracio.kalah.message.BoardMessage;
import com.pgracio.kalah.message.EventMessage;
import com.pgracio.kalah.engine.Player;
import com.pgracio.kalah.message.PlayMessage;
import org.springframework.validation.Errors;

public interface GameService {
  EventMessage addPlayer(Player player) throws InterruptedException;

  Errors validate(Player player);

  boolean validate(PlayMessage playMessage);

  BoardMessage play(PlayMessage message);
}
