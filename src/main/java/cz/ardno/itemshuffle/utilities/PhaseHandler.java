package cz.ardno.itemshuffle.utilities;

public class PhaseHandler {

    private static int phase = 0;

    public static void increase() {
        phase++;
    }

    public static void decrease() {
        phase--;
    }

    public static void set(int phase) {
        PhaseHandler.phase = phase - 1;
    }

    public static int getPhase() {
        return phase;
    }

    public static void reset() {
        phase = 0;
    }
}
