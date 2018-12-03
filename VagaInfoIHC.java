import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VagaInfoIHC extends JPanel implements ActionListener{
	private JTextField fieldPlaca, fieldHorario, fieldTipo;
	private JButton bSair, bAction;
	private JPanel big;
	
	private JFrame control;
	private JFrame popup;
	
	private int id;
	private int tipo;
	private int andar;
	
	private VagaData vaga;
	//private MainIHC ihc_pai;
	
	public VagaInfoIHC(JFrame f, VagaData vaga) {
		//ihc_pai = m;
		buildIHC(f, vaga);
		this.vaga = vaga;
		this.andar = vaga.getAndar();
	}
	
	private JLabel labelMaker(String s) {
		JLabel x = new JLabel(s);
		x.setAlignmentX(CENTER_ALIGNMENT);
		return x;
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		String cmd = a.getActionCommand();
		if (cmd.equals("inserir")) {
			popup = new JFrame("Entrar novo veiculo");
			popup.setContentPane( new EntradaIHC(popup, id, andar, tipo, this) );
	        popup.pack();
	        popup.setVisible(true);
		} else if (cmd.equals("cancelar")) {
			control.dispatchEvent(new WindowEvent(control, WindowEvent.WINDOW_CLOSING));
		} else if (cmd.equals("remover")) {
			popup = new JFrame("Sair veiculo");
			popup.setContentPane( new SaidaIHC(popup, fieldPlaca.getText(), this) );
			popup.pack();
			popup.setVisible(true);
		}
	}
	
	private void buildIHC(JFrame f, VagaData vaga) {
		this.removeAll();
		//Font bigFont = new Font("FE-Schrift", Font.PLAIN, 42);
		boolean ocupada = vaga.getOcupado();
		
		big = new JPanel();
		big.setLayout(new BoxLayout(big, BoxLayout.Y_AXIS));
		big.setOpaque(true);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		control = f;
		
		bSair = new JButton("Cancelar");
		bSair.addActionListener(this);
		bSair.setActionCommand("cancelar");
		buttons.add(bSair);
		buttons.add(Box.createHorizontalGlue());
		if (ocupada) {
			big.setBackground(Facade.colorOcupado);
			big.add(Box.createRigidArea(new Dimension(0, 50)));
			big.add(labelMaker("Vaga ocupada"));
			
			big.add(Box.createRigidArea(new Dimension(0, 50)));
			big.add(labelMaker("Placa do veiculo"));
			fieldPlaca = new JTextField(vaga.getVeiculo().getPlaca());
			fieldPlaca.setEditable(false);
			fieldPlaca.setHorizontalAlignment(JTextField.CENTER);
			fieldPlaca.setFont(Facade.bigFont);
			big.add(fieldPlaca);
			
			big.add(Box.createRigidArea(new Dimension(0, 50)));
			big.add(labelMaker("Horario de entrada"));
			fieldHorario = new JTextField(vaga.getVeiculo().getEpoch().toString());
			fieldHorario.setEditable(false);
			fieldHorario.setHorizontalAlignment(JTextField.CENTER);
			fieldHorario.setFont(Facade.bigFont);
			big.add(fieldHorario);
			
			big.add(Box.createRigidArea(new Dimension(0, 50)));
			big.add(labelMaker("Tipo de veiculo"));
			fieldTipo = new JTextField(vaga.getVeiculo().getTipoString());
			fieldTipo.setEditable(false);
			fieldTipo.setHorizontalAlignment(JTextField.CENTER);
			fieldTipo.setFont(Facade.bigFont);
			big.add(fieldTipo);

			bAction = new JButton("Remover veiculo");
			bAction.addActionListener(this);
			bAction.setActionCommand("remover");
		} else {
			big.setBackground(Facade.colorLivre);
			big.add(Box.createRigidArea(new Dimension(0, 200)));
			JLabel temp = labelMaker("Vaga livre");
			temp.setFont(Facade.bigFont);
			big.add(temp);
			bAction = new JButton("Inserir veiculo");
			bAction.addActionListener(this);
			bAction.setActionCommand("inserir");
		}
		buttons.add(bAction);
		
		big.add(Box.createRigidArea(new Dimension(0, 50)));
		big.add(buttons);
		
		add(big);
		
		id = vaga.getID();
		tipo = vaga.getTipo();
		f.pack();
		revalidate();
		repaint();
	}
	
	public void rebuildIHC() {
		buildIHC(control, vaga);
		//System.out.printf("Rebuilding for %d, empty = %d, placa = %s", vaga.getID(), vaga.getOcupado() ? 0 : 1, vaga.getVeiculo().getPlaca());
	}
}
