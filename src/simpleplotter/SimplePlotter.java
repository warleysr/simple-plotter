package simpleplotter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class SimplePlotter {
	
	public static void main(String[] args) {
		try {UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");} catch (Exception ex) {}
		
		JFrame frame = new JFrame("SimplePlotter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setResizable(true);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		
		PaintPanel pp = new PaintPanel();
		frame.setContentPane(pp);
		
		
		JFrame frame2 = new JFrame("SimplePlotter Options");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(300, 600);
		frame2.setResizable(false);
		frame2.setLayout(null);
		frame2.setLocationRelativeTo(null);
		frame2.setLocation(frame.getX() + frame.getWidth(), frame.getY());
		
		JTable tabela = new JTable(new DefaultTableModel(new Object[]{"X", "Y"}, 0));
		pp.setTable(tabela);
		
		DefaultTableModel model = (DefaultTableModel) tabela.getModel();
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				pp.repaint();
			}
		});
		
		JScrollPane scroll = new JScrollPane(tabela);
		scroll.setBounds(5, 0, 275, 450);
		
		JPanel jp = new JPanel();
		jp.setLayout(null);
		
		JButton add = new JButton("Adicionar");
		add.setBounds(30, 475, 100, 50);
		
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.addRow(new Object[]{});
			}
		});
		
		JButton rem = new JButton("Remover");
		rem.setBounds(160, 475, 100, 50);
		
		rem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selected = tabela.getSelectedRow();
				if (selected == -1) return;
				model.removeRow(selected);
				
				pp.repaint();
			}
		});
		
		JMenuBar bar = new JMenuBar();
		JMenu options = new JMenu("Opções");
		JMenuItem cor = new JMenuItem("Trocar cor da linha");
		JMenuItem esp = new JMenuItem("Trocar espessura da linha");
		JMenuItem imp = new JMenuItem("Importar pontos por texto");
		bar.add(options);
		options.add(cor);
		options.add(esp);
		options.add(imp);
		frame2.setJMenuBar(bar);
		
		cor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color c = JColorChooser.showDialog(frame2, "Escolha a cor", pp.getColor());
				if (c != null) {
					pp.setColor(c);
					pp.repaint();
				}
			}
		});
		
		esp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String res = JOptionPane.showInputDialog(frame2, "Informe a espessura da linha (1 - 20):");
				if (res == null) return;
				int i = Integer.valueOf(res);
				if (i < 1 || i > 20) {
					JOptionPane.showConfirmDialog(frame2, "A espessura deve estar entre 1 e 20.", 
							"SimplePlotter - Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
				pp.setStroke(i);
				pp.repaint();
			}
		});
		
		imp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String del = JOptionPane.showInputDialog("Informe o caractere delimitador:");
				if (del == null) return;
				if (del.length() > 1) {
					JOptionPane.showMessageDialog(frame2, "Informe apenas 1 caractere como delimitador", 
							"SimplePlotter - Erro", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JFrame impf = new JFrame("SimplePlotter - Importar pontos por texto");
				impf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				impf.setSize(500, 500);
				impf.setLayout(null);
				impf.setResizable(false);
				impf.setLocationRelativeTo(frame2);
				
				JTextArea txt = new JTextArea();
				JScrollPane scrtxt = new JScrollPane(txt);
				scrtxt.setBounds(5, 0, 475, 400);
				
				JButton impbtn = new JButton("Importar");
				impbtn.setBounds(175, 405, 150, 50);
				
				impbtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						model.setRowCount(0);
						
						String[] lines = txt.getText().split("\n");
						for (String line : lines) {
							String[] args = line.split(del);
							if (args.length != 2) continue;
							model.addRow(new Object[]{args[0].trim(), args[1].trim()});
						}
						
						impf.dispose();
					}
				});
				
				impf.add(scrtxt);
				impf.add(impbtn);
				impf.setVisible(true);
			}
		});
		
		frame2.setContentPane(jp);
		jp.add(scroll);
		jp.add(add);
		jp.add(rem);
		
		frame.setVisible(true);
		frame2.setVisible(true);
	}
}
