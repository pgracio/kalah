package com.pgracio.kalah.message;

import com.pgracio.kalah.engine.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BoardMessage {
  List<Integer> pits;
  private Player nextMove;
  private EventMessage message;
}
