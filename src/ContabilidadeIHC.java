import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ContabilidadeIHC extends JPanel {
	private JTable dados;
	private JScrollPane scroll;
	
	public ContabilidadeIHC() {
		String[] colunas = {"Placa", "Tipo", "Entrada", "Saida", "Valor"};
		Object[][] precursor = Sistema.getInstance().getContabilidade().getRows();
		
		dados = new JTable(precursor, colunas);
		scroll = new JScrollPane(dados);
		dados.setFillsViewportHeight(true);
		
		add(scroll);
	}
}
