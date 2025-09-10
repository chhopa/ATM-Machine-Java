package ATMMachine;
import java.util.Scanner;

class ATM{

    float Balance;
    int PIN = 1234;

    // PIN function
    public void checkPIN(){
        System.out.print("Enter your PIN: ");
        Scanner scanner = new Scanner(System.in);
        int enteredPIN = scanner.nextInt();
        if (enteredPIN == PIN){
            menu();

        }
        else{
            System.out.println("Wrong PIN! Try Again");
            checkPIN();
        }
    }

    // menu function
    public void menu(){

        System.out.println("Enter Your Choice: ");
        System.out.println("1. Check Account Balance ");
        System.out.println("2. Withdraw Money");
        System.out.println("3. Deposit Money");
        System.out.println("4. Exit");

        Scanner scanner = new Scanner(System.in);
        int option = scanner.nextInt();

        if (option == 1){
            checkBalance();
        }
        else if (option == 2){
            withdrawMoney();
        }
        else if (option == 3){
            depositMoney();
        }
        else if (option == 4){
            System.out.println("Thankyou! Have a great day.");
            return;
        }
        else{
            System.out.println("Invalid!!! Enter a valid choice ");
        }

    }

    // Balance checking function
    public void checkBalance(){
        System.out.println("Balance: " + Balance);
        menu();
    }

    // withdrawing amount function
    public void withdrawMoney(){
        System.out.print("Enter amount to withdraw: ");
        Scanner scanner = new Scanner(System.in);
        float amount = scanner.nextFloat();
        if (amount>Balance){
            System.out.println("Insufficient Funds");
        }
        else{
            Balance = Balance - amount;
            System.out.println("Money Withdrawn Successfully!");
            System.out.println("Current Balance: " + Balance);
        }
        menu();

    }

    // depositing amount function
    public void depositMoney(){
        System.out.print("Enter amount to deposit: ");
        Scanner scanner = new Scanner(System.in);
        float amount = scanner.nextFloat();
        Balance = Balance + amount;
        System.out.println("Deposit Completed Successfully");
        System.out.println("New Balance is " + Balance);
        menu();
    }


}

public class ATMMachine {
    public static void main(String[] args) {
        // Object creation of ATM class
        ATM obj = new ATM();
        obj.checkPIN();
    }
}

