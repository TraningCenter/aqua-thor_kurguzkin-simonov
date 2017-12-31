package com.simonov_kurguzkin.aquathor.visualizer.visHandies;

/**
 *
 * @author q
 */
public class StreamView implements EntityView {
    private final int start;
    private final int end;
    private final int speed;

    public StreamView(int start, int end, int speed) {
        this.start = start;
        this.end = end;
        this.speed = speed;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getSpeed() {
        return speed;
    }
}
