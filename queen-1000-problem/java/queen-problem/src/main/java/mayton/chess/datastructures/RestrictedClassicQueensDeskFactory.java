package mayton.chess.datastructures;

public class RestrictedClassicQueensDeskFactory extends RestrictedQueensDeskFactory {

    private int size;

    public RestrictedClassicQueensDeskFactory(int size){
        this.size = size;
    }

    @Override
    public RestrictedQueensDesk getRestrictedQueensDesk() {
        return new RestrictedClassicQueensDesk(size);
    }

}
