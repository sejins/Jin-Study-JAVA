package chapter12.enums;

public enum DirectionWithSymbol {
    WEST(1,"<"), SOUTH(2,"V"), EAST(3,">"), NORTH(4,"^");

    private static final DirectionWithSymbol[] DIR_ARR = DirectionWithSymbol.values();
    private final int value;
    private final String symbol;

    DirectionWithSymbol(int value, String symbol){
        this.value = value;
        this.symbol = symbol;
    }

    public int getValue(){return value;}
    public String getSymbol(){return symbol;}

    public static DirectionWithSymbol of(int dir){
        if(dir<1 || dir>4){
            throw new IllegalArgumentException("Invalid value : " + dir);
        }
        return DIR_ARR[dir-1];
    }

    public DirectionWithSymbol rotate(int num){
        num %=4;
        if(num<0)
            num+=4;
        return DIR_ARR[(value-1+num)%4];
    }

    public String toString(){return name() + " " + getSymbol();}
}
