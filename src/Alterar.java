import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import java.sql.*;

public class Alterar extends JDialog {
	JTextField cNome, cValor, cMarca, cQtde, cFornecedor;
	JLabel lNome, lValor, lQtde, lFornecedor, lMarca, lTipo;
	JButton btnGravar;
	JPanel pnlNome, pnlValor, pnlMarca, pnlQTDE, pnlBotoes, pnlGeral, pnlFornecedor, pnlTipo, pnlTamanho, pnlApagar;
	JSpinner control, tipos;
	int Iqtde, Ivalor;
	public JComboBox combo;
	
	public Connection Conexao;
	public String EnderecoDB, strRegistro, strComandoSQL,Sqtde,Stipo,tipo;
	// public int rsRegistro;
	public Statement Comando;
	public ResultSet rsRegistro;

	public Tela_inicial a = new Tela_inicial();

	public Alterar(Frame Proprietario, String Titulo, boolean Modal) {
		super(Proprietario, Titulo, Modal);
		strRegistro = (String) JOptionPane.showInputDialog("Digite o numero do registro");
	
		setLayout(null);
		setSize(600, 300);
		setBackground(Color.GRAY);
		setVisible(false);
		setResizable(true);
		setLayout(new GridLayout(3, 2));
		setTitle("Cadastro de Produtos");
		setLocation(50, 190);

		combo = new JComboBox(new Object[] {"----camisas----",
				"Camisa---Angêro","Camisa---Alenice","Camisa---WRK","Camisa---Time Kids",
				"Camisa---Planet Kids","Camisa---Brandili","Camsia---Boca Grande",
				"Camisa---Lecimar","Camisa---Rezzato"
				});
		
		cNome = new JTextField(20);
		cValor = new JTextField(20);
		cMarca = new JTextField(20);
		cFornecedor = new JTextField(16);

		pnlNome = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlNome.add(new JLabel("Nome:"));
		pnlNome.add(cNome);

		pnlValor = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlValor.add(new JLabel("Valor:"));
		pnlValor.add(cValor);

		pnlMarca = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlMarca.add(new JLabel("Marca:"));
		pnlMarca.add(cMarca);

		control = new JSpinner(new SpinnerNumberModel());
		Dimension d = new Dimension(225, 20);
		control.setPreferredSize(d);
		Object result = control.getValue();
		pnlQTDE = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlQTDE.add(new JLabel("QTDE:"));
		pnlQTDE.add(control);

		pnlTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTipo.add(new JLabel("Tipo:   "));
		pnlTipo.add(combo);

		pnlFornecedor = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlFornecedor.add(new JLabel("Fornecedor"));
		pnlFornecedor.add(cFornecedor);

		pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnGravar = new JButton("Gravar");

	
		btnGravar.setPreferredSize(new Dimension(300, 20));
		pnlBotoes.add(btnGravar);
		pnlBotoes.setBackground(Color.GRAY);

		pnlNome.setBackground(Color.GRAY);
		pnlValor.setBackground(Color.GRAY);
		pnlMarca.setBackground(Color.GRAY);
		pnlQTDE.setBackground(Color.GRAY);
		pnlTipo.setBackground(Color.gray);
		pnlFornecedor.setBackground(Color.gray);
		pnlBotoes.setBackground(Color.gray);

		pnlGeral = new JPanel(new GridLayout(4, 2));
		pnlGeral.add(pnlNome);
		pnlGeral.add(pnlValor);
		pnlGeral.add(pnlMarca);
		pnlGeral.add(pnlQTDE);
		pnlGeral.add(pnlTipo);
		pnlGeral.add(pnlFornecedor);

		pnlBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlGeral.setBackground(Color.gray);
		getContentPane().add(pnlGeral, BorderLayout.NORTH);
		getContentPane().add(pnlBotoes);
		getContentPane().setBackground(Color.gray);

		ManipulaBotao manipula = new ManipulaBotao();
		btnGravar.addActionListener(manipula);

		if (strRegistro != null) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				EnderecoDB = "jdbc:mysql://31.170.163.101:3306/fernandodev_estoque";
				Conexao = DriverManager.getConnection(EnderecoDB, "fernandodev_estoque", "123456");
				strComandoSQL = "SELECT * FROM Produtos WHERE Referencia = " + strRegistro;

				Comando = Conexao.createStatement();
				rsRegistro = Comando.executeQuery(strComandoSQL);

				if (rsRegistro.next()) {
					String referencia = rsRegistro.getString("Referencia");
					String Sqtde = rsRegistro.getString("Estoque");
					cNome.setText(rsRegistro.getString("Nome"));
					cValor.setText(rsRegistro.getString("Preco"));
					cMarca.setText(rsRegistro.getString("Marca"));
					cFornecedor.setText(rsRegistro.getString("Fornecedor"));
					String tipo = rsRegistro.getString("Tipo");
					Stipo = (String) combo.getSelectedItem();
				} else {
					JOptionPane.showMessageDialog(null, "Registro nao encotrado!", "Mensagem",
							JOptionPane.INFORMATION_MESSAGE);
					Comando.close();
					Conexao.close();
				}
			} catch (Exception Excecao) {
				System.out.println("erro: "+Excecao);
				JOptionPane.showMessageDialog(null, "SQLException: " + Excecao.getMessage(),
						"Erro: Sele��o de registro", JOptionPane.INFORMATION_MESSAGE);
			}
		}else {
			setVisible(false);
			dispose();
		}
	}

	public class ManipulaBotao implements ActionListener {
		public void actionPerformed(ActionEvent eventoObjeto) {
			String strComandoSQL;
			int intRegistro;

			if (eventoObjeto.getSource() == btnGravar) {
				try {
					
					Conexao = DriverManager.getConnection(EnderecoDB, "fernandodev_estoque", "123456");
					strComandoSQL = "UPDATE Produtos SET Nome=" + "\"" + cNome.getText() + "\"," + "Marca=\""
							+ cMarca.getText() + "\"," + "Preco=\"" + cValor.getText() + "\"," + "Fornecedor=\""
							+ cFornecedor.getText()+ "\","+"Tipo=\""+ combo.getSelectedItem() + "\"" + "WHERE Referencia = " + strRegistro;

					Comando = Conexao.createStatement();
					intRegistro = Comando.executeUpdate(strComandoSQL);

					if (intRegistro != 0) {
						JOptionPane.showMessageDialog(null, "Registro alterado!", "Mensagem",
								JOptionPane.INFORMATION_MESSAGE);

						Comando.close();
						Conexao.close();

						cNome.setText("");
						cValor.setText("");
						cMarca.setText("");
						cFornecedor.setText("");

					}
				} catch (Exception Excecao) {
					JOptionPane.showMessageDialog(null, "SQLException: " + Excecao.getMessage(),
							"Erro: Adi��o de registro", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

	}

}
