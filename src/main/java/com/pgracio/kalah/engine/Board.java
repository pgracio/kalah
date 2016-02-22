package com.pgracio.kalah.engine;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Board {
  private final static int EMPTY_PIT = 0;
  private final static int FULL_PIT = 6;
  private final static int KALAH_PLAYER1 = 6;
  private final static int KALAH_PLAYER2 = 13;
  private final static int PLAYER1 = 1;
  private final static int PLAYER2 = 2;
  private List<Integer> pits = Arrays.asList(FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, EMPTY_PIT,
      FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, FULL_PIT, EMPTY_PIT);

  public Next play(int pit, int playerPosition) {
    int stones = pits.get(pit);

    int finalPit = moveStones(pit, stones, playerPosition);

    if ((finalPit == KALAH_PLAYER1 || finalPit == KALAH_PLAYER2) && moreMoves()) {
      return Next.SAME_PLAYER;
    } else if (moreMoves()) {
      return Next.SWITCH_PLAYER;
    } else {
      finish();
      return Next.GAME_OVER;
    }
  }

  public String getResult() {
    return pits.get(KALAH_PLAYER1) + " - " + pits.get(KALAH_PLAYER2);
  }

  public int getStones(Integer pit) {
    return pits.get(pit);
  }

  private int moveStones(int pit, int stones, int player) {
    pits.set(pit, EMPTY_PIT);
    do {
      pit++;
      if (pit == pits.size()) {
        pit = EMPTY_PIT;
      }

      Integer current = pits.get(pit);
      current = current + 1;

      // No stones are put in the opponent's Kalah.
      if (!(player == PLAYER1 && pit == KALAH_PLAYER2) && !(player == PLAYER2 && pit == KALAH_PLAYER1)) {
        pits.set(pit, current);
        stones--;
      }
    } while (stones > 0);

    ownEmptyPit(pit, player);
    return pit;
  }

  private void ownEmptyPit(int pit, int player) {
    // last stone landed in an own empty pit
    if (player == PLAYER1 && pit < KALAH_PLAYER1 && pits.get(pit) == 1) {
      int oppositePit = 12 - pit;
      moveAllToKalah(pit, KALAH_PLAYER1);
      moveAllToKalah(oppositePit, KALAH_PLAYER1);

    } else if (player == PLAYER2 && pit > KALAH_PLAYER1 && pit < KALAH_PLAYER2 && pits.get(pit) == 1) {
      int oppositePit = -(pit - 12);
      moveAllToKalah(pit, KALAH_PLAYER2);
      moveAllToKalah(oppositePit, KALAH_PLAYER2);
    }
  }

  private void moveAllToKalah(int pit, int kalah) {
    int stones = pits.get(pit);
    int stonesKalah = pits.get(kalah);

    stonesKalah = stonesKalah + stones;

    pits.set(pit, EMPTY_PIT);
    pits.set(kalah, stonesKalah);
  }

  private boolean moreMoves() {
    return !((pits.get(0) + pits.get(1) + pits.get(2) + pits.get(3) + pits.get(4) + pits.get(5)) == 0
        || (pits.get(7) + pits.get(8) + pits.get(9) + pits.get(10) + pits.get(11) + pits.get(12)) == 0);
  }

  private void finish() {
    int stonesP1 = 0;
    int stonesP2 = 0;

    for (int i = 0; i < KALAH_PLAYER1; i++) {
      stonesP1 = stonesP1 + pits.get(i);
      pits.set(i, 0);
    }

    for (int i = KALAH_PLAYER1 + 1; i < KALAH_PLAYER2; i++) {
      stonesP2 = stonesP2 + pits.get(i);
      pits.set(i, 0);
    }
  }
}
