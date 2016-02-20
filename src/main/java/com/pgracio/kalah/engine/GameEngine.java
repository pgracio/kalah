package com.pgracio.kalah.engine;

import com.pgracio.kalah.message.BoardMessage;
import com.pgracio.kalah.message.PlayMessage;
import com.pgracio.kalah.util.MessageUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class GameEngine {

  private static GameEngine ourInstance = new GameEngine();

  public static GameEngine getInstance() {
    return ourInstance;
  }

  private Player player1;
  private Player player2;

  private Player nextMove;
  private Board board;

  private GameEngine() {
    board = new Board();
  }

  public boolean addPlayer(Player player) {
    boolean added = false;

    if (player1 == null) {
      player.setId(1);
      player1 = player;
      added = true;
    } else if (player2 == null) {
      player.setId(2);
      player2 = player;
      added = true;
    }
    return added;
  }

  public Player start() {
    final int random = (int) (Math.random() + 0.5);
    return switchPlayer(random);
  }

  public BoardMessage play(PlayMessage message) {
    final BoardMessage boardMessage = board.play(message, playerPosition(message.getPlayer().getName()));

    if (boardMessage.getNextMove() == null) {
      switchToOtherPlayer(message.getPlayer().getName());
      boardMessage.setNextMove(nextMove);
      boardMessage.setMessage(MessageUtil.createEventMessage(nextMove.getName()
          + " it's your turn now."));
    }
    return boardMessage;
  }

  public List<String> getPlayersNames() {
    List<String> playerNames = new ArrayList<>();

    if (player1 != null && player1.getName() != null)
      playerNames.add(player1.getName());
    if (player2 != null && player2.getName() != null)
      playerNames.add(player2.getName());
    return playerNames;
  }

  public int playerPosition(String name) {
    if (Objects.equals(name, player1.getName()))
      return 1;
    else
      return 2;
  }

  private Player switchToOtherPlayer(String currentName) {
    if (Objects.equals(currentName, player1.getName()))
      nextMove = player2;
    else
      nextMove = player1;
    return nextMove;
  }

  private Player switchPlayer(int id) {
    if (id == 1)
      nextMove = player1;
    else
      nextMove = player2;
    return nextMove;
  }
}
