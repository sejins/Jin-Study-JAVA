package chapter11;

public class ComparatorEx{
    private int x;
    private int y;

    public ComparatorEx(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString(){
        return x+" , "+y;
    }
}

