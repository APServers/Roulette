package net.decentstudio.gamblingaddon.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class Gambler {

    private final UUID id;
    private final String name;
}
