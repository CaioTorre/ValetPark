import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ContabilidadeIHC extends JPanel {
	private JTable dados;
	private JScrollPane scroll;
	private JLabel veiculos, liquido;
	
	public ContabilidadeIHC() {
		Font bigFont = new Font("FE-Schrift", Font.PLAIN, 18);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		String[] colunas = {"Placa", "Tipo", "Entrada", "Saida", "Valor"};
		Object[][] precursor = Sistema.getInstance().getContabilidade().getRows();
		
		dados = new JTable(precursor, colunas);
		scroll = new JScrollPane(dados);
		dados.setFillsViewportHeight(true);
		
		veiculos = new JLabel(String.format("Veiculos: %d", precursor.length));
		veiculos.setFont(bigFont);
		
		float renda = (float)0.0;
		for (Object[] r : precursor) {
			renda += (float)r[4];
			r[4] = String.format("R$%.2f", r[4]);
		}
		liquido = new JLabel(String.format("Liquido: R$%.2f", renda));
		liquido.setFont(bigFont);
		
		JPanel texts = new JPanel();
		texts.setLayout(new BoxLayout(texts, BoxLayout.X_AXIS));
		texts.add(Box.createRigidArea(new Dimension(50, 0)));
		texts.add(veiculos);
		texts.add(Box.createHorizontalGlue());
		texts.add(liquido);
		texts.add(Box.createRigidArea(new Dimension(50, 0)));
		
		add(scroll);
		add(Box.createRigidArea(new Dimension(0, 30)));
		add(texts);
		add(Box.createRigidArea(new Dimension(0, 30)));
	}
}
