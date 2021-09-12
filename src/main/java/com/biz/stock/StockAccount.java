package com.biz.stock;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class StockAccount {

    String name;	//	name of the stock
    int cash;	//	cash
    int count;
    int total;	//	total value
    CompanyShares[] companyShares;	//	company shares class array
    MyStack<String> transactionsStack;	//	stack
    MyQueue<String> transactionsQueue;	//	queue

    public static void main(String[] args) {
        StockAccount stockAccount = new StockAccount("hello");	//	class object
        stockAccount.addOrRemoveStock();
    }

     //asks if user wants to add or remove stocks and gets values
     //else prints the details

    void addOrRemoveStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type 'buy' to buy stock or type 'sell' to sell stock: ");
        String action = scanner.next();
        if(action.equals("buy")) {
            System.out.print("Enter amount, symbol, price of the stock: ");
            int amount = scanner.nextInt();
            String symbol = scanner.next();
            int price = scanner.nextInt();
            buy(amount, symbol, price);
            addOrRemoveStock();
        } else if(action.equals("sell")) {
            System.out.print("Enter amount, symbol, price of the stock: ");
            int amount = scanner.nextInt();
            String symbol = scanner.next();
            int price = scanner.nextInt();
            sell(amount, symbol, price);
            addOrRemoveStock();
        }
        else {
            printReport();
            System.out.println("\nTotal stock value: " + valueOf());
            printTransactionStack();
            printTransactionQueue();
            writeToFile();
            scanner.close();
        }
    }


    public StockAccount(String fileName) {
        transactionsStack = new MyStack<String>();
        transactionsQueue = new MyQueue<String>();
        getFiledata();
    }

     //reads file data and puts into the array

    private void getFiledata() {
        FileReader reader = null;
        try {
            reader = new FileReader("D:/stockaccount.txt");
            //	buffered reader to read the file
            BufferedReader bufferedReader = new BufferedReader(reader);
            name = bufferedReader.readLine();
            cash = Integer.parseInt(bufferedReader.readLine());
            count = Integer.parseInt(bufferedReader.readLine());
            companyShares = new CompanyShares[10];
            for (int i = 0; i < count; i++) {
                String line = bufferedReader.readLine();
                String[] lines = line.split(" ");
                String symbol = lines[0];
                int numberOfShares = Integer.parseInt(lines[1]);
                int price = Integer.parseInt(lines[2]);
                String dateTime = lines[3];
                companyShares[i] = new CompanyShares(symbol, numberOfShares, price, dateTime);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


     //returns total value of shares

    public int valueOf() {
        total = cash;
        for (int i = 0; i < count; i++) {
            total += companyShares[i].getValue();
        }
        return total;
    }


   //prints detailed report

    public void printReport() {
        System.out.println("\n" + name + "\n");
        System.out.println("Symbol\tNo. of Shares\tPrice\tValue\tDate");
        for (int i = 0; i < count; i++) {
            CompanyShares shares = companyShares[i];
            System.out.println(shares.getSymbol() + "\t" + shares.getNumberOfShares() + "\t\t" + shares.getPrice()
                    + "\t" + shares.getValue() + "\t" + shares.getDateTime());
        }
    }


     //buys stocks of given symbol

    public void buy(int amount, String symbol, int price) {
        String dateTime = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        CompanyShares companyShareObject = new CompanyShares(symbol, amount / price, price, dateTime);
        companyShares[count] = companyShareObject;
        count++;
        transactionsStack.push("Purchased");
        transactionsQueue.enqueue(dateTime);
    }

    // sells stocks of given symbol
    public void sell(int amount, String symbol, int price) {
        int numberOfShares = amount / price;
        String dateTime = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        transactionsStack.push("Sold");
        transactionsQueue.enqueue(dateTime);
        for (int i = 0; i < count; i++) {
            if (companyShares[i].getSymbol().equals(symbol)) {
                companyShares[i].setNumberOfShares(companyShares[i].getNumberOfShares() - numberOfShares);
                companyShares[i].setDateTime(dateTime);
                break;
            }
        }
    }

     //prints queue
    void printTransactionQueue() {
        System.out.println("\nTransactions Queue:");
        while (!transactionsQueue.isEmpty()) {
            System.out.println(transactionsQueue.dequeue());
        }
    }

    // prints stack
    void printTransactionStack() {
        System.out.println("\nTransactions Stack:");
        while (!transactionsStack.isEmpty()) {
            System.out.println(transactionsStack.pop());
        }
    }

     //updates file
    void writeToFile() {
        try {
            PrintWriter writer = new PrintWriter("D:/stockaccount.txt");
            writer.write(name + "\n" + cash + "\n" + count + "\n");
            for (int i = 0; i < count; i++) {
                CompanyShares share = companyShares[i];
                writer.write(share.getSymbol() + " " + share.getNumberOfShares()
                        + " " + share.getPrice() + " " + share.getDateTime() + "\n");
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}