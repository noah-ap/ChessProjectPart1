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
                    {
                        color = Color.WHITE;
                    }
                    else 
                    {
                        color = Color.BLACK;
                    }

                    switch (cell.charAt(1))
                    {
                        case 'K': type = Type.KING;     break;
                        case 'Q': type = Type.QUEEN;    break;
                        case 'R': type = Type.ROOK;     break;
                        case 'B': type = Type.BISHOP;   break;
                        case 'N': type = Type.KNIGHT;   break;   
                        case 'P': type = Type.PAWN;     break;  
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

    public void applyMove(Move move) 
    {
        int fromRow = move.getAtRow();
        int fromCol = move.getAtCol();
        int toRow = move.getToRow();
        int toCol = move.getToCol();

        int deltaRow = toRow - fromRow;
        int deltaCol = toCol - fromCol;

        boolean legal = false;

        Piece movingPiece = pieces[fromRow][fromCol];
        Piece targetPiece = pieces[toRow][toCol];

        // Moving Piece is a Blank Square
        if (movingPiece == null)
        {
            System.out.println("No piece at square!");
            return;
        }

        Color movingColor  = movingPiece.getColor();
        Type  movingType   = movingPiece.getType();

        Color targetColor;
        //Type  targetType   = targetPiece.getType();

        // Check Target Square
        if (targetPiece != null)
        {

            targetColor  = targetPiece.getColor();

            if (movingColor == targetColor)
            {
                System.out.println("Target not allowed!");
                return;
            }
        }

        // King - Add Castling
        if (movingType == Type.KING)
        {
            if (Math.abs(deltaRow) <= 1 && Math.abs(deltaCol) <= 1) 
            { legal = true; } 
        }

        // Queen
        if (movingType == Type.QUEEN)
        {
            if ( (Math.abs(deltaRow) == Math.abs(deltaCol)) || ((deltaRow == 0) ^ (deltaCol == 0)) )
            { legal = true; }
        }

        // Rook
        if (movingType == Type.ROOK)
        {
            if ((deltaRow == 0) ^ (deltaCol == 0))
            { legal = true; }
        }

        // Bishop
        if (movingType == Type.BISHOP)
        {
            if (Math.abs(deltaRow) == Math.abs(deltaCol))
            { legal = true; }
        }

        // Knight
        if (movingType == Type.KNIGHT)
        {
            if ( ((Math.abs(deltaRow) == 2) && (Math.abs(deltaCol) == 1)) || ((Math.abs(deltaRow) == 1) && (Math.abs(deltaCol) == 2)) )
            { legal = true; }
        }

        // Pawn - Add first move detection = if first move allow pawn to move up 2
        if (movingType == Type.PAWN)
        {   
            int direction = 0;
            if (movingColor == Color.BLACK) { direction = 1; }
            if (movingColor == Color.WHITE) { direction = -1;  }

            if (deltaRow == direction && Math.abs(deltaCol) == 0) {legal = true;}

            // Capture only legal if piece at target square
            if ( deltaRow == direction && Math.abs(deltaCol) == 1 && (targetPiece != null) ) {legal = true;}
        }

        if (legal == true)
        {
            pieces[toRow][toCol] = movingPiece;

            // Update cords of Piece
            movingPiece.setRowCol(toRow, toCol);

            pieces[fromRow][fromCol] = null;
        
            System.out.println( "Moved " + movingPiece.getColor() + " " + movingPiece.getType() +
                            " from (" + fromRow + "," + fromCol + ") to (" + toRow + "," + toCol + ")");
        }
        else
        {
            System.out.println("Move NOT Legal!");
        }

        legal = false;

    }
    /* Check piece type at At Position
      Check if piece move legal
      Check # status

      if good make move

      Set To Position to Type
      Set At Position to "--"

      Update Board
      Call next move and change player turns
   */

}
