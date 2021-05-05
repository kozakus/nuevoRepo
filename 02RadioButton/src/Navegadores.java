import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.net.URI;
import java.awt.event.ActionEvent;

public class Navegadores extends JFrame {

	private JPanel contentPane;
	private JTextField txtUrl;
	//button group variable
	private ButtonGroup bg;
	private JRadioButton rdbGoogle;
	JRadioButton rdbYahoo;
	JRadioButton rdbDuck;
	Desktop desktop = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Navegadores frame = new Navegadores();
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
	public Navegadores() {
		setTitle("BUSCADOR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Criterio");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(19, 192, 63, 33);
		contentPane.add(lblNewLabel);
		
		txtUrl = new JTextField();
		txtUrl.setBounds(101, 194, 181, 33);
		contentPane.add(txtUrl);
		txtUrl.setColumns(10);
		
		//event BOTON
		JButton btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String url = "";
				if (rdbGoogle.isSelected()) {
					url = "https://www.google.com/search?q=" + txtUrl.getText().replaceAll(" ", "+");
				}
				else if(rdbDuck.isSelected()) {
					url = "https://duckduckgo.com/?q="+ txtUrl.getText().replaceAll(" ", "+");
					
				}
				else if(rdbYahoo.isSelected()) {
					url = "https://es.search.yahoo.com/search?p="+ txtUrl.getText().replaceAll(" ", "+");
				}
				
				try {
					URI uri = new URI(url);
					desktop = Desktop.getDesktop();
					if (desktop != null)
						desktop.browse(uri);
						
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage().toString());
				}
			}
		});
		
		
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBuscar.setBounds(315, 191, 83, 37);
		contentPane.add(btnBuscar);
		
		JLabel lblNewLabel_1 = new JLabel("Selecciona tu buscador");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(19, 22, 181, 33);
		contentPane.add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setForeground(Color.ORANGE);
		panel.setBounds(19, 59, 105, 125);
		contentPane.add(panel);
		panel.setLayout(null);
		
		//creación de un nuevo button group
		bg = new ButtonGroup();		
		
		rdbGoogle = new JRadioButton("Google");
		rdbGoogle.setBounds(4, 6, 99, 21);
		///////////////////////////////////
		bg.add(rdbGoogle);
		panel.add(rdbGoogle);
		
		rdbYahoo = new JRadioButton("Yahoo");
		rdbYahoo.setBounds(4, 42, 99, 21);
		///////////////////////////////////
		bg.add(rdbYahoo);
		panel.add(rdbYahoo);

		rdbDuck = new JRadioButton("Duck Duck Go");
		rdbDuck.setBounds(4, 77, 99, 21);
		bg.add(rdbDuck);
		panel.add(rdbDuck);
		
	}
}
