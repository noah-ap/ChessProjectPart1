public class Board 
{
    private String[][] layout;
    private Piece[][] pieces;

    public Board()
    {
        layout = new String[][] {        
            {"BR", "BN", "BB", "BQ", "BK", "BB", "BN", "BR"},
            {"BP", "BP", "BP", "BP", "BP", "BP", "BP", "BP"},
            {"--", "--", "--", "--", "--", "--", "--", "--"},
            {"--", "--", "--", "--", "--", "--", "--", "--"},
            {"--", "--", "--", "--", "--", "--", "--", "--"},
            {"--", "--", "--", "--", "--", "--", "--", "--"},
            {"WP", "WP", "WP", "WP", "WP", "WP", "WP", "WP"},
            {"WR", "WN", "WB", "WQ", "WK", "WB", "WN", "WR"} 
            }; 

        pieces = new Piece[8][8];
        for(int r = 0; r < 8; r++)
            {
            for (int c = 0; c < 8; c++)
                {
                    Type type = Type.PAWN; 
                    Color color;

                    String cell = layout[r][c];

                    if (cell.equals("--"))
                    {
                        pieces[r][c] = null;
                        continue;
                    }

                    if (cell.charAt(0) == 'W') 
                    {color = Color.WHITE;}
                    else {color = Color.BLACK;}

                    switch (cell.charAt(1))
                    {
                        case 'K':
                            type = Type.KING;
                            break;
                        case 'Q':
                            type = Type.QUEEN;
                            break;
                        case 'R':
                            type = Type.ROOK;
                            break;
                        case 'B':
                            type = Type.BISHOP;
                            break;
                        case 'N':
                            type = Type.KNIGHT;
                            break;   
                        case 'P':
                            type = Type.PAWN;
                            break;  
                    }

                    pieces[r][c] = new Piece(color, type, r, c);
                }
            }
    }

    public void printBoard() 
    {
        for (int r = 0; r < 8; r++) 
            {
                for (int c = 0; c < 8; c++) 
                {

                    Piece pChar = pieces[r][c];
                    char colorChar = '-';
                    char typeChar = '-';

                    if (pChar == null)
                    {
                       System.out.printf("%c%c ", colorChar, typeChar); 
                       continue;
                    }
                    else 
                    {
                        switch (pChar.getColor())
                        {
                            case WHITE: colorChar = 'W'; break;
                            case BLACK: colorChar = 'B'; break;
                        }

                        switch (pChar.getType())
                        {
                            case KING:      typeChar = 'K'; break;
                            case QUEEN:     typeChar = 'Q'; break;
                            case ROOK:      typeChar = 'R'; break;
                            case BISHOP:    typeChar = 'B'; break;
                            case KNIGHT:    typeChar = 'N'; break;
                            case PAWN:      typeChar = 'P'; break;
                        }

                        System.out.printf("%c%c ", colorChar, typeChar); 
                    }
                }
                System.out.println();
            }
    }
}
