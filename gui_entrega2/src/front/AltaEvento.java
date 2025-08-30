package front;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import logica.IControllerEvento;



public class AltaEvento extends JInternalFrame{
	private static AltaEvento instance = null;
	private  IControllerEvento controllerEvento;
	private JTextField NombreEvento;
    private JTextField Sigla;
	private JTextField DescripcionEvento;
	private JTextField FechaEvento;
	private JLabel lblNombreEvento;
	private JLabel lblSigla;
	private JLabel lblDescripcionEvento;
	private JLabel lblFechaEvento;
	
	public AltaEvento(IControllerEvento ice) {
		controllerEvento = ice;
    	setTitle("Alta De Evento");
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(100, 100, 450, 300); 
       
        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(new EmptyBorder(8, 10, 10, 10));
        getContentPane().add(content, BorderLayout.CENTER);
        int y = 0;
        lblNombreEvento = new JLabel("Nombre:");
        content.add(lblNombreEvento, gbc(0, y, 1, 1, 0, 0, GridBagConstraints.NONE));
        NombreEvento = new JTextField(20);
        content.add(NombreEvento, gbc(1, y, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));
        y++;
        lblSigla = new JLabel("Sigla:");
        content.add(lblSigla, gbc(0, y, 1, 1,0,0, GridBagConstraints.NONE));
        
  	    Sigla = new JTextField(20);
		content.add(Sigla, gbc(1, y, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));
		y++;
		lblDescripcionEvento = new JLabel("Descripcion:");
		content.add(lblDescripcionEvento, gbc(0, y, 1, 1,0,0, GridBagConstraints.NONE));
		DescripcionEvento = new JTextField(20);
		content.add(DescripcionEvento, gbc(1, y, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));
		y++;
		lblFechaEvento = new JLabel("Fecha:");
		content.add(lblFechaEvento, gbc(0, y, 1, 1, 0,0, GridBagConstraints.NONE));
		FechaEvento = new JTextField(20);
		content.add(FechaEvento, gbc(1, y, 2, 1, 1, 0, GridBagConstraints.HORIZONTAL));
		y++;
		
  
	}
	
	public static AltaEvento getInstance(IControllerEvento I) {
		if (instance == null) {
			instance = new AltaEvento(I);
		}
		return instance;
	}
	
	private static GridBagConstraints gbc(int x, int y, int w, int h, double wx, double wy, int fill) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = x; c.gridy = y;
        c.gridwidth = w; c.gridheight = h;
        c.weightx = wx; c.weighty = wy;
        c.fill = fill;
        c.insets = new Insets(3, 3, 3, 3);
        c.anchor = GridBagConstraints.LINE_START;
        return c;
    }
	
	
	
	
}