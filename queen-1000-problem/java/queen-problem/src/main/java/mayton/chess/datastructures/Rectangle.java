package mayton.chess.datastructures;

import javax.annotation.concurrent.Immutable;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Immutable
public final class Rectangle {

    public final int x1;
    public final int y1;
    public final int x2;
    public final int y2;

    public Rectangle(int x1, int y1, int x2, int y2) {
        this.x1 = min(x1, x2);
        this.y1 = min(y1, y2);
        this.x2 = max(x1,x2);
        this.y2 = max(y1,y2);
    }
}
