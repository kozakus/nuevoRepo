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

public class Eliminar extends JFrame {

	private JPanel contentPane;
	private JTextField txtUser;
	private JTextField txtPass;
	private JTextField txtNombre;
	private Connection conexion;
	private JTextField txtBuscar;
	private JLabel lblNotFound;
	private String str_buscar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Eliminar frame = new Eliminar();
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
	public Eliminar() {
		
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
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setBackground(Color.RED);
		btnEliminar.setForeground(Color.BLACK);
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEliminar.setBounds(18, 190, 219, 47);
		panel.add(btnEliminar);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(349, 96, 79, 55);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		////////////////////// BOTON BUSCAR /////////////////////////////
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				str_buscar = txtBuscar.getText();
				String sql = "SELECT * FROM  `trj_user` WHERE `user` LIKE '" + str_buscar + "';";
						
				Statement registro;
				try {
					registro = conexion.createStatement();
					ResultSet consulta = registro.executeQuery(sql);
					
					if(consulta.next()) {
						lblNotFound.setVisible(false);
						txtUser.setText(consulta.getString("user"));
						txtPass.setText(consulta.getString("pass"));
						txtNombre.setText(consulta.getString("nombre"));
					}
					else {
						lblNotFound.setVisible(true);
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		btnBuscar.setBounds(345, 195, 83, 21);
		contentPane.add(btnBuscar);

		lblNotFound = new JLabel("Not Found");
		lblNotFound.setBounds(366, 50, 42, 13);
		contentPane.add(lblNotFound);
		lblNotFound.setVisible(false);

		/////////////////////// BOTON ACTUALIZAR ////////////////////////
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//str_buscar = txtBuscar.getText();
				String user = txtUser.getText().toString();
				String pass = txtPass.getText().toString();
				String nombre = txtNombre.getText().toString();
				
				String sql = "DELETE FROM `trj_user`"
						+ "WHERE `user`= '"+str_buscar+"';";
				
				//para comprobar que genera el código correcto de SQL
				//System.out.println(sql); 
				
				//EJECUTAR LA SQL
				try {
					//Statement necesario para ir a la BBDD
					Statement comando = conexion.createStatement();
					int ok = JOptionPane.showConfirmDialog(null, "Desea eliminar el registro: "  
							 + str_buscar, "Eliminar: ", JOptionPane.INFORMATION_MESSAGE);
					
					if(ok==0) {
						comando.executeUpdate(sql);
						txtBuscar.setText(null);
						limpiar_campos();
					
					}
					// ResultSet - Almacena el resultado de nuestra consulta
					comando.executeUpdate(sql);
					
					String msg = "El registro ha sido borrado con éxito";
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
	
	/*************** LIMPIAR CAMPOS ******************/
	public void limpiar_campos(){
		txtUser.setText(null);
		txtPass.setText(null);
		txtNombre.setText(null);
	}
}//fin clase
