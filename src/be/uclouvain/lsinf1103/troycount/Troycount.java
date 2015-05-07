package be.uclouvain.lsinf1103.troycount;

import java.io.*;
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

		// À compléter...
		return null;

	}

	public Person[] generateTabPerson(){
		Person[] tab = new Person[group_size];
		for (int i = 1; i < group_size; i++) {
			tab[i] = Person.createPerson(i);
		}
		for (Iterator<Spending> spendingIterator = spendings.iterator(); spendingIterator.hasNext(); ) {
			Spending next =  spendingIterator.next();
			System.out.println(next);
		}
		return tab;
	}

	public int get_group_size() {
		return group_size;
	}

	public static void main(String[] args) {
		try {
			Troycount troycount = new Troycount(new File("data/instance000.txt"));
			troycount.generateTabPerson();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
