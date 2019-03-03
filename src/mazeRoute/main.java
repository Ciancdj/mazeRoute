package mazeRoute;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import mazeRoute.CircleButton;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.BadLocationException;
/**
* @author Cianc
* @version ����ʱ�䣺2019��3��2�� ����8:56:35
* @ClassName ������
* @Description ������
*/

class inforSave {
	// ��ʼx����
	public static int startX = 0;
	// ��ʼy����
	public static int startY = 0;
	// ����x����
	public static int endX = 0;
	// ����y���� 
	public static int endY = 0;
	// ���̵�����
	public static int row = 0;
	// ���̵�����
	public static int col = 0;
	// ���̹���
	public static Integer[][] chess;
	// DP����
	public static Integer[][] dp;
	// ���̿��
	public static int chessWeight;
	// ���̸߶�
	public static int chessHeight;
}

class Pair{
	int x,y;
	public Pair(int x,int y) {
		this.x = x;
		this.y = y;
	}
}

class Frame extends JFrame {
	// ������
	private JPanel mainPanel = new JPanel();
	// ǽ�ڶ���
	static Queue<Pair> mazequeue = new LinkedList<Pair>();
	// ǽ�ڶ���
	static Queue<Pair> wallqueue = new LinkedList<Pair>();
	// �ܸ߶�
	int weight = 800;
	// �ܿ��
	int height = 600;
	private int []nextX = { 0, -1, 1, 0};
	private int []nextY = {-1,  0, 0, 1};
	public Frame() {
		super("mazeRoute");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(550, 200, weight, height);
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.WHITE);
		interInforView();
		Container c = this.getContentPane();
		c.add(mainPanel);
		this.setVisible(true);
	}
	
	private void init(int row, int col, int sx, int sy, int ex, int ey) {
		inforSave.startX = sx;
		inforSave.startY = sy;
		inforSave.endX = ex;
		inforSave.endY = ey;
		inforSave.col = col;
		inforSave.row = row;
		inforSave.chess = new Integer[row][col];
		inforSave.dp = new Integer[row][col];
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				inforSave.dp[i][j] = -1;
			}
		}
		inforSave.dp[sy][sx] = 0;
	}
	
	private void interInforView() {
		mainPanel.removeAll();
		JPanel inforView = new JPanel();
		inforView.setBackground(Color.WHITE);
		inforView.setBounds(0, 0, weight, height);
		JLabel[] textLabel = {
				new JLabel("������������� ��"), new JLabel("������������� ��"),
				new JLabel("���������xֵ ��"), new JLabel("���������yֵ ��"),
				new JLabel("���������xֵ ��"), new JLabel("���������yֵ ��"),
		};
		TextField[] interField = {
				new TextField(10), new TextField(10),
				new TextField(10), new TextField(10),
				new TextField(10), new TextField(10),
		};
		JButton []button = {
			new JButton("��ʼ"), new CircleButton("1"),
			new CircleButton("2"), new CircleButton("3")
		};
		JTextArea text = new JTextArea();
		JLabel buttonBorder = new JLabel();
		text.setBounds(15, 150, 530, 400);
		buttonBorder.setBounds(545, 430, 220, 100);
		buttonBorder.setBorder(BorderFactory.createTitledBorder("�����Բ�ΰ�ťΪ����"));
		text.setBorder(BorderFactory.createTitledBorder("�����·������Թ� ( 0��1�ֱ��ʾ�Թ��е�ͨ·���ϰ� )"));
		text.setFont(new Font("����",Font.CENTER_BASELINE,20));
		button[0].setBounds(550, 25, 200, 400);button[1].setBounds(550, 450, 66, 66);
		button[2].setBounds(617, 450, 66, 66);button[3].setBounds(684, 450, 66, 66);
		textLabel[0].setBounds(15, 25, 180, 25);interField[0].setBounds(195, 25, 50, 25);
		textLabel[1].setBounds(300, 25, 180, 25);interField[1].setBounds(480, 25, 50, 25);
		textLabel[2].setBounds(15, 70, 180, 25);interField[2].setBounds(195, 70, 50, 25);
		textLabel[3].setBounds(300, 70, 180, 25);interField[3].setBounds(480, 70, 50, 25);
		textLabel[4].setBounds(15, 115, 180, 25);interField[4].setBounds(195, 115, 50, 25);
		textLabel[5].setBounds(300, 115, 180, 25);interField[5].setBounds(480, 115, 50, 25);
		for(int index = 0; index < textLabel.length; index++) {
			textLabel[index].setFont(new Font("����",Font.CENTER_BASELINE,20));
			interField[index].setFont(new Font("����",Font.CENTER_BASELINE,20));
			inforView.add(textLabel[index]);
			inforView.add(interField[index]);
		}
		for(int index = 0; index < button.length; index++) {
			if(index >= 1) {
				button[index].setBackground(Color.PINK);
			}
			button[index].setFont(new Font("����",Font.CENTER_BASELINE,20));
			inforView.add(button[index]);
		}
		inforView.add(text);
		inforView.add(buttonBorder);
		inforView.setLayout(null);
		mainPanel.add(inforView);
		mainPanel.updateUI();
		button[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				init(
						Integer.parseInt(interField[0].getText()), Integer.parseInt(interField[1].getText()),
						Integer.parseInt(interField[2].getText()),Integer.parseInt(interField[3].getText()),
						Integer.parseInt(interField[4].getText()),Integer.parseInt(interField[5].getText())
				);
				char[] str = text.getText().replaceAll("\n", "").replaceAll(" ", "").toCharArray();
				int x = 0,y = 0;
				for(int index = 0; index < str.length; index++) {
					inforSave.chess[x][y] = str[index] - '0';
					if(inforSave.chess[x][y] == 1) {
						wallqueue.offer(new Pair(y,x));
					}
					y = y + 1;
					if((y % inforSave.col) == 0) {
						y=0;
						x++;
					}
				}
				calDp();
				finOutput();
			}
		});
		button[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interField[0].setText("10");
				interField[1].setText("10");
				interField[2].setText("1");
				interField[3].setText("0");
				interField[4].setText("8");
				interField[5].setText("9");
				text.setText(
						  "1 0 1 1 1 1 1 1 0 1\n"
						+ "0 0 0 0 0 0 1 0 0 1\n"
						+ "0 1 0 1 1 0 1 1 0 1\n"
						+ "0 1 0 0 0 0 0 0 0 0\n"
						+ "1 1 0 1 1 0 1 1 1 1\n"
						+ "0 0 0 0 1 0 0 0 0 1\n"
						+ "0 1 1 1 1 1 1 1 0 1\n"
						+ "0 0 0 0 1 0 0 0 0 0\n"
						+ "0 1 1 1 1 0 1 1 1 0\n"
						+ "0 0 0 0 1 0 0 0 0 1\n");
			}
		});
		button[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interField[0].setText("10");
				interField[1].setText("10");
				interField[2].setText("1");
				interField[3].setText("1");
				interField[4].setText("8");
				interField[5].setText("1");
				text.setText(
						  "1 1 1 1 1 1 1 1 1 1\n"
						+ "1 0 0 1 0 0 0 1 0 1\n"
						+ "1 0 0 1 0 0 0 1 0 1\n"
						+ "1 0 0 0 0 1 0 0 0 1\n"
						+ "1 0 1 1 1 0 0 0 0 1\n"
						+ "1 0 0 0 1 0 0 0 0 1\n"
						+ "1 0 1 0 0 0 1 0 0 1\n"
						+ "1 0 1 1 1 0 1 1 0 1\n"
						+ "1 1 1 0 0 0 0 0 0 1\n"
						+ "1 1 1 1 1 1 1 1 1 1\n");
			}
		});
		button[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interField[0].setText("5");
				interField[1].setText("5");
				interField[2].setText("0");
				interField[3].setText("0");
				interField[4].setText("2");
				interField[5].setText("2");
				text.setText(
						  "0 0 0 0 0\n"
						+ "0 1 1 1 0\n"
						+ "0 1 0 1 1\n"
						+ "0 1 1 1 0\n"
						+ "0 0 0 0 0\n");
			}
		});
	}
	
	private void calDp() {
		Queue<Pair> queue = new LinkedList<Pair>();
		Pair pair = new Pair(inforSave.startX, inforSave.startY);
		queue.offer(pair);
		while(!queue.isEmpty()) {
			pair = queue.poll();
			for(int index = 0; index < 4; index++) {
				if((pair.x + nextX[index] >= 0 && pair.x + nextX[index] < inforSave.col) && 
						(pair.y + nextY[index] >= 0 && pair.y + nextY[index] < inforSave.row)) {
					if(inforSave.chess[pair.y + nextY[index]][pair.x + nextX[index]] == 0 && 
							inforSave.dp[pair.y + nextY[index]][pair.x + nextX[index]] == -1)
						queue.offer(new Pair(pair.x + nextX[index], pair.y + nextY[index]));
					else if(inforSave.chess[pair.y + nextY[index]][pair.x + nextX[index]] == 0 &&
							inforSave.dp[pair.y + nextY[index]][pair.x + nextX[index]] != -1)
						 	//max(inforSave.dp[pair.x][pair.y], (inforSave.dp[pair.x + i][pair.y + j] + 1))
						if(inforSave.dp[pair.y][pair.x] == -1)
							inforSave.dp[pair.y][pair.x] = (inforSave.dp[pair.y + nextY[index]][pair.x + nextX[index]] + 1);
						else
							inforSave.dp[pair.y][pair.x] = inforSave.dp[pair.y][pair.x] < (inforSave.dp[pair.y + nextY[index]][pair.x + nextX[index]] + 1) 
								? inforSave.dp[pair.y][pair.x] : (inforSave.dp[pair.y + nextY[index]][pair.x + nextX[index]] + 1);
				}
			}
		}
	}
	
	private void finOutput() {
		mainPanel.removeAll();
		JPanel finView = new JPanel();
		finView.setBackground(Color.WHITE);
		finView.setLayout(null);
		finView.setBounds(0, 0, 800, 600);
		inforSave.chessHeight = (460 / inforSave.row);
		inforSave.chessWeight = (760 / inforSave.col);
		JLabel label = new JLabel("  ���Թ����·��Ϊ" + inforSave.dp[inforSave.endY][inforSave.endX]);
		label.setBounds(70, 485, 300, 50);
		label.setFont(new Font("����",Font.CENTER_BASELINE,20));
		label.setBorder(BorderFactory.createTitledBorder("���"));
		JButton button = new JButton("����");
		button.setBounds(500, 480, 200, 70);
		finView.add(label);
		Draw draw = new Draw();
		finView.add(draw);
		finView.add(button);
		try {
			if(inforSave.dp[inforSave.endY][inforSave.endX] == -1) {
				throw new noRouteError();
			}
			Pair pair = new Pair(inforSave.endX, inforSave.endY);
			while(pair.x != inforSave.startX || pair.y != inforSave.startY) {
				int min = 10000000;
				int minx = 0, miny = 0;
				for(int index = 0; index < 4; index++) {
					if (((pair.y + nextY[index]) < 0 || (pair.y + nextY[index]) >= inforSave.row) ||
							((pair.x + nextX[index]) < 0 || (pair.x + nextX[index]) >= inforSave.col))
						continue;
					if (inforSave.chess[pair.y + nextY[index]][pair.x + nextX[index]] == 1)
						continue;
					if(min > inforSave.dp[pair.y + nextY[index]][pair.x + nextX[index]]) {
						min = inforSave.dp[pair.y + nextY[index]][pair.x + nextX[index]];
						minx = pair.x + nextX[index];
						miny = pair.y + nextY[index]; 
					}
				}
				pair = new Pair(minx, miny);
				mazequeue.add(pair);
			}
		} catch(noRouteError e) {
			JOptionPane.showMessageDialog(null, "û���ҵ���ִ�е�·��");
		}
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interInforView();
			}
		});
		mainPanel.add(finView);
		mainPanel.updateUI();
	}
}

