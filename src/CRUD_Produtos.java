import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class CRUD_Produtos extends JDialog{
	JTextField cNome,cValor,cMarca,cQtde,cFornecedor;
	JLabel lNome,lValor,lQtde,lFornecedor,lMarca,lTipo;
	JButton btnAdicionar,btnApagar,btnSair,btnEditar;
	JPanel pnlNome,pnlValor,pnlMarca,pnlQTDE,pnlBotoes,pnlGeral,pnlFornecedor,pnlTipo,pnlTamanho,pnlApagar;
	JSpinner control, tipos;
	int Iqtde,Ivalor;
	
	
	
	public JComboBox combo;
	public Connection Conexao;
	public String EnderecoDB,strRegistro;
	public int rsRegistro;
	public Statement Comando;
	public ResultSet rsRegistro2;
	
	public Tela_inicial a = new Tela_inicial();
	public CRUD_Produtos(Frame Proprietario,String Titulo,boolean Modal) {
		super(Proprietario,Titulo,Modal);
		
		setLayout(null);
		setSize(600,300);
		setBackground(Color.GRAY);
		setVisible(false);
		setResizable(true);
		setLayout(new GridLayout(3,2));
		setTitle("Cadastro de Produtos");
		setLocation(50,190);
		
		cNome = new JTextField(20);
		cValor = new JTextField(20);
		cMarca = new JTextField(20);
		cFornecedor = new JTextField(16);

		combo = new JComboBox(new Object[] {"---camisas---",
		"Camisa---Angêro","Camisa---Alenice","Camisa---WRK","Camisa---Time Kids",
		"Camisa---Planet Kids","Camisa---Brandili","Camsia---Boca Grande",
		"Camisa---Lecimar","Camisa---Rezzato","---Calcinhas---","Calcinhas"
		});
		
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
		Dimension d = new Dimension(225,20);
		control.setPreferredSize(d);
		Object result = control.getValue();
		pnlQTDE = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlQTDE.add(new JLabel("QTDE:"));
		pnlQTDE.add(control);
		
		//String lista[] = {"Short","Blusa","Camisa","Calça","Vestido","Sapatinho","Meia","Cueca","Calcinha","Conjunto"};
		 //tipos = new JSpinner(new SpinnerListModel(lista));
		//tipos.setPreferredSize(d);
		pnlTipo = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlTipo.add(new JLabel("Tipo:   "));
		pnlTipo.add(combo);
		
		pnlFornecedor = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlFornecedor.add(new JLabel("Fornecedor"));
		pnlFornecedor.add(cFornecedor);
		
		
		
		pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnAdicionar = new JButton("Cadastrar");
		btnApagar = new JButton("Apagar");
		btnEditar = new JButton("Editar");
	//	btnSair = new JButton("Sair");
		btnAdicionar.setPreferredSize(new Dimension(300,20));
		btnApagar.setPreferredSize(new Dimension(300,20));
		btnEditar.setPreferredSize(new Dimension(300,20));
		pnlBotoes.add(btnAdicionar);
		pnlBotoes.add(btnApagar);
		pnlBotoes.add(btnEditar);
		pnlBotoes.setBackground(Color.GRAY);
		

		
		pnlNome.setBackground(Color.GRAY);
		pnlValor.setBackground(Color.GRAY);
		pnlMarca.setBackground(Color.GRAY);
		pnlQTDE.setBackground(Color.GRAY);
		pnlTipo.setBackground(Color.gray);
		pnlFornecedor.setBackground(Color.gray);
		pnlBotoes.setBackground(Color.gray);
		
		pnlGeral = new JPanel(new GridLayout(4,2));
		pnlGeral.add(pnlNome);
		pnlGeral.add(pnlValor);
		pnlGeral.add(pnlMarca);
		pnlGeral.add(pnlQTDE);
		pnlGeral.add(pnlTipo);
		pnlGeral.add(pnlFornecedor);
	
		pnlBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlGeral.setBackground(Color.gray);
		getContentPane().add(pnlGeral,BorderLayout.NORTH);
		getContentPane().add(pnlBotoes);
		getContentPane().setBackground(Color.gray);
		
		ManipulaBotao manipula = new ManipulaBotao();
		btnEditar.addActionListener(manipula);
		btnAdicionar.addActionListener(manipula);
		btnApagar.addActionListener(manipula);
		//btnSair.addActionListener(manipula);
	}

	public class ManipulaBotao implements ActionListener{
		public void actionPerformed(ActionEvent eventObjeto) {
			String Snome,Smarca,Sfornecedor,Svalor,Stipo,Sqtde,strComandoSQL;
			Snome = cNome.getText();
			Smarca = cMarca.getText();
			Sfornecedor = cFornecedor.getText();
			Svalor = cValor.getText();
			Stipo = (String) combo.getSelectedItem();
			
			
			//String tipoPeca = tipos.getValue().toString(); 
			int qtde = Integer.parseInt(control.getValue().toString());
			
			if(eventObjeto.getSource() == btnAdicionar) {
				if((cNome.getText().equals(""))&&(cMarca.getText().equals(""))&&(cFornecedor.getText().equals(""))&&(cValor.getText().equals(""))) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente!!","Mensagem",JOptionPane.INFORMATION_MESSAGE);
				}else {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						EnderecoDB = "jdbc:mysql://31.170.163.101:3306/fernandodev_estoque";
						Conexao = DriverManager.getConnection(EnderecoDB, "fernandodev_estoque", "123456");
						Comando = Conexao.createStatement();
						strComandoSQL = "INSERT INTO Produtos (Nome,Marca,Tipo,Estoque,Preco,Fornecedor)"+"VALUES("+"\""+Snome+"\","+
								"\""+Smarca+"\","+
								"\""+Stipo+"\","+
								"\""+qtde+"\","+
								"\""+Svalor+"\","+
								"\""+Sfornecedor+"\")";
						
						Comando = Conexao.createStatement();
						rsRegistro = Comando.executeUpdate(strComandoSQL);
						JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!!","Mensagem",JOptionPane.INFORMATION_MESSAGE);
						Comando.close();
						Conexao.close();
					
					} catch (Exception e) {
						System.out.println("erro:"+e);
						JOptionPane.showMessageDialog(null, "Erro ao adicionar produto: "+e,"Mensagem",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
					
			}
				if (eventObjeto.getSource () == btnApagar) {
					strRegistro = (String)JOptionPane.showInputDialog("Digite o numero do registro");
					int intRegistro ;
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						EnderecoDB = "jdbc:mysql://31.170.163.101:3306/fernandodev_estoque";
						Conexao = DriverManager.getConnection(EnderecoDB, "fernandodev_estoque", "123456");
						Comando = Conexao.createStatement();
						
						strComandoSQL = "DELETE FROM Produtos WHERE Referencia = "+strRegistro;
						intRegistro = Comando.executeUpdate(strComandoSQL);
						if(intRegistro != 0) {
							JOptionPane.showMessageDialog(null, "Registro excluido!","Mensagem",JOptionPane.INFORMATION_MESSAGE);
							Comando.close();
							Conexao.close();
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Erro no sistemas ou registro nao eonctrado","Mensagem",JOptionPane.INFORMATION_MESSAGE);
				}		
			}
				if(eventObjeto.getSource() == btnEditar) {
					System.out.println("botao disparado");
					Alterar editar = new Alterar(null,"Venda",true);
					editar.setResizable(false);
					editar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					editar.setVisible(true);
					editar.setBackground(Color.GRAY);
					
				}
		}
	}
}