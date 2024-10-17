package librerias.Figures;

import java.util.ArrayList;
import java.util.List;

import librerias.Tuple;

public class Figures_opt {

    private long downSide, upSide, leftSide, rightSide;
    private List<Tuple<Long,Integer>> figureComponents = new ArrayList<>();
    private boolean canMove = true;

    public Figures_opt(Long altura, int figura){
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

    public void moveLeft(Long[] alturas){
        if(leftSide == 0)
            return;

        for(Tuple<Long, Integer> component : figureComponents){
            if(alturas[component.getSecond()-1] >= component.getFirst())
                return;
        }

        leftSide--;
        rightSide--;

        for(Tuple<Long, Integer> component : figureComponents){
            component.setSecond(component.getSecond()-1);
        }
        return;
    }


    public void moveRight(Long[] alturas){
        if(!(rightSide < alturas.length-1))
            return;

        for(Tuple<Long, Integer> component : figureComponents){
            if(alturas[component.getSecond()+1] >= component.getFirst())
                return;
        }

        leftSide++;
        rightSide++;

        for(Tuple<Long, Integer> component : figureComponents){
            component.setSecond(component.getSecond()+1);
        }
        return;
    }


    public void moveDown(Long[] alturas){
        if(downSide == 0){
            canMove = false;
            return;
        }

        for(Tuple<Long, Integer> component : figureComponents){
            if(alturas[component.getSecond()] >= component.getFirst()-1){
                canMove = false;
                return;
            }
        }

        downSide--;
        upSide--;

        for(Tuple<Long, Integer> component : figureComponents){
            component.setFirst(component.getFirst()-1);
        }
        return;
    }

    public Long[] getAlturas(Long[] alturas){
        for(Tuple<Long, Integer> component : figureComponents){
            if(component.getFirst() > alturas[component.getSecond()]){
                alturas[component.getSecond()] = component.getFirst();
            }
        }
        
        return alturas;
    }


    private void generateLine(Long altura){
        figureComponents.add(new Tuple<>(altura, 2));
        figureComponents.add(new Tuple<>(altura,3));
        figureComponents.add(new Tuple<>(altura,4));
        figureComponents.add(new Tuple<>(altura, 5));
        this.downSide = altura;
        this.upSide = altura;
        this.leftSide = 2;
        this.rightSide = 5;
    }

    private void generateCross(Long altura){
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

    private void generateL(Long altura){
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

    private void generateColumn(Long altura){
        figureComponents.add(new Tuple<>(altura, 2));
        figureComponents.add(new Tuple<>(altura+1,2));
        figureComponents.add(new Tuple<>(altura+2,2));
        figureComponents.add(new Tuple<>(altura+3, 2));
        this.downSide = altura;
        this.upSide = altura+3;
        this.leftSide = 2;
        this.rightSide = 2;
    }

    private void generateSquare(Long altura){
        figureComponents.add(new Tuple<>(altura, 2));
        figureComponents.add(new Tuple<>(altura,3));
        figureComponents.add(new Tuple<>(altura+1,2));
        figureComponents.add(new Tuple<>(altura+1, 3));
        this.downSide = altura;
        this.upSide = altura+1;
        this.leftSide = 2;
        this.rightSide = 3;
    }





    public long getDownSide() {
        return this.downSide;
    }

    public void setDownSide(long downSide) {
        this.downSide = downSide;
    }

    public long getUpSide() {
        return this.upSide;
    }

    public void setUpSide(long upSide) {
        this.upSide = upSide;
    }

    public long getLeftSide() {
        return this.leftSide;
    }

    public void setLeftSide(long leftSide) {
        this.leftSide = leftSide;
    }

    public long getRightSide() {
        return this.rightSide;
    }

    public void setRightSide(long rightSide) {
        this.rightSide = rightSide;
    }

    public List<Tuple<Long,Integer>> getFigureComponents() {
        return this.figureComponents;
    }

    public void setFigureComponents(List<Tuple<Long,Integer>> figureComponents) {
        this.figureComponents = figureComponents;
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


    
}
