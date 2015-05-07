package be.uclouvain.lsinf1103.troycount;
import java.util.*;


public class Spending {

	private double amount;          // montant de la dépense
	private int paid_by;            // identifiant de la personne qui a payé
	private int[] paid_for;         // identifiants des personnes pour qui la dépense a été faite 
	private HashMap<Integer,Double> fixed_charges; // montant fixe dû par chaque personne [déduit du montant total]


	/**
	 *
	 * @pre {{paid_for}} != null
	 * @pre la somme des éléments de {{fixed_charges}} <= à {{amount}}
	 */

	public Spending(int paid_by, int[] paid_for, double[] fixed_charges, double amount){
		this.paid_by = paid_by;
		this.paid_for = paid_for;
		this.amount = amount;

		this.fixed_charges = new HashMap<Integer,Double>();
		for(int i=0; i<paid_for.length; i++){
			this.fixed_charges.put(paid_for[i], fixed_charges[i]);
		}
	}

	/**
	 * @post Renvoie l'identifiant de la personne qui réglé la dépense.
	 */

	public int get_paid_by(){
		return this.paid_by;
	}


	/**
	 * @post Renvoie les identifiant des personnes pour qui la dépense a été effectuée.
	 */
	public int[] get_paid_for(){
		return this.paid_for.clone();
	}

	/**
	 * @post Renvoie la part fixe de la dépense associée à l'identifiant {{identifier}}.
	 */
	public double get_fixed_charges(int identifier){
		return this.fixed_charges.get(identifier);
	}


	/**
	 * @post Renvoie le montant de la dépense.
	 */
	public double get_amount(){
		return this.amount;
	}

	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Paid by ").append(this.get_paid_by()).append("\n");
		sb.append("Amount = ").append(this.get_amount()).append("\n");
		
		for(Integer id: this.paid_for){
			sb.append("Paid for ").append(id);
			sb.append(" ( +").append(this.fixed_charges.get(id)).append(" )\n");
		}
		
		return sb.toString();
	}
	
	
}
