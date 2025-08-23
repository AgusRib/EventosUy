package front;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private AltaUsuario frmAltaUsuario;
	private ModificarDatosUsuario frmModificarDatosUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
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
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnSistema = new JMenu("Sistema");
		menuBar.add(mnSistema);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnSistema.add(mntmSalir);
		
		JMenu mnFuncionalidades = new JMenu("Funcionalidades");
		menuBar.add(mnFuncionalidades);
		
		JMenu mnUsuario = new JMenu("Usuario");
		mnFuncionalidades.add(mnUsuario);
		
		JMenuItem mntmAltaUsuario = new JMenuItem("Alta Usuario");
		mnUsuario.add(mntmAltaUsuario);
		frmAltaUsuario = new AltaUsuario();
		this.getContentPane().add(frmAltaUsuario);
		frmAltaUsuario.setVisible(false);
		mntmAltaUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmAltaUsuario.setVisible(true);
			}
		});
		
		
		JMenuItem mntmModificarDatos = new JMenuItem("Modificar Datos");
		mnUsuario.add(mntmModificarDatos);
		frmModificarDatosUsuario = new ModificarDatosUsuario();
		this.getContentPane().add(frmModificarDatosUsuario);
		frmModificarDatosUsuario.setVisible(false);
		mntmModificarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmModificarDatosUsuario.setVisible(true);
			}
		});
		
		JMenu mnEvento = new JMenu("Evento");
		mnFuncionalidades.add(mnEvento);
		
		JMenuItem mntmConsulta_1 = new JMenuItem("Consulta");
		mnEvento.add(mntmConsulta_1);
		
		JMenu mnEdicion = new JMenu("Edicion");
		mnFuncionalidades.add(mnEdicion);
		
		JMenuItem mntmRegistro = new JMenuItem("Registro");
		mnEdicion.add(mntmRegistro);
		
		JMenuItem mntmConsulta = new JMenuItem("Consulta");
		mnEdicion.add(mntmConsulta);
		
		JMenu mnPatrocinio = new JMenu("Patrocinio");
		mnFuncionalidades.add(mnPatrocinio);
		
		JMenuItem mntmConsulta_3 = new JMenuItem("Consulta");
		mnPatrocinio.add(mntmConsulta_3);
		
		JMenu mnTipoRegistro = new JMenu("Tipo Registro");
		mnFuncionalidades.add(mnTipoRegistro);
		
		JMenuItem mntmConsulta_2 = new JMenuItem("Consulta");
		mnTipoRegistro.add(mntmConsulta_2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));

	}

}
