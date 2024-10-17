package day22;

import java.util.HashSet;

public class Brick {

    public int startX,
            startY,
            startZ,
            endX,
            endY,
            endZ;
    
    public HashSet<Integer> suportedBricks, suportingBricks;

    public Brick(String sX, String sY, String sZ, String eX, String eY, String eZ){
        startX = Integer.parseInt(sX);
        startY = Integer.parseInt(sY);
        startZ = Integer.parseInt(sZ);
        endX = Integer.parseInt(eX);
        endY = Integer.parseInt(eY);
        endZ = Integer.parseInt(eZ);
        suportedBricks = new HashSet<>();
        suportingBricks = new HashSet<>();
    }
}