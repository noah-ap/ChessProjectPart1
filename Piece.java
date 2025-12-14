public class Piece {
    private String type;
    private String position;
    private int row;
    private int col;

    public Piece(String type, String position, int row, int col)
    {
        this.type = type;
        this.position = position;
        this.row = row;
        this.col = col;
    }

    public String getType() {return type;}
    public String getPosition() {return position;}
    public int getRow() {return row;}
    public int getCol() {return col;}

    public void setType(String type)
    {
        this.type = type;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public void setXY(int x, int y)
    {
        this.row = x;
        this.col = y;
    }
}