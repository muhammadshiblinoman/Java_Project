import java.util.*;

// Account class representing a bank account
class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public Account(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
        System.out.println("Deposited $" + amount + " successfully.");
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
        } else if (amount > balance) {
            System.out.println("Insufficient funds.");
        } else {
            balance -= amount;
            System.out.println("Withdrew $" + amount + " successfully.");
        }
    }

    public void displayAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println("Current Balance: $" + balance);
    }
}

// Bank class to manage multiple accounts
class Bank {
    private Map<String, Account> accounts = new HashMap<>();

    public void createAccount(String number, String holder, double balance) {
        if (accounts.containsKey(number)) {
            System.out.println("Account already exists.");
            return;
        }
        accounts.put(number, new Account(number, holder, balance));
        System.out.println("Account created successfully.");
    }

    public Account getAccount(String number) {
        return accounts.get(number);
    }

    public void displayAllAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        for (Account account : accounts.values()) {
            account.displayAccountInfo();
            System.out.println("------------------------");
        }
    }
}

// Main class with menu
public class BankManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();
        boolean running = true;

        while (running) {
            System.out.println("\n--- Bank Management System ---");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Show All Accounts");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Account Number: ");
                    String number = sc.nextLine();
                    System.out.print("Enter Account Holder Name: ");
                    String holder = sc.nextLine();
                    System.out.print("Enter Initial Balance: ");
                    double balance = sc.nextDouble();
                    sc.nextLine();
                    bank.createAccount(number, holder, balance);
                }
                case 2 -> {
                    System.out.print("Enter Account Number: ");
                    String number = sc.nextLine();
                    Account acc = bank.getAccount(number);
                    if (acc != null) {
                        System.out.print("Enter amount to deposit: ");
                        double amount = sc.nextDouble();
                        acc.deposit(amount);
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter Account Number: ");
                    String number = sc.nextLine();
                    Account acc = bank.getAccount(number);
                    if (acc != null) {
                        System.out.print("Enter amount to withdraw: ");
                        double amount = sc.nextDouble();
                        acc.withdraw(amount);
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter Account Number: ");
                    String number = sc.nextLine();
                    Account acc = bank.getAccount(number);
                    if (acc != null) {
                        acc.displayAccountInfo();
                    } else {
                        System.out.println("Account not found.");
                    }
                }
                case 5 -> bank.displayAllAccounts();
                case 6 -> {
                    System.out.println("Thank you for using the system.");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        sc.close();
    }
}
