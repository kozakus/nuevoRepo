import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class menu extends JFrame implements ActionListener {

	private JPanel contentPane;
	
	private JMenuItem mnt800;
	private JMenuItem mnt1024;
	private JMenuItem mntColor;
	private JMenuItem mntOriginal;
	private JMenuItem mntCustom;
	
	private JMenuItem mntSalir;
	private JTextField txtAlto;
	private JTextField txtAncho;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menu frame = new menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 506, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntNuevo = new JMenuItem("Nuevo");
		mnFile.add(mntNuevo);
		
		mntSalir = new JMenuItem("Salir");
		mntSalir.addActionListener(this);
		mnFile.add(mntSalir);
		
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		//hay que añadir el elemento que sea escuchado con el addactionlistener(this)
		mntColor = new JMenuItem("Cambiar color", new ImageIcon("assets/color.png"));
		//mntColor = new JMenuItem("Cambiar Color");
		mnOptions.add(mntColor);
		mntColor.addActionListener(this);
			
		JMenu mnRedimensionar = new JMenu("Redimensionar");
		mnOptions.add(mnRedimensionar);
		
		mntOriginal = new JMenuItem("Texto original");
		mnRedimensionar.add(mntOriginal);
		mntOriginal.addActionListener(this);
		
		mnt800 = new JMenuItem("800x600");
		mnRedimensionar.add(mnt800);
		mnt800.addActionListener(this);
		
		mnt1024 = new JMenuItem("1024x768");
		mnRedimensionar.add(mnt1024);
		mnt1024.addActionListener(this);
		
		mntCustom = new JMenuItem("Custom");
		mnRedimensionar.add(mntCustom);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		mntCustom.addActionListener(this);
		
		
		txtAlto = new JTextField();
		txtAlto.setBounds(355, 22, 96, 32);
		contentPane.add(txtAlto);
		txtAlto.setColumns(10);
		
		txtAncho = new JTextField();
		txtAncho.setColumns(10);
		txtAncho.setBounds(355, 77, 96, 32);
		contentPane.add(txtAncho);
		
		JLabel lblAlto = new JLabel("Alto:");
		lblAlto.setBounds(280, 21, 67, 32);
		contentPane.add(lblAlto);
		
		JLabel lblAncho = new JLabel("Ancho:");
		lblAncho.setBounds(280, 76, 67, 32);
		contentPane.add(lblAncho);
	}

	public int random(int num1, int num2) {
		 return (int) Math.floor(Math.random()*(num1-num2+1)+num2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == mntSalir) {
			System.exit(0);
		}
		else if (e.getSource() == mntColor) {
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);
            Color c = new Color(red, green, blue);
			contentPane.setBackground(c);
			//contentPane.setBackground(new Color(red,green,blue));
		}
		else if(e.getSource() == mntOriginal) {
			setSize(580, 340);
		}
		else if(e.getSource() == mnt800) {
			setSize(800, 600);
		}
		else if(e.getSource() == mnt1024) {
			setSize(1024, 768);
		}
		else if(e.getSource() == mntCustom) {
			try {
				int alto = Integer.parseInt(txtAlto.getText());
				int ancho = Integer.parseInt(txtAncho.getText());
				setSize(alto, ancho);
			} catch (Exception error) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "las cajas deben contener valores", "Error!", 1);
			}

		}
		
		
	}
}
