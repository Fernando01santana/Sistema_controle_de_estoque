
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Tela_inicial extends JFrame {

	public static JPanel pnlBotoes, pnlAtualizar, pnlLabel, pnlGbotoes, pnlMenu;
	public static JButton btnProdutos, btnVenda, btnEstoque, btnRelatorio, btnSair, btnAtualizar;
	public static JLabel lop, lTabelaEstoque, lTabelaVenda,tProduto,tEstoque;
	public JMenuBar menuBar;
	public Connection Conexao;
	public String EnderecoDB;
	public ResultSet rsRegistro, rsRegistro2;
	public Statement Comando, Comando2;

	int intTotalRegistro, intNumRegistro, intRegistro, intTotalRegistro2, intNumRegistro2, intRegistro2;
	String NomeColuna[] = { "Referencia", "Nome", "Preço", "Tipo", "Estoque", "Marca", "Fornecedor" };
	String Campo[] = new String[7];
	String Campo3[] = new String[7];
	
	String NomeColuna2[] = { "Codigo", "Produto", "Preço", "Quantidade" };
	String Campo2[] = new String[4];

	// configurações iniciais das tabela
	public JPanel painelFundo;
	public JTable tabela, tabela2;
	static public DefaultTableModel ModeloAgenda;
	static public DefaultTableModel ModeloAgenda2;
	public JScrollPane barraRolagem, barraRolagem1;

	// conexao com o banco de dados
	public void gerarTela() {

		// configuração do JFrame
		setTitle("Gerencia Estoque");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		setLayout(new GridLayout(1, 5));
		Container AreaTela = getContentPane();
		AreaTela.setBackground(Color.white);

		// criando variaveis do tipo ImageIcon e adicionando o caminho da imagem dentro
		// do proejeto
		ImageIcon imgimagem1 = new ImageIcon(getClass().getResource("produto.png"));
		ImageIcon imgimagem2 = new ImageIcon(getClass().getResource("venda.png"));
		ImageIcon imgimagem3 = new ImageIcon(getClass().getResource("estoque.png"));
		ImageIcon imgimagem4 = new ImageIcon(getClass().getResource("relatorio.png"));
		ImageIcon imgimagem5 = new ImageIcon(getClass().getResource("sair.png"));
		ImageIcon imgimagem6 = new ImageIcon(getClass().getResource("atualizar.png"));

		// instanciando os botoes e adicionando as imagens no lugar do texto
		btnProdutos = new JButton(imgimagem1);
		btnVenda = new JButton(imgimagem2);
		btnEstoque = new JButton(imgimagem3);
		btnRelatorio = new JButton(imgimagem4);
		btnAtualizar = new JButton(imgimagem6);
		btnSair = new JButton(imgimagem5);

		// estado dos botoes
		btnProdutos.setEnabled(true);
		btnRelatorio.setEnabled(false);

		// adicionando o action listener aos botoes
		
		FuncaoBotoes Fbotoes = new FuncaoBotoes();
		atualizando att = new atualizando();
		btnProdutos.addActionListener(Fbotoes);
		btnVenda.addActionListener(Fbotoes);
		btnEstoque.addActionListener(Fbotoes);
		btnRelatorio.addActionListener(Fbotoes);
		btnAtualizar.addActionListener(att);
		btnSair.addActionListener(Fbotoes);

		// adicionando legenda aos botoes
		btnProdutos.setToolTipText("Cadastro de produtos");
		btnVenda.setToolTipText("Venda");
		btnEstoque.setToolTipText("Estoque");
		btnRelatorio.setToolTipText("Relatório de estoque");
		btnSair.setToolTipText("Sair da aplicação");
		btnAtualizar.setToolTipText("Atualizar tabela");

		// adicionando os botoes ao painel pnlBotoes
		pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlBotoes.add(btnProdutos);
		pnlBotoes.add(btnVenda);
		pnlBotoes.add(btnEstoque);
		pnlBotoes.add(btnRelatorio);
		pnlBotoes.add(btnAtualizar);
		pnlBotoes.add(btnSair);

		// adcionando pnlBotoes que e do tipo FlowLayout ao pnlGbotoes que do tipo
		// GridLayout
		pnlGbotoes = new JPanel(new GridLayout(8, 1));
		pnlGbotoes.setBackground(Color.gray);
		pnlGbotoes.add(pnlBotoes);
		// getContentPane().add(pnlMenu,BorderLayout.LINE_START);
		getContentPane().add(pnlGbotoes, BorderLayout.NORTH);

		// configuração das tabelas

		

		// Venda Vtabela = new Venda();

		// adicionando o painelfundo ao JFrame

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			EnderecoDB = "jdbc:mysql://31.170.163.101:3306/fernandodev_estoque";
			Conexao = DriverManager.getConnection(EnderecoDB, "fernandodev_estoque", "123456");
			Comando = Conexao.createStatement();
			rsRegistro = Comando.executeQuery("SELECT * FROM Produtos ORDER BY Referencia");
			
			intTotalRegistro2 = 0;
			intTotalRegistro = 0;
			while (rsRegistro.next()) {
				intTotalRegistro++;

				ModeloAgenda2 = new DefaultTableModel(NomeColuna2, intTotalRegistro2);
				ModeloAgenda = new DefaultTableModel(NomeColuna, intTotalRegistro);

				rsRegistro.beforeFirst();
				rsRegistro.next();
				intRegistro = rsRegistro.getInt("Referencia");
				Campo[0] = Integer.toString(intRegistro);
				Campo[1] = rsRegistro.getString("Nome");
				Campo[2] = rsRegistro.getString("Preco");
				Campo[3] = rsRegistro.getString("Tipo");
				Campo[4] = rsRegistro.getString("Estoque");
				Campo[5] = rsRegistro.getString("Marca");
				Campo[6] = rsRegistro.getString("Fornecedor");
				// Campo[7] = rsRegistro.getString("Tamanho");
				ModeloAgenda.insertRow(0, Campo);

				intNumRegistro = 1;
				while (rsRegistro.next()) {
					intRegistro = rsRegistro.getInt("Referencia");
					Campo[0] = Integer.toString(intRegistro);
					Campo[1] = rsRegistro.getString("Nome");
					Campo[2] = rsRegistro.getString("Preco");
					Campo[3] = rsRegistro.getString("Tipo");
					Campo[4] = rsRegistro.getString("Estoque");
					Campo[5] = rsRegistro.getString("Marca");
					Campo[6] = rsRegistro.getString("Fornecedor");
					// Campo[7] = rsRegistro.getString("Tamanho");
					ModeloAgenda.insertRow(intNumRegistro, Campo);
					intNumRegistro++;
				}
			}

			Comando2 = Conexao.createStatement();
			rsRegistro2 = Comando.executeQuery("SELECT * FROM Venda ORDER BY Num_Venda");
			rsRegistro2.beforeFirst();
			rsRegistro2.next();
			intRegistro2 = rsRegistro2.getInt("Num_Venda");
			Campo2[0] = Integer.toString(intRegistro2);
			Campo2[1] = rsRegistro2.getString("Produto");
			Campo2[2] = rsRegistro2.getString("Preco");
			Campo2[3] = rsRegistro2.getString("Quantidade");
			ModeloAgenda2.insertRow(0, Campo2);

			intNumRegistro2 = 1;
			while (rsRegistro2.next()) {
				intRegistro2 = rsRegistro2.getInt("Num_Venda");
				Campo2[0] = Integer.toString(intRegistro2);
				Campo2[1] = rsRegistro2.getString("Produto");
				Campo2[2] = rsRegistro2.getString("Preco");
				Campo2[3] = rsRegistro2.getString("Quantidade");
				ModeloAgenda2.insertRow(intNumRegistro2, Campo2);
				intNumRegistro2++;
			}

			Comando.close();
			Conexao.close();
		} catch (Exception Excecao) {
			System.out.println("erro:" + Excecao);
		}
		;
		lTabelaEstoque = new JLabel("Estoque:");
		lTabelaVenda = new JLabel("Venda:");
		
		//JPanel labels = new JPanel();
		//labels.setLayout(new BorderLayout(BorderLayout.CENTER));
		//labels.add(lTabelaEstoque);
		//labels.add(lTabelaVenda);
		
		painelFundo = new JPanel();
		painelFundo.setLayout(new GridLayout(2, 1));
		tabela = new JTable(ModeloAgenda);
		barraRolagem = new JScrollPane(tabela);

		tabela2 = new JTable(ModeloAgenda2);
		barraRolagem1 = new JScrollPane(tabela2);
		//painelFundo.add(labels);
		painelFundo.add(barraRolagem);
		//painelFundo.add(lTabelaVenda);
		painelFundo.add(barraRolagem1);

		getContentPane().add(painelFundo);
	}

	public class atualizando implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == btnAtualizar) {
				
				DefaultTableModel tblRemove = (DefaultTableModel) tabela.getModel();
				DefaultTableModel tblRemove2 = (DefaultTableModel) tabela2.getModel();
				
					while (tabela.getModel().getRowCount() > 0) {  
				           ((DefaultTableModel) tabela.getModel()).removeRow(0);  
				       }
				while(tabela2.getModel().getRowCount() > 0) {
					((DefaultTableModel) tabela2.getModel()).removeRow(0);  
				}
				
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
					EnderecoDB = "jdbc:mysql://31.170.163.101:3306/fernandodev_estoque";
					Conexao = DriverManager.getConnection(EnderecoDB, "fernandodev_estoque", "123456");
					Comando = Conexao.createStatement();
					Connection Conexao2;
					Statement Comando2;
					ResultSet rsRegistro3,rsRegistro4;
					
					Conexao2 = DriverManager.getConnection(EnderecoDB, "fernandodev_estoque", "123456");
					Comando2 = Conexao2.createStatement();
					
					rsRegistro4 = Comando2.executeQuery("SELECT * FROM Produtos ORDER BY Referencia");
					String Campo4[] = new String[7];
					int intNumRegistro4 = 0,intRegistro4 = 0;
					rsRegistro4.beforeFirst();
					rsRegistro4.next();
					intRegistro4 = rsRegistro4.getInt("Referencia");
					Campo4[0] = Integer.toString(intRegistro4);
					Campo4[1] = rsRegistro4.getString("Nome");
					Campo4[2] = rsRegistro4.getString("Preco");
					Campo4[3] = rsRegistro4.getString("Tipo");
					Campo4[4] = rsRegistro4.getString("Estoque");
					Campo4[5] = rsRegistro4.getString("Marca");
					Campo4[6] = rsRegistro4.getString("Fornecedor");
					ModeloAgenda.insertRow(0, Campo4);
					
					intNumRegistro4 = 1;
					while(rsRegistro4.next()) {
						intRegistro4 = rsRegistro4.getInt("Referencia");
						Campo4[0] = Integer.toString(intRegistro4);
						Campo4[1] = rsRegistro4.getString("Nome");
						Campo4[2] = rsRegistro4.getString("Preco");
						Campo4[3] = rsRegistro4.getString("Tipo");
						Campo4[4] = rsRegistro4.getString("Estoque");
						Campo4[5] = rsRegistro4.getString("Marca");
						Campo4[6] = rsRegistro4.getString("Fornecedor");
						ModeloAgenda.insertRow(intNumRegistro4, Campo4);
					}
					
					rsRegistro3 = Comando.executeQuery("SELECT * FROM Venda ORDER BY Num_Venda");
					String Campo3[] = new String [4];
					int intNumRegistro3 = 0,intRegistro3 = 0;
					rsRegistro3.beforeFirst();
					rsRegistro3.next();
					intRegistro3 = rsRegistro3.getInt("Num_Venda");
					Campo3[0] = Integer.toString(intRegistro3);
					Campo3[1] = rsRegistro3.getString("Produto");
					Campo3[2] = rsRegistro3.getString("Preco");
					Campo3[3] = rsRegistro3.getString("Quantidade");
					ModeloAgenda2.insertRow(0, Campo3);
					
					intNumRegistro3 = 1;
					while(rsRegistro3.next()) {
						intRegistro3 = rsRegistro3.getInt("Num_Venda");
						Campo3[0] = Integer.toString(intRegistro3);
						Campo3[1] = rsRegistro3.getString("Produto");
						Campo3[2] = rsRegistro3.getString("Preco");
						Campo3[3] = rsRegistro3.getString("Quantidade");
						ModeloAgenda2.insertRow(intNumRegistro3, Campo3);
					}	
				}catch(Exception e ) {
					System.out.println("deu um erro: "+e);
				}
			}
		}
	}

	public static void main(String[] args) {
		Tela_inicial objeto = new Tela_inicial();
		objeto.gerarTela();
		objeto.setVisible(true);
	}
}