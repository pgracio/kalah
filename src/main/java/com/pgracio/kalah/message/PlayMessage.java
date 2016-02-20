package com.pgracio.kalah.message;

import com.pgracio.kalah.engine.Player;
import lombok.Data;

@Data
public class PlayMessage {
  private Player player;
  private Integer pit;
}
