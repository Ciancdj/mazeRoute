package mazeRoute;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
/**
* @author Cianc
* @version 创建时间：2019年3月2日 上午10:24:37
* @ClassName 类名称
* @Description 类描述
*/
public class CircleButton extends JButton{
	public CircleButton(String label) {
		super(label);
		Dimension size = getPreferredSize();
        size.width = size.height = 20;
        setPreferredSize(size);
        setContentAreaFilled(false);
        this.setBorderPainted(false); // 不绘制边框
        this.setFocusPainted(false); // 不绘制焦点状态
	}
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
            g.setColor(Color.lightGray); // 点击时高亮
        } else {
            g.setColor(getBackground());
        }
		g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
		super.paintComponent(g);
	}
	protected void paintBorder(Graphics g) {
        g.setColor(Color.white);
        // drawOval方法画矩形的内切椭圆，但不填充。只画出一个边界
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }
	Shape shape;
	public boolean contains(int x, int y) {
		if ((shape == null) || (!shape.getBounds().equals(getBounds()))) {
			// 构造一个椭圆形对象
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
	    // 判断鼠标的x、y坐标是否落在按钮形状内。
	    return shape.contains(x, y);
	}
}
