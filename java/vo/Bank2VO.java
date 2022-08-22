package vo;

public class Bank2VO {
	private int bid;
	private String bname;
	private int balance;
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "Bank2 [bid=" + bid + ", bname=" + bname + ", balance=" + balance + "]";
	}
	
	
}
