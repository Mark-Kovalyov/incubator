package mayton.chess;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class QueenProcessor {

    public static boolean checkPosition(@Nullable PositionCodeJson positionCodeJson){
        if (positionCodeJson==null) return false;
        int size = positionCodeJson.getSize();
        if (!checkAllowedDesks(size)) return false;
        if (size != positionCodeJson.getPositionCode().length()) return false;
        if (!containsAllDigits(positionCodeJson.getPositionCode())) return false;
        if (!checkQueenAttacks(size, positionCodeJson.getPositionCode())){
            return true;
        }
        return false;
    }

    public static boolean checkQueenAttacks(int size, String pos){

        return false;
    }

    public static boolean checkAllowedDesks(int size){
        return  (size == 9 || size == 1000) ;
    }

    public static boolean containsAllDigits(String positionCode) {
        if (positionCode!=null) {
            for (int i = 0; i < positionCode.length(); i++) {
                if (!Character.isDigit(positionCode.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean checkPosition(@Nullable String positionCode){
        if (positionCode == null || positionCode.length()==0) {
            return false;
        }

        if (!containsAllDigits(positionCode)){
            return false;
        }


        return true;
    }

    public static boolean containsMetaSymbols(@Nullable String positionCode) {
        if (positionCode == null || positionCode.length()==0) {
            return false;
        }
        return  (positionCode.contains("%") || positionCode.contains("_"));
    }
}
