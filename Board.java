public class Board 
{
    private String[][] layout;
    private Piece[][] pieces;

    private Color currentTurn = Color.WHITE;

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

    public Piece getPiece(int r, int c) { return pieces[r][c]; }
    public Color getCurrentTurn()       { return currentTurn; }

    public void applyMove(Move move) 
    {
        MoveContext ctx = new MoveContext(this, move);

        if (isLegalMove(ctx))
        {
            // Update Board Array
            pieces[ctx.toRow][ctx.toCol] = ctx.movingPiece;
            pieces[ctx.atRow][ctx.atCol] = null;
            
            // Update cords of Piece
            ctx.movingPiece.setRowCol(ctx.toRow, ctx.toCol);
            
            // Update turn
            currentTurn = (currentTurn == Color.WHITE) ? Color.BLACK : Color.WHITE;

            System.out.println( "Moved " + ctx.movingPiece.getColor() + " " + ctx.movingPiece.getType() +
                            " from (" + ctx.atRow + "," + ctx.atCol + ") to (" + ctx.toRow + "," + ctx.toCol + ")");
        }
        else
        {
            System.out.println("Move NOT Legal!");
        }
    }

    public boolean isInsideBoard(MoveContext ctx)
    {
        return isInside(ctx.atRow, ctx.atCol) && isInside(ctx.toRow, ctx.toCol);
    }
    
    private boolean isInside(int r, int c)
    {
        return r >= 0 && r < 8 && c >= 0 && c < 8;
    }

    public boolean isCorrectTurn(MoveContext ctx)
    {
        if (ctx.movingColor != currentTurn)
        {
            System.out.println("It's " + currentTurn + "'s turn!");
            return false;
        }
        return true;
    }

    public boolean hasMovingPiece(MoveContext ctx)
    {
        return ctx.movingPiece != null;
    }

    public boolean isTargetAllowed(MoveContext ctx)
    {
        if (ctx.targetPiece != null)
        {
            if (ctx.movingColor == ctx.targetColor)
            {
                System.out.println("Target not allowed!");
                return false;
            }
        }
        return true;
    }

    public boolean isPieceMoveLegal(MoveContext ctx)
    {
        // King - Add Castling
        if (ctx.movingType == Type.KING)
        {
            if (Math.abs(ctx.deltaRow) <= 1 && Math.abs(ctx.deltaCol) <= 1) 
            { return true; } 
        }

        // Queen
        if (ctx.movingType == Type.QUEEN)
        {
            if ( (Math.abs(ctx.deltaRow) == Math.abs(ctx.deltaCol)) || ((ctx.deltaRow == 0) ^ (ctx.deltaCol == 0)) )
            { return true; }
        }

        // Rook
        if (ctx.movingType == Type.ROOK)
        {
            if ((ctx.deltaRow == 0) ^ (ctx.deltaCol == 0))
            { return true; }
        }

        // Bishop
        if (ctx.movingType == Type.BISHOP)
        {
            if (Math.abs(ctx.deltaRow) == Math.abs(ctx.deltaCol))
            { return true; }
        }

        // Knight
        if (ctx.movingType == Type.KNIGHT)
        {
            if ( ((Math.abs(ctx.deltaRow) == 2) && (Math.abs(ctx.deltaCol) == 1)) || ((Math.abs(ctx.deltaRow) == 1) && (Math.abs(ctx.deltaCol) == 2)) )
            { return true; }
        }

        // Pawn
        if (ctx.movingType == Type.PAWN)
        {   
            // Move 1 Square Foward
            if (ctx.deltaRow == ctx.pawnDirection && Math.abs(ctx.deltaCol) == 0) 
            {return true;}

            // Move 2 Squares Foward
            if ((ctx.deltaRow == (ctx.pawnDirection * 2) && Math.abs(ctx.deltaCol) == 0) && (ctx.pawnFirstMove)) 
            {return true;}

            // Capture only legal if piece at target square
            if ( ctx.deltaRow == ctx.pawnDirection && Math.abs(ctx.deltaCol) == 1 && (ctx.targetPiece != null) ) 
            {return true;}
        } 
        return false;
    }

    public boolean isPathClear(MoveContext ctx)
    {
        int currentRow = ctx.atRow + ctx.stepRow;
        int currentCol = ctx.atCol + ctx.stepCol;

        if (ctx.movingType == Type.QUEEN || ctx.movingType == Type.ROOK || ctx.movingType == Type.BISHOP)
        {
            while (currentRow != ctx.toRow || currentCol != ctx.toCol)
            {
                if (pieces[currentRow][currentCol] != null)
                {
                    System.out.println("Path blocked!");
                    return false; 
                }

                currentRow += ctx.stepRow;
                currentCol += ctx.stepCol;
            }
        }
        return true;
    }

    public boolean isLegalMove(MoveContext ctx) 
    {
    if (!isInsideBoard(ctx)) return false;
    if (!hasMovingPiece(ctx)) return false;
    if (!isCorrectTurn(ctx)) return false;
    if (!isTargetAllowed(ctx)) return false;
    if (!isPieceMoveLegal(ctx)) return false;
    if (!isPathClear(ctx)) return false;

    return true;
    }

    /* public void applyMove(Move move) 
    {
        MoveContext ctx = new MoveContext(this, move);

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
        // Type  targetType   = targetPiece.getType();

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

        // Path Clear?

        int rowStep = Integer.signum(deltaRow);
        int colStep = Integer.signum(deltaCol);

        int currentRow = fromRow + rowStep;
        int currentCol = fromCol + colStep;

        while (currentRow != toRow || currentCol != toCol)
        {
            if (pieces[currentRow][currentCol] != null)
            {
                System.out.println("Path blocked!");
                return; // or legal = false
            }

            currentRow += rowStep;
            currentCol += colStep;
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
            boolean firstMove = false;
            boolean promotion = false;

            if (movingColor == Color.BLACK) 
            { 
                direction = 1;
                if (fromRow == 1)
                {
                    firstMove = true;
                }
                if (toRow == 7)
                {
                    promotion = true;
                }
            }

            if (movingColor == Color.WHITE) 
            { 
                direction = -1; 
                if (fromRow == 6)
                {
                firstMove = true;
                }
                if (toRow == 0)
                {
                    promotion = true;
                }
            }

            // Move 1 Square Foward
            if (deltaRow == direction && Math.abs(deltaCol) == 0) {legal = true;}

            // Move 2 Squares Foward
            if ((deltaRow == (direction * 2) && Math.abs(deltaCol) == 0) && (firstMove == true)) {legal = true;}

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
