package simpleplotter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JTable;

public class PaintPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private Color c;
	private int stroke;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(c);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(stroke));
		
		int points = table.getRowCount();
		int[] x = new int[points];
		int[] y = new int[points];
		for (int i = 0; i < table.getRowCount(); i++) {
			Object ox = table.getValueAt(i, 0);
			Object oy = table.getValueAt(i, 1);
			if ((ox == null) || (oy == null)) {
				points--;
				continue;
			}
			x[i] = Integer.valueOf((String) ox);
			y[i] = Integer.valueOf((String) oy);
		}
		g.drawPolyline(x, y, points);
	}
	
	public void setTable(JTable table) {
		this.table = table;
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
	
	public Color getColor() {
		return c;
	}
	
	public void setStroke(int stroke) {
		this.stroke = stroke;
	}
	
	public int getStroke() {
		return stroke;
	}

}
