package io.github.seanwu1105.umldiagrameditor.canvas.mode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModeIdTest {

    @Test
    void toStringShouldHaveLowerCase() {
        for (final var modeId : ModeId.values()) {
            assertEquals(modeId.name().toLowerCase(), modeId.toString());
        }
    }
}