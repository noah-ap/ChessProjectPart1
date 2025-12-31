public class Chess3 {
    public static void main(String[] args) 
    {
        Board board = new Board();
        UserInput userMove = new UserInput();

        board.printBoard();

        boolean checkMate = false;

        while (checkMate == false)
        {
            Move move = userMove.getMove();

            if (move != null)
            {
            board.applyMove(move);
            board.printBoard();
            }

            String attackerColor = "None";
            if (board.getCurrentTurn() == Color.WHITE)
            {
                attackerColor = "White";
            }
            if (board.getCurrentTurn() == Color.BLACK)
            {
                attackerColor = "Black";
            }

            String defenderColor = "None";
            if (board.getCurrentTurn() == Color.WHITE)
            {
                defenderColor = "Black";
            }
            if (board.getCurrentTurn() == Color.BLACK)
            {
                defenderColor = "White";
            }
            
            boolean check      = board.isInCheck();
            boolean kingSafety = board.isKingSafe();
            System.out.println(attackerColor + " in check? " + check);
            System.out.println(defenderColor + " is safe? " + kingSafety);
            System.out.println("Current Color's Turn: " + attackerColor);
        }
    }    
}   




    
