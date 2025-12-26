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

    public Square getKingSquare(Color color)
    {
        for (int r = 0; r < 8; r++) 
            {
                for (int c = 0; c < 8; c++) 
                {
                    Piece p = pieces[r][c];

                    if (p != null && p.getType() == Type.KING && p.getColor() == color)
                    {
                        return new Square(r, c);
                    }
                }
            }
        return null;
    }

    public boolean isSquareAttacked(Square square, Color color)
    {
        for (int r = 0; r < 8; r++) 
            {
                for (int c = 0; c < 8; c++) 
                {
                    Piece p = pieces[r][c];
                    int targetRow = square.getRow();
                    int targetCol = square.getCol();

                    if (p == null)               { continue; }
                    if (p.getColor() == color)   { continue; }
                    
                    // King 
                    if (p.getType() == Type.KING)
                    {
                        if (Math.abs(targetRow - r) <= 1 && Math.abs(targetCol - c) <= 1) 
                        { return true; } 
                    }

                    // Queen
                    if (p.getType() == Type.QUEEN)
                    {
                        if ((targetRow == r) || (targetCol == c))
                        {
                            int stepRow = Integer.compare(targetRow, r);
                            int stepCol = Integer.compare(targetCol, c);

                            int currentRow = r + stepRow;
                            int currentCol = c + stepCol;

                            while (currentRow != targetRow || currentCol != targetCol)
                            {
                                if (pieces[currentRow][currentCol] != null)
                                { break; }

                                if (currentRow == targetRow && currentCol == targetCol)
                                { return true; }

                                currentRow += stepRow;
                                currentCol += stepCol;
                            }
                        }

                        if (Math.abs(targetRow - r) == Math.abs(targetCol - c))
                        { 
                            int stepRow = Integer.compare(targetRow, r);
                            int stepCol = Integer.compare(targetCol, c);

                            int currentRow = r + stepRow;
                            int currentCol = c + stepCol;

                            while (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8)
                            {
                                if (pieces[currentRow][currentCol] != null)
                                { break; }

                                if (currentRow == targetRow && currentCol == targetCol)
                                { return true; }

                                currentRow += stepRow;
                                currentCol += stepCol;
                            }
                        }
                    }

                    // Rook
                    if (p.getType() == Type.ROOK)
                    {
                        if ((targetRow == r) || (targetCol == c))
                        {
                            int stepRow = Integer.compare(targetRow, r);
                            int stepCol = Integer.compare(targetCol, c);

                            int currentRow = r + stepRow;
                            int currentCol = c + stepCol;

                            while (currentRow != targetRow || currentCol != targetCol)
                            {
                                if (pieces[currentRow][currentCol] != null)
                                { break; }

                                if (currentRow == targetRow && currentCol == targetCol)
                                { return true; }

                                currentRow += stepRow;
                                currentCol += stepCol;
                            }
                        }
                    }

                    // Bishop
                    if (p.getType() == Type.BISHOP)
                    {
                        if (Math.abs(targetRow - r) == Math.abs(targetCol - c))
                        { 
                            int stepRow = Integer.compare(targetRow, r);
                            int stepCol = Integer.compare(targetCol, c);

                            int currentRow = r + stepRow;
                            int currentCol = c + stepCol;

                            while (currentRow >= 0 && currentRow < 8 && currentCol >= 0 && currentCol < 8)
                            {
                                if (pieces[currentRow][currentCol] != null)
                                { break; }

                                if (currentRow == targetRow && currentCol == targetCol)
                                { return true; }

                                currentRow += stepRow;
                                currentCol += stepCol;
                            }
                        }
                    }

                    // Knight
                    if (p.getType() == Type.KNIGHT)
                    {
                        if ( ((Math.abs(targetRow - r) == 2) && (Math.abs(targetCol - c) == 1)) || ((Math.abs(targetRow - r) == 1) && (Math.abs(targetCol - c) == 2)) )
                        { return true; }
                    }
                    
                    // Pawn
                    if (p.getType() == Type.PAWN)
                    {
                        int pawnDirection = (p[r][c].getColor == Color.WHITE) ? -1 : 1;

                        if ( (targetRow - r) == pawnDirection && Math.abs(targetCol - c) == 1 ) 
                        {return true;}
                    } 
                }
            }

            return false;
    }

}
