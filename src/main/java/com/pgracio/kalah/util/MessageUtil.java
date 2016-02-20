package com.pgracio.kalah.util;

import com.pgracio.kalah.message.BoardMessage;
import com.pgracio.kalah.message.EventMessage;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class MessageUtil {
  public static EventMessage createEventMessage(String message) {
    return new EventMessage("[" + LocalDateTime.now().format(DateTimeFormatter.ISO_TIME)
        + "] " + message);
  }

  public static EventMessage createEventMessage(Errors errors) {
    String message =
        errors.getAllErrors()
            .stream()
            .map(ObjectError::getCode)
            .collect(Collectors.joining("\\n"));
    return createEventMessage(message);
  }

  public static BoardMessage createBoardMessage(BoardMessage boardMessage) {
    return boardMessage;
  }
}
