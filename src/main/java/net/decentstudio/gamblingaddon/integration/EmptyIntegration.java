package net.decentstudio.gamblingaddon.integration;

public class EmptyIntegration implements IIntegration {

    @Override
    public int getCurrentGameRoomId(String nick) {
//        return -1; todo return back
        return 1;
    }
}
