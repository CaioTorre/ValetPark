import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MainIHC extends JPanel implements ChangeListener {
	protected JTabbedPane pisos;
	protected JPanel big, controle;
	
	protected JButton entradaB, saidaB;
	private JFrame master, popup;
	
	private int selectedTab;
	
	public MainIHC() {
		selectedTab = 0;
		rebuildView();
	}
	
	private void rebuildView() {
		this.removeAll();
		
		pisos = new JTabbedPane();
		Piso pt = PisoT.getInstance();
		pisos.addTab("Terreo", pt.assembleVagas());
		Piso p1 = Piso1.getInstance();
		pisos.addTab("Piso 1", p1.assembleVagas());
		
		pisos.setSelectedIndex(selectedTab);
		
		pisos.addChangeListener(this);
		
		controle = new JPanel();
		controle.setLayout(new GridLayout(0, 1, 0, 5));
		
		entradaB = new JButton("Entrar novo carro");
		entradaB.addActionListener(Sistema.getInstance());
		entradaB.setMnemonic(KeyEvent.VK_E);
		entradaB.setActionCommand("inserirNew");
		entradaB.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		saidaB = new JButton("Sair carro");
		saidaB.addActionListener(Sistema.getInstance());
		saidaB.setMnemonic(KeyEvent.VK_S);
		saidaB.setActionCommand("removerNew");
		saidaB.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		
		controle.add(entradaB);
		controle.add(saidaB);
		
		big = new JPanel();
		big.add(pisos);
		big.add(controle);
		
		add(big);
	}
	
	/*public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("entrar")) {
			popup = new JFrame("Entrar novo veiculo");
			popup.setContentPane( new EntradaIHC(popup) );
	        popup.pack();
	        popup.setVisible(true);
		} else if (cmd.equals("sair")) {
			popup = new JFrame("Sair veiculo");
			popup.setContentPane( new SaidaIHC(popup));
			popup.pack();
			popup.setVisible(true);
		}
	}*/
	
	public void refreshView() {
		//Piso pt = PisoT.getInstance();
		//Piso p1 = Piso1.getInstance();
		//JPanel p = (JPanel)this.getComponent(0);
		//JTabbedPane nova = new JTabbedPane();
		//nova.addTab("Terreo", pt.assembleVagas());
		//nova.addTab("Piso 1", p1.assembleVagas());
	
		//JTabbedPane t = (JTabbedPane)p.getComponent(0);
		//t.setTabComponentAt(0, pt.assembleVagas());
		//t.setTabComponentAt(1, p1.assembleVagas());
		rebuildView();
		revalidate();
		repaint();
	}
	
	public void stateChanged(ChangeEvent e) {
		JTabbedPane pane = (JTabbedPane) e.getSource();
        System.out.println("Selected Tab: " + pane.getSelectedIndex());
        this.selectedTab = pane.getSelectedIndex();
        // Prints the string 3 times if there are 3 tabs etc
    }
}
