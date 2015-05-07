package be.uclouvain.lsinf1103.troycount;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import be.uclouvain.lsinf1103.troycount.Person;

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
	public Troycount(File file) throws FileNotFoundException{

		Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));

		this.group_size = scanner.nextInt();
		int number_of_spendings = scanner.nextInt();
		this.spendings = new ArrayList<Spending>(number_of_spendings);

		int i=0;
		String line;
		while(number_of_spendings > i){

			line = scanner.nextLine();
			if (line.trim().length() > 0){
				String[] sline = line.split("\\t");
				int paid_by = Integer.parseInt(sline[0]);
				double amount = Double.parseDouble(sline[1]);

				ArrayList<Integer> paid_for_al = new ArrayList<Integer>();
				ArrayList<Double> fixed_charges_al = new ArrayList<Double>();

				for(int j=2; j<sline.length; j++){
					String [] pair = sline[j].split("/");
					paid_for_al.add(Integer.parseInt(pair[0]));
					fixed_charges_al.add(Double.parseDouble(pair[1]));
				}

				int[] paid_for = new int[paid_for_al.size()];
				double[] fixed_charges = new double[fixed_charges_al.size()];

				for(int j=0; j<paid_for.length; j++){
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
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Group Size: ").append(this.group_size).append("\n");
		sb.append("Number of transactions: \n").append(this.spendings.size()).append("\n");

		for(Spending spending : spendings){
			sb.append("\n");
			sb.append(spending);
		}
		return sb.toString();
	}


	/**
	 * Renvoie une liste de transactions permettant d'équilibrer les
	 * dépenses entre les participants.
	 * @post Renvoie une liste de transactions dont les montants ont 
	 * au plus deux chiffres après la virgule.
	 */
	public Transaction[] balance(){
		Person[] person = generateTabPerson();
		Arrays.sort(person);
		System.out.println(Arrays.toString(person));
		Transaction[] transaction = new Transaction[person.length];
		int i = 0;
		int j = person.length-1;
		int k = 0;
		while (i<person.length-2 && j>=0){
			if (person[i].getBalance()<=-person[j].getBalance()){
				transaction[k] = new Transaction(person[i].getId(),person[j].getId(),person[i].getBalance());
				person[j].charge(person[i].getBalance());
				person[i].debit(person[i].getBalance());
				i++;
				k++;
				if (person[j].getBalance()==0){
					j--;
				}
			}  else {
				transaction[k] = new Transaction(person[i].getId(),person[j].getId(),-person[j].getBalance());
				person[i].debit(-person[j].getBalance());
				person[j].charge(-person[j].getBalance());
				j--;
				k++;
				if (person[i].getBalance()==0){
					i++;
				}
			}
		}		System.out.println(Arrays.toString(transaction));
		return transaction;
	}

	public Person[] generateTabPerson(){
		Person[] tabPers = new Person[group_size];
		for (int i = 0; i < group_size; i++) {
			tabPers[i] = Person.createPerson(i+1);
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
				tabPers[person-1].charge(spendingAmount/chargedPersons);
			}
			tabPers[debitedPerson-1].debit(spendingAmount);


		}

		return tabPers;
	}

	public int get_group_size() {
		return group_size;
	}

	public static void main(String[] args) {
		try {
			Troycount troycount = new Troycount(new File("data/instance011.txt"));
			troycount.balance();





//			Person[] p = troycount.generateTabPerson();
//			System.out.println(Arrays.toString(p));
//			Arrays.sort(p);
//			System.out.println(Arrays.toString(p));
//			double sum = 0;
//			for (int i = 0; i < p.length ; i++) {
//				sum = sum + p[i].getBalance();
//			}
//			System.out.println(sum);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
