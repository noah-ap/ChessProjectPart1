public class MoveContext {
    public final Move move;
    public final Piece movingPiece;
    public final Piece targetPiece;
    public final int atRow, atCol, toRow, toCol;
    public final int deltaRow, deltaCol;
    public final int stepRow, stepCol;
    public final Color movingColor, targetColor;
    public final Type movingType, targetType;
    public final int pawnDirection;
    public final boolean pawnFirstMove;

    public MoveContext(Board board, Move move)
    {
        this.move = move;

        this.atRow = move.getAtRow();
        this.atCol = move.getAtCol();
        this.toRow = move.getToRow();
        this.toCol = move.getToCol();
        
        this.deltaRow = toRow - atRow;
        this.deltaCol = toCol - atCol;

        this.stepRow = Integer.signum(deltaRow);  
        this.stepCol = Integer.signum(deltaCol); 

        this.movingPiece = board.getPiece(atRow, atCol);
        this.targetPiece = board.getPiece(toRow, toCol);

        if (movingPiece != null)
        {
            this.movingColor = movingPiece.getColor();
            this.movingType  = movingPiece.getType();
        }
        else 
        {
            this.movingColor = null;
            this.movingType  = null;
        }

        if (targetPiece != null)
        {
            this.targetColor = targetPiece.getColor();
            this.targetType  = targetPiece.getType();
        }
        else
        {
            this.targetColor = null;
            this.targetType  = null;
        }

        this.pawnDirection = (movingColor == Color.WHITE) ? -1 : 1;
        this.pawnFirstMove = ( (movingColor == Color.WHITE && atRow == 6) || (movingColor == Color.BLACK && atRow == 1) );
    }
}