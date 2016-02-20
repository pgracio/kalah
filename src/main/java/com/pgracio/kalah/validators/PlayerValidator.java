package com.pgracio.kalah.validators;

import com.pgracio.kalah.engine.Player;
import lombok.AllArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@AllArgsConstructor
public class PlayerValidator implements Validator {

  private final List<String> otherNames;

  @Override
  public boolean supports(Class<?> clazz) {
    return Player.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    Player player = (Player) target;

    if (player.getName().isEmpty()) {
      errors.rejectValue("name", "You need to insert your name.");
    } else if (otherNames.contains(player.getName())) {
      errors.rejectValue("name", "This is already a player with the same name.");
    }
  }
}
