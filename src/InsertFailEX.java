
public class InsertFailEX extends Exception {
	private String cause;
	public InsertFailEX(String cause) {
		this.cause = cause;
	}
	public String toString() { return this.cause; }
}
