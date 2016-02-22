package com.pgracio.kalah.message;

import com.pgracio.kalah.engine.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BoardMessage {
  List<Integer> pits;
  private EventMessage message;
}
