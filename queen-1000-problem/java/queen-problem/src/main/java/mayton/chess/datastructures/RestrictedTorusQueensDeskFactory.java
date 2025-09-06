package mayton.chess.datastructures;

public class RestrictedTorusQueensDeskFactory extends RestrictedQueensDeskFactory {

    private int size;

    public RestrictedTorusQueensDeskFactory(int size) {
        this.size = size;
    }

    @Override
    public RestrictedQueensDesk getRestrictedQueensDesk() {
        return new RestrictedTorusQueensDesk(size);
    }

}
