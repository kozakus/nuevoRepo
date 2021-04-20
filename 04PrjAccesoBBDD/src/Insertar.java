import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class Insertar extends JFrame {

	private JPanel contentPane;
	private JTextField txtUser;
	private JTextField txtPass;
	private JTextField txtNombre;
	private Connection conexion;
	private JTextField txtBuscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Insertar frame = new Insertar();
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
	public Insertar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*********************BOTON REGISTRAR*********************************/
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.activeCaption));
		panel.setBounds(69, 10, 272, 253);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(8, 26, 48, 26);
		panel.add(lblNewLabel);
		
		txtUser = new JTextField();
		txtUser.setBounds(92, 23, 173, 35);
		panel.add(txtUser);
		txtUser.setColumns(10);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblContrasea.setBounds(8, 82, 76, 35);
		panel.add(lblContrasea);
		
		txtPass = new JTextField();
		txtPass.setBounds(92, 83, 173, 35);
		panel.add(txtPass);
		txtPass.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombre.setBounds(8, 144, 76, 35);
		panel.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(92, 145, 173, 35);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		JButton btnInsertar = new JButton("Registrar");
		btnInsertar.setBackground(SystemColor.textHighlight);
		btnInsertar.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		btnInsertar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInsertar.setBounds(18, 190, 219, 47);
		panel.add(btnInsertar);
		
		
		/************************ BOTON BUSCAR **************************/
		JButton btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str_buscar = txtBuscar.getText();
				String sql = "SELECT * FROM `trj_user` WHERE `trj_user` ='" + str_buscar + "'";
				Statement registro;
				try {
					registro = conexion.createStatement();
					ResultSet consulta = registro.executeQuery(sql);
					
					if(consulta.next()) {
					txtUser.setText(consulta.getString("user"));
					txtPass.setText(consulta.getString("pass"));
					txtNombre.setText(consulta.getString("nombre"));
					}
					else {
						//no se ejecuta porque no existe.
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				//
				System.out.println(sql);
			}
		});
		btnBuscar.setBounds(353, 217, 83, 36);
		btnBuscar.setIcon(new ImageIcon("assets/lupa.png"));
		contentPane.add(btnBuscar);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(352, 121, 76, 86);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String user = txtUser.getText().toString();
				String pass = txtPass.getText().toString();
				String nombre = txtNombre.getText().toString();
				
				String sql = "INSERT INTO `trj_user` (`user`, `pass`, `nombre`) "
						+ "VALUES ('"+ user +"', '"+ pass +"', '"+ nombre + "');";
				
				//para comprobar que genera el código correcto de SQL
				//System.out.println(sql); 
				
				//EJECUTAR LA SQL
				try {
					//Statement necesario para ir a la BBDD
					Statement comando = conexion.createStatement();
					
					// ResultSet - Almacena el resultado de nuestra consulta
					comando.executeUpdate(sql);
					
					String msg = "El registro ha sido grabado con éxito";
					JOptionPane.showMessageDialog(null, msg, "Registro", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException e1) {
					//ERRORES
					// el SYSO hace que ponga el error exacto 
					System.out.println(e1.getMessage().toString());
					String msg = "No ha sido posible realizar la operación";
					JOptionPane.showMessageDialog(null,msg, "Error bbdd", 0);
				}
				
			}
		});
		
		/************************CONEXION CON LA BBDD****************************/
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/java_c", "geo", "1234");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
			"Ha sido imposible conectarse, \n" + 
					" revise los datos de conexion");
			JOptionPane.showMessageDialog(this, e.toString());
		}
	}//fin constructor de la clase
}//fin clase
