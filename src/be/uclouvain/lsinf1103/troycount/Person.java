package be.uclouvain.lsinf1103.troycount;

import be.uclouvain.lsinf1103.exceptions.AlreadyExistingIDException;

import java.util.ArrayList;


public class Person implements Comparable {
    private double balance = 0;
    private int id;
    private static ArrayList<Integer> usedIDs = new ArrayList<Integer>();

    public Person(int id) {
        this.id = id;
        usedIDs.add(id);
    }

    public void balanceSub(double value) {
        this.balance = balance - value;
    }

    public void balanceAdd(double value) {
        this.balance = balance + value;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            return (this.balance == ((Person) obj).getBalance());
        }
        return false;
    }


    @Override
    public int compareTo(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Person) {
            if (this.balance > ((Person) obj).getBalance()) {
                return -1;
            } else if (this.balance < ((Person) obj).getBalance()) {
                return 1;
            } else {
                return 0;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String toString() {
        return id + ": " + balance;
    }

    public double getBalance() {
        return balance;
    }


    public int getId() {
        return id;
    }

}
