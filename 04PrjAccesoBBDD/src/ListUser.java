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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.mysql.cj.xdevapi.Table;

import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ListUser extends JFrame {

	private JPanel contentPane;
	private JTextField txtUser;
	private JTextField txtPass;
	private JTextField txtNombre;
	private Connection conexion;
	private String str_buscar;
	private DefaultTableModel modelo;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListUser frame = new ListUser();
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
	public ListUser() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 604, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*********************BOTON REGISTRAR*********************************/
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, SystemColor.activeCaption));
		panel.setBounds(310, 31, 272, 253);
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
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setEnabled(false);
		btnModificar.setBackground(SystemColor.textHighlight);
		btnModificar.setForeground(UIManager.getColor("CheckBox.darkShadow"));
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnModificar.setBounds(18, 190, 219, 47);
		panel.add(btnModificar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 10, 280, 336);
		contentPane.add(scrollPane);
		
		
		/*********************** DEFINIR TABLA ****************************/
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			//mouse click sobre tabla 
			public void mouseClicked(MouseEvent e) {
				int fila = table.rowAtPoint(e.getPoint()); //devuelve la fila del registro
				int columna = 0;
				//System.out.println(fila);
				
				if((fila > - 1) && (columna>-1)) {
					
					//str_buscar.modelo.getValueAt(fila,columna).toString();
					txtUser.setText(modelo.getValueAt(fila, columna).toString());
					txtNombre.setText(modelo.getValueAt(fila, columna+1).toString());
					txtPass.setText(modelo.getValueAt(fila, columna+2).toString());
					
					btnModificar.setEnabled(true); //para que se active el boton cuando se llenan los campos
					
				}
				
				
			}
			
		});
		scrollPane.setViewportView(table);
		
		modelo = new DefaultTableModel();
		
		modelo.addColumn("Usuario");
		modelo.addColumn("Pass");
		modelo.addColumn("Nombre");
		
		table.setModel(modelo);

		/////////////////////// BOTON ACTUALIZAR ////////////////////////
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//str_buscar = txtBuscar.getText();
				String user = txtUser.getText().toString();
				String pass = txtPass.getText().toString();
				String nombre = txtNombre.getText().toString();
				
				String sql = "UPDATE `trj_user` SET `user`="
						+ " '"+user+"',  `pass`='"+ pass +"',`nombre`='"+nombre+"' WHERE `user`= '"+str_buscar+"';";
				
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
		rellenar_tabla();
	}//fin constructor de la clase
	
	/************************** CARGAR / RELLENAR TABLA ***********************/
	public void rellenar_tabla() {

		String sql = "SELECT * FROM `trj_user` WHERE 1";
		
		try {
			//Statement necesario para ir a la BBDD
			Statement comando = conexion.createStatement();
			// ResultSet - Almacena el resultado de nuestra consulta
			comando.executeQuery(sql);
			ResultSet resultado = comando.executeQuery(sql);
			
			while(resultado.next()) {
				Object[] fila = new Object[3];
				//lo que hay entre comillas son los campos de la bbdd
				fila[0] = resultado.getString("user");
				fila[1] = resultado.getString("pass");
				fila[2] = resultado.getString("nombre");
				
				//añade fila al final del modelo de la tabla
				modelo.addRow(fila);
				System.out.println(resultado.getString("user"));
			}
			
		} catch (SQLException e1) {
			//ERRORES
			// el SYSO hace que ponga el error exacto 
			System.out.println(e1.getMessage().toString());
		}
		
		/****************        REFRESCAR                ******************/
		
	}
}//fin clase
