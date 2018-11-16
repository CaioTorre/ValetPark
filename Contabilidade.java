import java.util.ArrayList;

public class Contabilidade {
	private static Contabilidade self;
	private Contabilidade() {
		
	}
	public static Contabilidade getInstance() {
		if (self == null) self = new Contabilidade();
		return self;
	}
	
	private ArrayList<ContabLog> log = new ArrayList<ContabLog>();
	public void insertLog(ContabLog l) { log.add(l); }
	public ContabLog getLogIndexed(int index) { return log.get(index); }
}
