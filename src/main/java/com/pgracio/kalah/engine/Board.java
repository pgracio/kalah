package com.pgracio.kalah.engine;

import com.pgracio.kalah.message.BoardMessage;
import com.pgracio.kalah.message.PlayMessage;
import com.pgracio.kalah.util.MessageUtil;

import java.util.Arrays;
import java.util.List;

public class Board {
  private final static int EMPTY_PIT = 0;
  private final static int FULL_PIT = 6;
  private final static int KALAH_PLAYER1 = 6;
  private final static int KALAH_PLAYER2 = 13;
  private List<Integer> pits = Arrays.asList(FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, EMPTY_PIT,
      FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, EMPTY_PIT);

  public BoardMessage play(PlayMessage message, int playerPosition) {
    int pit = message.getPit();
    int stones = pits.get(pit);

    int finalPit = moveStones(pit, stones, playerPosition);

    if (finalPit == KALAH_PLAYER1 || finalPit == KALAH_PLAYER2) {
      final Player nextMove = message.getPlayer();
      return new BoardMessage(pits, nextMove,
          MessageUtil.createEventMessage("Great move! " + nextMove.getName() + " play again."));
    } else {
      return new BoardMessage(pits, null, null);
    }
  }

  public int getStones(int pit) {
    return pits.get(pit);
  }

  private int moveStones(int pit, int stones, int playerPosition) {
    pits.set(pit, EMPTY_PIT);
    do {
      pit++;
      if(pit == pits.size()) {
        pit = EMPTY_PIT;
      }

      Integer current = pits.get(pit);
      current = current + 1;

      if (!(playerPosition == 1 && pit == KALAH_PLAYER2) && !(playerPosition == 2 && pit == KALAH_PLAYER1)) {
        pits.set(pit, current);
        stones--;
      }
    } while (stones > 0);

    return pit;
  }
}
