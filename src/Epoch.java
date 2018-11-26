
public class Epoch {
	private int d, m, hh, mm, ss;
	private int epoch;
	
	public static final String format = "##/##-##:##:##";
	
	public Epoch(int dia, int mes, int h, int m, int s) {
		hh = h; mm = m; ss = s;
		this.d = dia;
		this.m = mes;
		epoch = asEpoch();
	}
	
	public Epoch(int e) { 
		epoch = e;
		updateTime();
	}
	
	private void updateEpoch() {
		epoch = asEpoch();
	}
	
	private void updateTime() {
		ss = epoch % 60;
		mm = (epoch / 60) % 60;
		hh = (epoch / 3600) % 86400;
		d = (epoch / 86400) % 2678400;
		m = (epoch / 2678400);
	}
	
	public int deltaT(Epoch b) {
		return deltaE(b).asEpoch();
	}
	
	public Epoch deltaE(Epoch b) {
		return new Epoch(epoch - b.getEpoch());
	}
	
	public int asEpoch() {
		return ss + mm * 60 + hh * 3600 + d * 86400 + m * 2678400;
	}
	
	public static int toEpoch(int dia, int mes, int hh, int mm, int ss) {
		return new Epoch(dia, mes, hh, mm, ss).asEpoch();
	}

	public int getHh() {
		return hh;
	}

	public void setHh(int hh) {
		this.hh = hh;
		updateEpoch();
	}

	public int getMm() {
		return mm;
	}

	public void setMm(int mm) {
		this.mm = mm;
		updateEpoch();
	}

	public int getSs() {
		return ss;
	}

	public void setSs(int ss) {
		this.ss = ss;
		updateEpoch();
	}

	public int getEpoch() {
		return epoch;
	}

	public void setEpoch(int epoch) {
		this.epoch = epoch;
		updateTime();
	}
	
	public String toString() {
		return String.format("%02d/%02d-%02d:%02d:%02d", d + 1, m + 1, hh, mm, ss);
	}
	
	public boolean isValid() {
		boolean primer = !(hh < 0 || mm < 0 || mm > 59 || ss < 0 || ss > 59 || d < 0 || m < 0 || m > 11);
		if (!primer) return false;
		System.err.println("Passed primer");
		if ((m == 0 || m == 2 || m == 4 || m == 6 || m == 7 || m == 9 || m == 11) && (d > 30)) return false;
		if ((m == 1) && (d > 27)) return false;
		if (d > 29) return false;
		return true;
	}
	
	public static Epoch parseFromString(String s) throws BadEpochStringEX {
		try {
			int dia = Integer.parseInt("" + s.charAt(0)  + s.charAt(1) ) - 1;
			int mes = Integer.parseInt("" + s.charAt(3)  + s.charAt(4) ) - 1;
			if (dia < 1 || mes < 1) throw new BadEpochStringEX();
			int hh = Integer.parseInt(""  + s.charAt(6)  + s.charAt(7) );
			int mm = Integer.parseInt(""  + s.charAt(9)  + s.charAt(10));
			int ss = Integer.parseInt(""  + s.charAt(12) + s.charAt(13));
			Epoch e = new Epoch(dia, mes, hh, mm, ss);
			if (!e.isValid()) throw new BadEpochStringEX();
			System.err.println("Created epoch: " + e.toString());
			return e;
		} catch (NumberFormatException ex) {
			throw new BadEpochStringEX();
		} catch (BadEpochStringEX ex) {
			throw ex;
		}
	}
}
