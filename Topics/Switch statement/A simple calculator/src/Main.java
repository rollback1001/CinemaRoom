import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner( System.in );

        String userInput = scanner.nextLine();

        String[] userInputArray = userInput.split( " " );

        Long firstNum = Long.parseLong( userInputArray[0]);
        String operand = userInputArray[1];
        Long SecondNum = Long.parseLong( userInputArray[2] );

        switch ( operand ) {
            case "+":
                System.out.println(firstNum + SecondNum);
                break;
            case "-":
                System.out.println(firstNum - SecondNum);
                break;
            case "*":
                System.out.println(firstNum * SecondNum);
                break;
            case "/":
                if ( SecondNum == 0 ) {
                    System.out.println("Division by 0!");
                } else {
                    System.out.println(firstNum / SecondNum);
                }
                break;
            default:
                System.out.println("Unknown operator");
        }
    }
}