class Draw extends JPanel{
	public Draw() {
		this.setLayout(null);
		this.setBackground(Color.WHITE);
		this.setBounds(10, 10, 760, 460);
		this.setBorder(BorderFactory.createEtchedBorder());
		if(inforSave.dp[inforSave.endY][inforSave.endX] != -1) {
			this.repaint();
		}
	}
	
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// ����ǽ��
		g.setColor(Color.BLACK);
		Pair tem = null;
		while(!Frame.wallqueue.isEmpty()) {
			tem = Frame.wallqueue.poll();
			g.fillRect(tem.x * (inforSave.chessWeight), tem.y * (inforSave.chessHeight),
					(inforSave.chessWeight), (inforSave.chessHeight));
		}
		// ����·��
		g.setColor(Color.RED);
		while(!Frame.mazequeue.isEmpty()) {
			tem = Frame.mazequeue.poll();
			g.fillRect(tem.x * (inforSave.chessWeight), tem.y * (inforSave.chessHeight),
					(inforSave.chessWeight), (inforSave.chessHeight));
		}
		// ���ƿ�ʼ�ͽ����ڵ�
		g.setColor(Color.GREEN);
		g.fillRect(inforSave.startX * (inforSave.chessWeight), inforSave.startY * (inforSave.chessHeight),
				(inforSave.chessWeight), (inforSave.chessHeight));
		g.fillRect(inforSave.endX * (inforSave.chessWeight), inforSave.endY * (inforSave.chessHeight),
				(inforSave.chessWeight), (inforSave.chessHeight));
	}
}

class noRouteError extends Exception{
	public noRouteError() {
		super("û���ҵ���ִ�е�·��");
	}
}

public class main {
	public static void main(String[] args) {
		Frame frame = new Frame();
	}
}
