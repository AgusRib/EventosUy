package front;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logica.Factory;
import logica.IControllerEvento;
import logica.IControllerUsuario;

public class Main {

	private static final long serialVersionUID = 1L;
	private JFrame frmMain;
	
	private AltaUsuario frmAltaUsuario;
	private ModificarDatosUsuario frmModificarDatosUsuario;
	private ConsultaEdicionDeEvento frmConsultaEdicion;
	private RegistroEdicion frmRegistroEdicion;
	private ConsultaDeEvento frmConsultaDeEvento;
	private AltaTipoRegistro frmAltaTipoRegistro;
	
	private IControllerUsuario ICU;
	private IControllerEvento ICE;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.frmMain.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Main() {
		frmMain = new JFrame();
		frmMain.setTitle("Main");

		ICU = Factory.getInstance().getControllerUsuario();
		
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMain.setBounds(100, 100, 720, 480);
		
		// Barra superior
		JMenuBar menuBar = new JMenuBar();
		frmMain.setJMenuBar(menuBar);
		
		JMenu mnSistema = new JMenu("Sistema");
		menuBar.add(mnSistema);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnSistema.add(mntmSalir);
		
		JMenu mnFuncionalidades = new JMenu("Funcionalidades");
		menuBar.add(mnFuncionalidades);
		
		
		// Menu Usuario
		JMenu mnUsuario = new JMenu("Usuario");
		mnFuncionalidades.add(mnUsuario);
		
		//Submenus Usuario
			JMenuItem mntmAltaUsuario = new JMenuItem("Alta Usuario");
			mnUsuario.add(mntmAltaUsuario);
			frmAltaUsuario = new AltaUsuario(ICU);
			frmMain.getContentPane().add(frmAltaUsuario);
			frmAltaUsuario.setVisible(false);
			mntmAltaUsuario.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmAltaUsuario.setVisible(true);
					frmAltaUsuario.toFront();
				}
			});
			
			
			JMenuItem mntmModificarDatos = new JMenuItem("Modificar Datos");
			mnUsuario.add(mntmModificarDatos);
			frmModificarDatosUsuario = new ModificarDatosUsuario();
			frmMain.getContentPane().add(frmModificarDatosUsuario);
			frmModificarDatosUsuario.setVisible(false);
			mntmModificarDatos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmModificarDatosUsuario.setVisible(true);
					frmModificarDatosUsuario.toFront();
				}
			});
		
		// Menu Evento
		JMenu mnEvento = new JMenu("Evento");
		mnFuncionalidades.add(mnEvento);
		
		// Submenus Evento
			JMenuItem mntmConsultaEvento = new JMenuItem("Consulta");
			mnEvento.add(mntmConsultaEvento);
			frmConsultaDeEvento = new ConsultaDeEvento();
			frmMain.getContentPane().add(frmConsultaDeEvento);
			frmConsultaDeEvento.setVisible(false);
			mntmConsultaEvento.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmConsultaDeEvento.setVisible(true);
					frmConsultaDeEvento.toFront();
				}
			});
		
		// Edicion
		JMenu mnEdicion = new JMenu("Edicion");
		mnFuncionalidades.add(mnEdicion);
		
		//Submenus Edicion
			JMenuItem mntmRegistro = new JMenuItem("Registro");
			mnEdicion.add(mntmRegistro);
			frmRegistroEdicion = new RegistroEdicion();
			frmMain.getContentPane().add(frmRegistroEdicion);
			frmRegistroEdicion.setVisible(false);
			mntmRegistro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmRegistroEdicion.setVisible(true);
					frmRegistroEdicion.toFront();
				}
			});
			
			JMenuItem mntmConsultaEdicion = new JMenuItem("Consulta");
			mnEdicion.add(mntmConsultaEdicion);
			frmConsultaEdicion = new ConsultaEdicionDeEvento();
			frmMain.getContentPane().add(frmConsultaEdicion);
			frmConsultaEdicion.setVisible(false);
			mntmConsultaEdicion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmConsultaEdicion.setVisible(true);
					frmConsultaEdicion.toFront();
				}
			});
		
		// Patrocinio
		JMenu mnPatrocinio = new JMenu("Patrocinio");
		mnFuncionalidades.add(mnPatrocinio);
		
		//Submenus patrocinio
			JMenuItem mntmConsultaPatrocinio = new JMenuItem("Consulta");
			mnPatrocinio.add(mntmConsultaPatrocinio);
		
		
		// Tipo Registro
		JMenu mnTipoRegistro = new JMenu("Tipo Registro");
		mnFuncionalidades.add(mnTipoRegistro);
		
			//Sub menus TipoRegistro
			JMenuItem mntmAltaTipoRegistro = new JMenuItem("Alta");
			mnTipoRegistro.add(mntmAltaTipoRegistro);
			frmAltaTipoRegistro = new AltaTipoRegistro(ICE);
			frmMain.getContentPane().add(frmAltaTipoRegistro);
			frmAltaTipoRegistro.setVisible(false);
			mntmAltaTipoRegistro.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					frmAltaTipoRegistro.setVisible(true);
					frmAltaTipoRegistro.toFront();
				}
			});
			
			JMenuItem mntmConsultaTipoReg = new JMenuItem("Consulta");
			mnTipoRegistro.add(mntmConsultaTipoReg);
		
		frmMain.getContentPane().setLayout(null);
	}

}
