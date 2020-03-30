import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Tela_inicial extends JFrame{
	
	public static JPanel pnlBotoes,pnlAtualizar;
	public static JButton btnProdutos,btnVenda,btnEstoque,btnRelatorio,btnSair,btnAtualizar;
	public Connection Conexao;
	public String EnderecoDB;
	public ResultSet rsRegistro;
	public Statement Comando;
	

	//configurações da tabela
	JPanel painelFundo;
	JTable tabela;
	static	public DefaultTableModel ModeloAgenda;
	JScrollPane barraRolagem;  
	Object [][] dados = {
	        {"1", "Arroz", "Comida","R$4,00","Kika"},
	        {"2", "Feijão", "Comida","R$6,00","São João"},
	        {"3", "Batata", "Comida","R$2,50","Terra"},
	        {"1", "Arroz", "Comida","R$4,00","Kika"},
	        {"2", "Feijão", "Comida","R$6,00","São João"},
	        {"3", "Batata", "Comida","R$2,50","Terra"},
	        {"1", "Arroz", "Comida","R$4,00","Kika"},
	        {"2", "Feijão", "Comida","R$6,00","São João"}
};
	String colunas []= {"Codigo", "Nome", "Tipo","Valor","Fornecedor"}; 
	
	     //conexao com o banco de dados
	public void gerarTela() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			EnderecoDB = "jdbc:mysql://31.170.163.101:3306/fernandodev_estoque";
			Conexao = DriverManager.getConnection(EnderecoDB, "fernandodev_estoque", "123456");
			Comando = Conexao.createStatement();
			rsRegistro = Comando.executeQuery("SELECT * FROM estoque ORDER BY id_estoque");
			
			
			System.out.println("conexao com o banco de dados estabelecida");
		}catch(Exception Excecao) {
			System.out.println("erro:"+Excecao);
		};
			
		
	setExtendedState(JFrame.MAXIMIZED_BOTH);	
	setVisible(true);
	setLayout(new GridLayout(1, 5));
	Container AreaTela = getContentPane();
	AreaTela.setBackground(Color.white);
	
	ImageIcon imgimagem1 = new ImageIcon(getClass().getResource("produto.png"));
	ImageIcon imgimagem2 = new ImageIcon(getClass().getResource("venda.png"));
	ImageIcon imgimagem3 = new ImageIcon(getClass().getResource("estoque.png"));
	ImageIcon imgimagem4 = new ImageIcon(getClass().getResource("relatorio.png"));
	ImageIcon imgimagem5 = new ImageIcon(getClass().getResource("sair.png"));
	ImageIcon imgimagem6 = new ImageIcon(getClass().getResource("atualizar.png"));
	
		btnProdutos = new JButton(imgimagem1);
		btnVenda = new JButton(imgimagem2);
		btnEstoque = new JButton(imgimagem3);
		btnRelatorio = new JButton(imgimagem4);
		btnSair = new JButton(imgimagem5);
		btnAtualizar = new JButton(imgimagem6);
		
	//	btnProdutos.setToolTipText("Cadastro de produtos");
	//	btnVenda.setToolTipText("Venda");
	//	btnEstoque.setToolTipText("Estoque");
	//	btnRelatorio.setToolTipText("Relatório de estoque");
	//	btnSair.setToolTipText("Sair da aplicação");
	//	btnAtualizar.setToolTipText("Atualizar tabela");
		
		pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlBotoes.add(btnProdutos);
		pnlBotoes.add(btnVenda);
		pnlBotoes.add(btnEstoque);
		pnlBotoes.add(btnRelatorio);
		pnlBotoes.add(btnSair);
		pnlBotoes.add(btnAtualizar);
	
				
		getContentPane().add(pnlBotoes,BorderLayout.NORTH);
		add(pnlBotoes,BorderLayout.NORTH);
		 painelFundo = new JPanel();
		 
		 
		//	pnlAtualizar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		//	pnlAtualizar.add(btnAtualizar);
		//	getContentPane().add(pnlAtualizar,BorderLayout.SOUTH);
		//	add(pnlAtualizar,BorderLayout.SOUTH);
		
		 
	        painelFundo.setLayout(new GridLayout(1, 1));
	        tabela = new JTable(dados, colunas);
	        barraRolagem = new JScrollPane(tabela);
	        painelFundo.add(barraRolagem);
	        getContentPane().add(painelFundo);	
	        
		
	}


	 public static void main(String[] args) {
		    Tela_inicial objeto = new Tela_inicial();
		    objeto.gerarTela();
		    objeto.setVisible(true);
		
		 }

}


