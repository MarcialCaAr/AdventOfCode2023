package librerias.Figures;

import java.util.ArrayList;
import java.util.List;

import librerias.Tuple;

public class Figures {

    private int downSide, upSide, leftSide, rightSide;
    private List<Tuple<Integer,Integer>> figureComponents = new ArrayList<>();
    private boolean canMove = true;

    public Figures(int altura, int figura){
        switch(figura){
            case 0:
                generateLine(altura);
                break;
            case 1:
                generateCross(altura);
                break;
            case 2:
                generateL(altura);
                break;
            case 3:
                generateColumn(altura);
                break;
            case 4:
                generateSquare(altura);
                break;
        }

    }

    public List<Character[]> moveLeft(List<Character[]> board){
        if(leftSide == 0)
            return board;

        for(Tuple<Integer, Integer> component : figureComponents){
            if(board.get(component.getFirst())[component.getSecond()-1] == '#')
                return board;
        }

        leftSide--;
        rightSide--;

        for(Tuple<Integer, Integer> component : figureComponents){
            board.get(component.getFirst())[component.getSecond()] = '.';
            component.setSecond(component.getSecond()-1);
            board.get(component.getFirst())[component.getSecond()] = '@';
        }
        return board;
    }


    public List<Character[]> moveRight(List<Character[]> board){
        if(!(rightSide < board.get(0).length-1))
            return board;

        for(Tuple<Integer, Integer> component : figureComponents){
            if(board.get(component.getFirst())[component.getSecond()+1] == '#')
                return board;
        }

        leftSide++;
        rightSide++;

        for(Tuple<Integer, Integer> component : figureComponents){
            board.get(component.getFirst())[component.getSecond()] = '.';
            component.setSecond(component.getSecond()+1);
            board.get(component.getFirst())[component.getSecond()] = '@';
        }
        return board;
    }


    public List<Character[]> moveDown(List<Character[]> board){
        if(downSide == 0){
            for(Tuple<Integer, Integer> component : figureComponents){
                board.get(component.getFirst())[component.getSecond()] = '#';
            }
            canMove = false;
            return board;
        }

        for(Tuple<Integer, Integer> component : figureComponents){
            if(board.get(component.getFirst()-1)[component.getSecond()] == '#'){
                for(Tuple<Integer, Integer> component2 : figureComponents){
                    board.get(component2.getFirst())[component2.getSecond()] = '#';
                }
                canMove = false;
                return board;
            }
        }

        downSide--;
        upSide--;

        for(Tuple<Integer, Integer> component : figureComponents){
            board.get(component.getFirst())[component.getSecond()] = '.';
            component.setFirst(component.getFirst()-1);
            board.get(component.getFirst())[component.getSecond()] = '@';
        }
        return board;
    }


    private void generateLine(int altura){
        figureComponents.add(new Tuple<>(altura, 2));
        figureComponents.add(new Tuple<>(altura,3));
        figureComponents.add(new Tuple<>(altura,4));
        figureComponents.add(new Tuple<>(altura, 5));
        this.downSide = altura;
        this.upSide = altura;
        this.leftSide = 2;
        this.rightSide = 5;
    }

    private void generateCross(int altura){
        figureComponents.add(new Tuple<>(altura, 3));
        figureComponents.add(new Tuple<>(altura+1,2));
        figureComponents.add(new Tuple<>(altura+1,3));
        figureComponents.add(new Tuple<>(altura+1, 4));
        figureComponents.add(new Tuple<>(altura+2, 3));
        this.downSide = altura;
        this.upSide = altura+2;
        this.leftSide = 2;
        this.rightSide = 4;
    }

    private void generateL(int altura){
        figureComponents.add(new Tuple<>(altura, 2));
        figureComponents.add(new Tuple<>(altura,3));
        figureComponents.add(new Tuple<>(altura,4));
        figureComponents.add(new Tuple<>(altura+1, 4));
        figureComponents.add(new Tuple<>(altura+2, 4));
        this.downSide = altura;
        this.upSide = altura+2;
        this.leftSide = 2;
        this.rightSide = 4;
    }

    private void generateColumn(int altura){
        figureComponents.add(new Tuple<>(altura, 2));
        figureComponents.add(new Tuple<>(altura+1,2));
        figureComponents.add(new Tuple<>(altura+2,2));
        figureComponents.add(new Tuple<>(altura+3, 2));
        this.downSide = altura;
        this.upSide = altura+3;
        this.leftSide = 2;
        this.rightSide = 2;
    }

    private void generateSquare(int altura){
        figureComponents.add(new Tuple<>(altura, 2));
        figureComponents.add(new Tuple<>(altura,3));
        figureComponents.add(new Tuple<>(altura+1,2));
        figureComponents.add(new Tuple<>(altura+1, 3));
        this.downSide = altura;
        this.upSide = altura+1;
        this.leftSide = 2;
        this.rightSide = 3;
    }




    public boolean isCanMove() {
        return this.canMove;
    }

    public boolean getCanMove() {
        return this.canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public int getDownSide() {
        return this.downSide;
    }

    public void setDownSide(int downSide) {
        this.downSide = downSide;
    }

    public int getUpSide() {
        return this.upSide;
    }

    public void setUpSide(int upSide) {
        this.upSide = upSide;
    }

    public int getLeftSide() {
        return this.leftSide;
    }

    public void setLeftSide(int leftSide) {
        this.leftSide = leftSide;
    }

    public int getRightSide() {
        return this.rightSide;
    }

    public void setRightSide(int rightSide) {
        this.rightSide = rightSide;
    }

    public List<Tuple<Integer,Integer>> getFigureComponents() {
        return this.figureComponents;
    }

    public void setFigureComponents(List<Tuple<Integer,Integer>> figureComponents) {
        this.figureComponents = figureComponents;
    }

    
}
