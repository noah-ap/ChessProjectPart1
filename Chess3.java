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

            String turnStat = "None";
            if (board.getCurrentTurn() == Color.WHITE)
            {
                turnStat = "White";
            }
            if (board.getCurrentTurn() == Color.BLACK)
            {
                turnStat = "Black";
            }
            
            boolean check = board.isInCheck();
            System.out.println(turnStat + " in check? " + check);
        }
    }    
}   




    
