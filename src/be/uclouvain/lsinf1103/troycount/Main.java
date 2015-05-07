package be.uclouvain.lsinf1103.troycount;
import java.io.*;

/**
 * Classe contenant la main permettant de lire les fichiers contenant la 
 * description des comptes passés en arguments, d'effectuer la balance et 
 * d'écrire à la sortie standard la liste des remboursements à effectuer
 * pour chacun de ces comptes.
 */
public class Main {
		
	public static void main(String... args) throws IOException{

		for(String filename: args){
			File f = new File(filename);
			Troycount count = new Troycount(f);
			Transaction[] transactions = count.balance();
			
			System.out.println("File : " + f.getName());
			for(Transaction t: transactions) System.out.println(t);
			System.out.println();
		}
	}
}