package librerias;

import java.util.Objects;

public class Triple<F extends Comparable<F>, S extends Comparable<S>, T extends Comparable<T>>
        implements Comparable<Triple<F, S, T>> {
    private F first;
    private S second;
    private T third;

    public Triple(){
        this.first = null;
        this.second = null;
        this.third = null;
    }

    public Triple(F first, S second, T third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public F getFirst() {
        return this.first;
    }

    public S getSecond() {
        return this.second;
    }

    public T getThird(){
        return this.third;
    }

    public void setFirst(F replaceFirst) {
        this.first = replaceFirst;
    }

    public void setSecond(S replaceSecond) {
        this.second = replaceSecond;
    }

    public void setThird(T replaceThird){
        this.third = replaceThird;
    }

    @Override
    public String toString() {
        return "(" + first + "," + second + "," + third + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;

        Triple<?, ?, ?> other = (Triple<?, ?, ?>) object;
        if (getFirst().getClass() != other.getFirst().getClass()
                || getSecond().getClass() != other.getSecond().getClass()
                || getThird().getClass() != other.getThird().getClass())
            return false;

            // this.getFirst().equals(other.getFirst()) || 
        if (!this.getSecond().equals(other.getSecond()) || !this.getThird().equals(other.getThird()))
            return false;

        return true;
    }

    @Override
    public int compareTo(Triple<F,S,T> other) {
        int d = this.first.compareTo(other.first);
        if (d == 0){
            int d2 = this.second.compareTo(other.second);
            if (d2 == 0){
                return this.third.compareTo(other.third);
            }
        }
        return d;
      }


    @Override
    public int hashCode() {
        return Objects.hash(first, second, third);
    }

}