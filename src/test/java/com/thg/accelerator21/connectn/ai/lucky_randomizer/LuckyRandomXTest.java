package com.thg.accelerator21.connectn.ai.lucky_randomizer;
import com.thg.accelerator23.connectn.ai.lucky_randomizer.LuckyRandomX.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LuckyRandomXTest {

  @DisplayName("Random number")
  @Test()
  public void isGeneratingRandomNumber() {
    int generateRandomNumber = new Random().nextInt(1, 10);
    System.out.println(generateRandomNumber);
    assertTrue(generateRandomNumber <= 10);
  }
}
