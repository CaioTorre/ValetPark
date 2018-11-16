
public class Epoch {
	private int hh, mm, ss;
	private int epoch;
	
	public Epoch(int h, int m, int s) {
		hh = h; mm = m; ss = s;
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
		hh = epoch / 3600;
	}
	
	public int deltaT(Epoch b) {
		return deltaE(b).asEpoch();
	}
	
	public Epoch deltaE(Epoch b) {
		return new Epoch(epoch - b.getEpoch());
	}
	
	public int asEpoch() {
		return ss + mm * 60 + hh * 3600;
	}
	
	public static int toEpoch(int hh, int mm, int ss) {
		return new Epoch(hh, mm, ss).asEpoch();
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
		return String.format("%02d:%02d:%02d", hh, mm, ss);
	}
	
	public boolean isValid() {
		return !(hh < 0 || mm < 0 || mm > 59 || ss < 0 || ss > 59);
	}
	
	public static Epoch parseFromString(String s) throws BadEpochStringEX {
		try {
			int hh = Integer.parseInt("" + s.charAt(0) + s.charAt(1));
			int mm = Integer.parseInt("" + s.charAt(3) + s.charAt(4));
			int ss = Integer.parseInt("" + s.charAt(6) + s.charAt(7));
			return new Epoch(hh, mm, ss);
		} catch (NumberFormatException ex) {
			throw new BadEpochStringEX();
		}
	}
}
