package net.decentstudio.gamblingaddon.util.ui.font;

import java.util.HashMap;
import java.util.Map;

public enum Font {

    ROBOTO_REGULAR("Roboto-Regular"),
    ROBOTO_BOLD("Roboto-Bold"),
    ROBOTO_MEDIUM("Roboto-Medium");

    private final Map<Integer, FontContainer> fonts = new HashMap<>();
    private final String name;

    Font(String name) {
        this.name = name;
    }

    public FontContainer get(int size) {
        if (!fonts.containsKey(size)) {
            fonts.put(size, new FontContainer(name, size));
        }

        return fonts.get(size);
    }
}