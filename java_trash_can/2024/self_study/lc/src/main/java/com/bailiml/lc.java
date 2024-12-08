package com.bailiml;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class lc {

    // 24/11/21
    public int finalPositionOfSnake(int n, List<String> commands) {
        final int[] re = {0};
        commands.forEach(s -> {
            switch (s) {
                case "UP":
                    re[0] -= n;
                    break;
                case "DOWN":
                    re[0] += n;
                    break;
                case "LEFT":
                    re[0] -= 1;
                    break;
                default:
                    re[0] += 1;
                    break;
            }
        });
        return re[0];
    }

    @Test
    public void testFinalPositionOfSnake() {
        List<String> commands = new ArrayList<>();
        commands.add("RIGHT");
        commands.add("DOWN");
        System.out.println(finalPositionOfSnake(2, commands));
    }
}
