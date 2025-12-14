public class Board 
{
    private String[][] layoutType;
    private String[][] layoutPosition;
    private Piece[][] pieces;

    public Board()
    {
        layoutType = new String[][] { 
            {"xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx"},       
            {"8 ", "BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR", "xx"},
            {"7 ", "BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP", "xx"},
            {"6 ", "--", "--", "--", "--", "--", "--", "--", "--", "xx"},
            {"5 ", "--", "--", "--", "--", "--", "--", "--", "--", "xx"},
            {"4 ", "--", "--", "--", "--", "--", "--", "--", "--", "xx"},
            {"3 ", "--", "--", "--", "--", "--", "--", "--", "--", "xx"},
            {"2 ", "WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP", "xx"},
            {"1 ", "WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR", "xx"},
            {"xx", "a ", "b ", "c ", "d ", "e ", "f ", "g ", "h ", "xx"} 
            }; 
        
        layoutPosition = new String[][] { 
            {"xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx"},
            {"xx", "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", "xx"},
            {"xx", "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7", "xx"},
            {"xx", "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6", "xx"},
            {"xx", "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5", "xx"},
            {"xx", "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4", "xx"},
            {"xx", "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3", "xx"},
            {"xx", "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2", "xx"},
            {"xx", "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "xx"},
            {"xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx", "xx"} 
            };

        pieces = new Piece[10][10];
        for(int r = 0; r < 10; r++)
            {
            for (int c = 0; c < 10; c++)
                {
                    pieces[r][c] = new Piece(layoutType[r][c], layoutPosition[r][c], r, c);
                }
            }
    }

    public void printBoard() 
    {
        for (int r = 0; r < 10; r++) 
            {
                for (int c = 0; c < 10; c++) 
                {
                    System.out.print(pieces[r][c].getType() + " ");
                }
                System.out.println();
            }
    }
}
