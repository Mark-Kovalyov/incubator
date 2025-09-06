package mayton.chess.datastructures;

public abstract class RestrictedQueensDesk extends ChessDesk {

    public RestrictedQueensDesk(int size) {
        super(size);
    }

    public abstract boolean tryToSetQueen(int x, int y);

    public abstract boolean isUnderFire(int x, int y);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int y = 0; y < size; y++) {
            for(int x = 0; x < size; x++) {
                if (getValue(x,y)) {
                    sb.append("Q");
                } else if (isUnderFire(x,y)) {
                    sb.append("+");
                } else {
                    sb.append(".");
                }

                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
