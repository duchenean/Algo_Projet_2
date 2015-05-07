package be.uclouvain.lsinf1103.troycount;

import be.uclouvain.lsinf1103.exceptions.AlreadyExistingIDException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Antoine on 07/05/2015.
 */
public class Person implements Comparable {
    private double balance = 0;
    private int id;
    private static ArrayList<Integer> usedIDs = new ArrayList<Integer>();

    private Person(int id) {
        this.id=id;
        usedIDs.add(id);
    }

    public static Person createPerson(int id) {
        if (!usedIDs.contains(id)){
            return new Person(id);
        } else {
            throw new AlreadyExistingIDException(id);
        }
    }

    public void debit(double value){
        this.balance = balance-value;
    }

    public void charge(double value){
        this.balance = balance+value;
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
        if (obj == null){
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

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void main(String[] args) {
        Person a = createPerson(1);
        Person b = createPerson(1);
        Person c = createPerson(3);

        a.setBalance(25);
        b.setBalance(75);
        c.setBalance(75);

        Person[] tab = {a,b,c};

        Arrays.sort(tab);

        System.out.println(Arrays.toString(tab));

    }
}
