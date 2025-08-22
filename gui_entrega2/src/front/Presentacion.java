package front;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;

public class Presentacion extends JFrame{
	public Presentacion() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setVisible(false);
		getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		JMenu mnSistema = new JMenu("Sistema");
		menuBar.add(mnSistema);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnSistema.add(mntmSalir);
		
		JMenu mnNewMenu = new JMenu("Usuario");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmAlta = new JMenuItem("Alta Usuario");
		mntmAlta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				AltaUsuario frame_AltaUsuario = new AltaUsuario();
				desktopPane.add(frame_AltaUsuario);
				frame_AltaUsuario.setVisible(true);
			}
		});
		mnNewMenu.add(mntmAlta);
		
		JMenuItem mntmModificar = new JMenuItem("Modificar Usuario");
		mnNewMenu.add(mntmModificar);
		
		JMenu mnEvento = new JMenu("Evento");
		menuBar.add(mnEvento);
		
		JMenuItem mntmConsultaEvento = new JMenuItem("Consulta Evento");
		mnEvento.add(mntmConsultaEvento);
		
		JMenuItem mntmConsultaEventoedicion = new JMenuItem("Consulta EventoEdicion");
		mnEvento.add(mntmConsultaEventoedicion);
		
		JMenuItem mntmConsultaPatrocinio = new JMenuItem("Consulta Patrocinio");
		mnEvento.add(mntmConsultaPatrocinio);
		
		
	}
	
}