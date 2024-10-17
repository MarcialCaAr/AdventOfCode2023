package librerias;

import java.util.ArrayList;
import java.util.List;

public class Combination {

    public static List<String> getCombinations(char[] input, int size){
        return getCombinations(input, size, 0,"");
    }


    private static List<String> getCombinations(char[] input, int size, int actualChar, String currentComb){
        List<String> res = new ArrayList<>();
        if(actualChar == size){
            res.add(currentComb);
            return res;
        }

        for(int i = 0; i < input.length; i++){
            res.addAll(getCombinations(input, size, actualChar+1, currentComb + input[i]));

        }

        return res;
    } 
}
