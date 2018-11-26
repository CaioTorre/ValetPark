import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Facade {
	public static final Color colorOcupado = Color.getHSBColor((float)(353.0/360.0), (float)0.69, (float)1.0);
	public static final Color colorLivre   = Color.getHSBColor((float)(140.0/360.0), (float)0.69, (float)1.0);
	public static final Font bigFont = new Font("FE-Schrift", Font.PLAIN, 42);
	
	private Sistema s;
	private ArrayList<JFrame> popupList = new ArrayList<JFrame>();
	
	private static Facade self;
	public static Facade getInstance() {
		if (self == null) self = new Facade();
		return self;
	}
	
	private Facade() {
		this.s = Sistema.getInstance();
	}
	
	public int insereVeiculo(String placa, String ep_str, int tipo_veiculo) throws HoraInvalidaEX, BadEpochStringEX, InsertFailEX {
		return s.entraCarro(placa, ep_str, tipo_veiculo);
	}
	
	public int insereVeiculo(String placa, String ep_str, int tipo_veiculo, int vaga) throws HoraInvalidaEX, BadEpochStringEX, InsertFailEX, VagaInvalidaEX {
		return s.entraCarro(placa, ep_str, tipo_veiculo, vaga);
	}
	
	public void removeVeiculo(String placa, String ep_str) throws BadEpochStringEX, HoraInvalidaEX, DeltaTInvalidoEX, PlacaNNEncontradaEX {
		Epoch ep = Epoch.parseFromString(ep_str);
		s.saiCarro(placa, ep);
	}
	
	public void refreshMainIHC() {
		s.refreshMain();
	}
	
	public Object[][] getContabilidadePrecursor() {
		return s.getContabilidade().getRows();
	}
	
	/*public void addNewPopup(JFrame jf) {
		popupList.add(jf);
		s.setAllStatesMainIHC(false);
	}
	
	public void removePopup(int index) {
		popupList.remove(index);
		if (popupList.size() == 0) s.setAllStatesMainIHC(true);
	}*/
}
