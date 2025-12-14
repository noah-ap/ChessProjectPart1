import java.util.Scanner;

public class UserInput 
{
    private Scanner input;

    public UserInput() 
    {
        input = new Scanner(System.in);
    }

    public String getMove() 
    {
        System.out.println("Enter a move: ");
        return input.nextLine();
    }
}