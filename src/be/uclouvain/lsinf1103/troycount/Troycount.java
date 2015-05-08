package be.uclouvain.lsinf1103.troycount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;


/**
 * Représente une liste de dépenses effectuées par un groupe de personnes.
 * Permet notamment de calculer les transactions à faire pour rembourser les
 * personnes qui ont avancé l'argent pour le groupe.
 */
public class Troycount {

    private int group_size;                // nombre de personnes qui participent
    private ArrayList<Spending> spendings; // liste des dépenses

    /**
     * Crée une instance du problème à partir d'un fichier respectant le format
     * décrit dans l'énoncé.
     */
    public Troycount(File file) throws FileNotFoundException {

        Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));

        this.group_size = scanner.nextInt();
        int number_of_spendings = scanner.nextInt();
        this.spendings = new ArrayList<Spending>(number_of_spendings);

        int i = 0;
        String line;
        while (number_of_spendings > i) {

            line = scanner.nextLine();
            if (line.trim().length() > 0) {
                String[] sline = line.split("\\t");
                int paid_by = Integer.parseInt(sline[0]);
                double amount = Double.parseDouble(sline[1]);

                ArrayList<Integer> paid_for_al = new ArrayList<Integer>();
                ArrayList<Double> fixed_charges_al = new ArrayList<Double>();

                for (int j = 2; j < sline.length; j++) {
                    String[] pair = sline[j].split("/");
                    paid_for_al.add(Integer.parseInt(pair[0]));
                    fixed_charges_al.add(Double.parseDouble(pair[1]));
                }

                int[] paid_for = new int[paid_for_al.size()];
                double[] fixed_charges = new double[fixed_charges_al.size()];

                for (int j = 0; j < paid_for.length; j++) {
                    paid_for[j] = paid_for_al.get(j);
                    fixed_charges[j] = fixed_charges_al.get(j);
                }

                Spending spending = new Spending(paid_by, paid_for, fixed_charges, amount);
                this.spendings.add(spending);
                i++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group Size: ").append(this.group_size).append("\n");
        sb.append("Number of transactions: \n").append(this.spendings.size()).append("\n");

        for (Spending spending : spendings) {
            sb.append("\n");
            sb.append(spending);
        }
        return sb.toString();
    }


    /**
     * Renvoie une liste de transactions permettant d'équilibrer les
     * dépenses entre les participants.
     *
     * @post Renvoie une liste de transactions dont les montants ont
     * au plus deux chiffres après la virgule.
     */
    public Transaction[] balance() {
        Person[] persons = generateTabPersons();
        Arrays.sort(persons);

        int firstSplitIndex = 0;
        int secondSplitIndex = 0;

        for (int i = 0; persons[i].getBalance() >= 0; i++) {
            secondSplitIndex++;
            if (persons[i].getBalance() > 0) {
                firstSplitIndex++;
            }
        }

        Person[] debtors = Arrays.copyOfRange(persons, 0, firstSplitIndex);
        Person[] creditors = Arrays.copyOfRange(persons, secondSplitIndex, persons.length);

        reverse(creditors);

        Stack<Transaction> transactions = new Stack<Transaction>();
        int i = 0, j = 0;

        while (i < debtors.length && j < creditors.length) {
            if (debtors[i].getBalance() <= -creditors[j].getBalance()) {
                double transactionAmount = debtors[i].getBalance();
                Transaction t = new Transaction(debtors[i].getId(),creditors[j].getId(),transactionAmount);
                transactions.add(t);
                System.out.println(t);
                creditors[j].balanceAdd(transactionAmount);
                debtors[i].balanceSub(transactionAmount);
                i++;
            } else {
                double transactionAmount = -creditors[j].getBalance();
                Transaction t = new Transaction(debtors[i].getId(),creditors[j].getId(),transactionAmount);
                transactions.add(t);
               System.out.println(t);
                creditors[j].balanceAdd(transactionAmount);
                debtors[j].balanceSub(transactionAmount);
                j++;
            }

        }
        return transactions.toArray(new Transaction[transactions.size()]);
    }

    public static Object[] reverse(Object[] o) {
        for (int i = 0; i < o.length / 2; i++) {
            Object temp = o[i];
            o[i] = o[o.length - 1 - i];
            o[o.length - 1 - i] = temp;
        }
        return o;
    }


    public Person[] generateTabPersons() {
        Person[] persons = new Person[group_size];
        for (int i = 0; i < group_size; i++) {
            int personID = i + 1;
            persons[i] = new Person(personID);
        }
        for (Spending aSpending : spendings) {
            int debitedPerson = aSpending.get_paid_by();
            int[] chargedPerson = aSpending.get_paid_for();
            int chargedPersons = chargedPerson.length;
            double spendingAmount = aSpending.get_amount();

            for (int person : chargedPerson) {
                double fixedCharge = aSpending.get_fixed_charges(person);
                spendingAmount -= fixedCharge;
            }

            for (int person : chargedPerson) {
                persons[person - 1].balanceAdd(spendingAmount / chargedPersons);
            }
            persons[debitedPerson - 1].balanceSub(spendingAmount);
        }
        return persons;
    }

    public int get_group_size() {
        return group_size;
    }

}
