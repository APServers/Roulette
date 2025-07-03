package net.decentstudio.gamblingaddon.util.game;

import net.decentstudio.gamblingaddon.domain.BidSection;

public class BidSectionFactory {
    
    public static BidSection number(int number) {
        return new BidSection(null, number);
    }
    
    public static BidSection red() {
        return new BidSection(SectionColor.RED, -1);
    }
    
    public static BidSection black() {
        return new BidSection(SectionColor.BLACK, -1);
    }
}