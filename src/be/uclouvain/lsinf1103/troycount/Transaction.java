package be.uclouvain.lsinf1103.troycount;

public class Transaction {
	
	public final int from;
	public final int to;
	public final double amount;
	
	public Transaction(int from, int to, double amount){
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	@Override
	public String toString(){
		return "Transaction of " + amount + " from " + from + " to " + to + ".";
	}

}
