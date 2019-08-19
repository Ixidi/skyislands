package me.ixidi.skyisland.util.error;

import lombok.Getter;

public class SkyIslandException extends RuntimeException {

    @Getter
    private String solution;

    public SkyIslandException(String message, String solution) {
        super(message);
        this.solution = solution;
    }

}
