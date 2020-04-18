

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import javax.swing.JTextField;
import java.awt.Rectangle;
import javax.swing.JPasswordField;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridLayout;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JButton;

import classes.BancoDeDados;

public class CadUsu extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JTextField jTextField = null;

	private JPasswordField jPasswordField = null;

	private JPanel jPanel = null;

	private JLabel jLabel = null;

	private JButton jButton = null;

	private JLabel jLabel1 = null;

	private JLabel jLabel2 = null;

	private JButton jButton1 = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	private JButton jButton4 = null;

	private JButton jButton5 = null;
	private BancoDeDados banco = null;  //  @jve:decl-index=0:
	private ImageIcon conteudoFoto = null;

	/**
	 * This is the default constructor
	 */
	public CadUsu() {
		super();
		initialize();
		inicialize2();
	}
	public void inicialize2(){
		try {
			banco = new BancoDeDados("MYSQL");
			if(!banco.conecta()){
				JOptionPane.showMessageDialog(null, "Não conseguiu conectar o banco...");
				System.exit(0);
			}
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(499, 330);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(new Rectangle(159, 86, 42, 22));
			jLabel2.setText("Senha:");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new Rectangle(149, 56, 52, 22));
			jLabel1.setText("Usuário:");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJTextField(), null);
			jContentPane.add(getJPasswordField(), null);
			jContentPane.add(getJPanel(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(jLabel1, null);
			jContentPane.add(jLabel2, null);
			jContentPane.add(getJButton1(), null);
			jContentPane.add(getJButton2(), null);
			jContentPane.add(getJButton3(), null);
			jContentPane.add(getJButton4(), null);
			jContentPane.add(getJButton5(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new JTextField();
			jTextField.setBounds(new Rectangle(204, 56, 134, 22));
			jTextField.addFocusListener(new java.awt.event.FocusAdapter() {
				public void focusLost(java.awt.event.FocusEvent e) {
					String sql = "SELECT id, senha, foto from usuario where id = '" +
					 jTextField.getText() + "'";
					System.out.println(sql);
					try {
						banco.consultar(sql);
						if (banco.TemRegistro()){
							jTextField.setText(banco.PegaCampo("id"));
							jPasswordField.setText(banco.PegaCampo("senha"));
							ImageIcon novaimagem = new ImageIcon(banco.PegaCampo("foto"));
							jLabel.setIcon(novaimagem);							
							
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			});
		}
		return jTextField;
	}

	/**
	 * This method initializes jPasswordField	
	 * 	
	 * @return javax.swing.JPasswordField	
	 */
	private JPasswordField getJPasswordField() {
		if (jPasswordField == null) {
			jPasswordField = new JPasswordField();
			jPasswordField.setBounds(new Rectangle(203, 86, 135, 21));
		}
		return jPasswordField;
	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLabel = new JLabel();
			jLabel.setText("JLabel");
			GridLayout gridLayout = new GridLayout();
			gridLayout.setRows(1);
			jPanel = new JPanel();
			jPanel.setLayout(gridLayout);
			jPanel.setBounds(new Rectangle(204, 123, 138, 72));
			jPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			jPanel.add(jLabel, null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(205, 203, 136, 26));
			jButton.setText("Carregar Imagem");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFileChooser fc = new JFileChooser();
					FileNameExtensionFilter filtroimagem = new FileNameExtensionFilter("Imagens","jpg","gif","png");
					fc.setFileFilter(filtroimagem);
					
					ImageIcon conteudo = null;
					String caminho = null;
					File arq = fc.getSelectedFile();
					
					fc.showOpenDialog(null);
					caminho = fc.getCurrentDirectory().getPath() + "\\" + fc.getSelectedFile().getName();
					conteudo = new ImageIcon(caminho);
					conteudoFoto = conteudo;
					int comprimento = jLabel.getWidth();
					int altura = jLabel.getHeight();
					Image conteudo2 = new ImageIcon(conteudo.getImage().getScaledInstance(comprimento, altura, 1)).getImage();
					ImageIcon novaimagem = new ImageIcon(conteudo2);
					jLabel.setIcon(novaimagem);
					
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new Rectangle(28, 259, 74, 21));
			jButton1.setText("Inserir");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String sql = "INSERT INTO usuario (id, senha, foto) VALUES ('" +
					   jTextField.getText() + "','" +
					   String.valueOf(jPasswordField.getPassword()) + "','" +
					   conteudoFoto + "')";
					try {
						banco.atualizar(sql);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
									}
			});
		}
		return jButton1;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new Rectangle(115, 259, 74, 21));
			jButton2.setText("Alterar");
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new Rectangle(208, 259, 74, 21));
			jButton3.setText("Excluir");
		}
		return jButton3;
	}

	/**
	 * This method initializes jButton4	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton4() {
		if (jButton4 == null) {
			jButton4 = new JButton();
			jButton4.setBounds(new Rectangle(371, 259, 74, 21));
			jButton4.setText("Sair");
			jButton4.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return jButton4;
	}

	/**
	 * This method initializes jButton5	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton5() {
		if (jButton5 == null) {
			jButton5 = new JButton();
			jButton5.setBounds(new Rectangle(349, 56, 37, 22));
			jButton5.setText("...");
		}
		return jButton5;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				CadUsu thisClass = new CadUsu();
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
			}
		});
	}	

}  //  @jve:decl-index=0:visual-constraint="10,10"
