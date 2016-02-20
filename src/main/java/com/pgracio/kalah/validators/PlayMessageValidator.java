package com.pgracio.kalah.validators;

import com.pgracio.kalah.engine.Player;
import com.pgracio.kalah.message.PlayMessage;
import lombok.AllArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@AllArgsConstructor
public class PlayMessageValidator implements Validator {
  private Player nextMove;
  private int stones;
  private int playerPosition;

  @Override
  public boolean supports(Class<?> clazz) {
    return PlayMessage.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    final PlayMessage playMessage = (PlayMessage) target;
    final String name = playMessage.getPlayer().getName();

    if (!name.equalsIgnoreCase(nextMove.getName())) {
      errors.rejectValue("player", "Player " + name + " please wait for your turn.");
    } else if((playMessage.getPit() > 6 && playerPosition == 1) ||
        (playMessage.getPit() < 7 && playerPosition == 2)) {
      errors.rejectValue("pit", "This pit doesn't belong to " + name + " select one of your pits.");
    }
    else if (stones == 0) {
      errors.rejectValue("pit", "This pit is empty. Select one with stones.");
    }
  }
}