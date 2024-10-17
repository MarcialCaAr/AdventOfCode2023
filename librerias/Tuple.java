package librerias;

import java.util.Objects;

public class Tuple<F extends Comparable<F>, S extends Comparable<S>>
        implements Comparable<Tuple<F, S>> {
    private F first;
    private S second;

    public Tuple(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return this.first;
    }

    public S getSecond() {
        return this.second;
    }

    public void setFirst(F replaceFirst) {
        this.first = replaceFirst;
    }

    public void setSecond(S replaceSecond) {
        this.second = replaceSecond;
    }

    @Override
    public String toString() {
        return "(" + first + "," + second + ")";
    }

    @Override
    public boolean equals(Object object) {
        // Returns true if this object is a member of this object.
        if (this == object)
            return true;
        // Returns true if the object is null.
        if (object == null)
            return false;
        // Returns true if the class of the object is equal to the class of the object.
        if (getClass() != object.getClass())
            return false;
        Tuple<?, ?> other = (Tuple<?, ?>) object;


        // Returns true if the first and second classes are equal.
        if (getFirst().getClass() != other.getFirst().getClass()
                || getSecond().getClass() != other.getSecond().getClass())
            return false;

        // Returns true if this object is equal to the other object.
        if (!this.getFirst().equals(other.getFirst()) || !this.getSecond().equals(other.getSecond()))
            return false;

        return true;
    }

    // @Override
    // public int hashCode() {
    //     final int prime = 31;
    //     int result = 1;
    //     result = prime * result + ((first == null) ? 0 : first.hashCode());
    //     result = prime * result + ((second == null) ? 0 : second.hashCode());
    //     return result;
    // }


    /**
    * Returns a hash code for this tuple. This is equivalent to Objects#hashCode ( Object Object ) but more efficient when comparing two tuples in a single operation.
    * 
    * 
    * @return a hash code for this tuple ( never null ). The result is undefined if this tuple is empty
    */
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }


    /**
    * Compares this tuple to another. The comparison is based on the first component of the tuple and the second component of the tuple.
    * 
    * @param other - the tuple to compare to not null. Must not be null.
    * 
    * @return negative if this is less zero if equal positive if greater and zero if both are equal ( in the case of equals
    */
    @Override
    public int compareTo(Tuple<F, S> other) {
        int d = this.first.compareTo(other.first);
        // Compare this object to another object.
        if (d == 0)
          return this.second.compareTo(other.second);
        return d;
      }
}