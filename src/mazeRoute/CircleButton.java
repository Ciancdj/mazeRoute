package mazeRoute;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
/**
* @author Cianc
* @version ����ʱ�䣺2019��3��2�� ����10:24:37
* @ClassName ������
* @Description ������
*/
public class CircleButton extends JButton{
	public CircleButton(String label) {
		super(label);
		Dimension size = getPreferredSize();
        size.width = size.height = 20;
        setPreferredSize(size);
        setContentAreaFilled(false);
        this.setBorderPainted(false); // �����Ʊ߿�
        this.setFocusPainted(false); // �����ƽ���״̬
	}
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
            g.setColor(Color.lightGray); // ���ʱ����
        } else {
            g.setColor(getBackground());
        }
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
		super.paintComponent(g);
	}
	protected void paintBorder(Graphics g) {
        g.setColor(Color.white);
        // drawOval���������ε�������Բ��������䡣ֻ����һ���߽�
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }
	Shape shape;
	public boolean contains(int x, int y) {
		if ((shape == null) || (!shape.getBounds().equals(getBounds()))) {
			// ����һ����Բ�ζ���
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
	    // �ж�����x��y�����Ƿ����ڰ�ť��״�ڡ�
	    return shape.contains(x, y);
	}
}
