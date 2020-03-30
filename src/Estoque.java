import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class Estoque extends JFrame{
	public JTable tabela;
	static	public DefaultTableModel ModeloAgenda;
	public static JScrollPane barraRolagem;
	public Connection Conexao;
	public String EnderecoDB;
	public ResultSet rsRegistro;
	public Statement Comando;
	
	int intTotalRegistro,intNumRegistro,intRegistro, intTotalRegistro2,intNumRegistro2,intRegistro2;
	String NomeColuna[] = {"Referencia","Nome","Preço","Tipo","Estoque","Marca","Fornecedor"};
	String Campo[] = new String[7];
	
	

	public  Estoque(Frame Proprietario,String Titulo,boolean Modal) {
		
		setLayout(null);
		setSize(682,620);
		setBackground(Color.GRAY);
		setVisible(false);
		setResizable(true);
		setLayout(new GridLayout(1,1));
		setTitle("Vendas");
		setLocation(2,114);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			EnderecoDB = "jdbc:mysql://31.170.163.101:3306/fernandodev_estoque";
			Conexao = DriverManager.getConnection(EnderecoDB, "fernandodev_estoque", "123456");
			Comando = Conexao.createStatement();
			rsRegistro = Comando.executeQuery("SELECT * FROM Produtos ORDER BY Referencia");
			
			intTotalRegistro = 0;
			while(rsRegistro.next()) {
				intTotalRegistro++;
				
			
				ModeloAgenda = new DefaultTableModel(NomeColuna,intTotalRegistro);
				
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
				//Campo[7] = rsRegistro.getString("Tamanho");
				ModeloAgenda.insertRow(0,Campo);
				
				intNumRegistro = 1;
				while(rsRegistro.next()) {
					intRegistro = rsRegistro.getInt("Referencia");
					Campo[0] = Integer.toString(intRegistro);
					Campo[1] = rsRegistro.getString("Nome");
					Campo[2] = rsRegistro.getString("Preco");
					Campo[3] = rsRegistro.getString("Tipo");
					Campo[4] = rsRegistro.getString("Estoque");
					Campo[5] = rsRegistro.getString("Marca");
					Campo[6] = rsRegistro.getString("Fornecedor");
					//Campo[7] = rsRegistro.getString("Tamanho");
					ModeloAgenda.insertRow(intNumRegistro,Campo);
					intNumRegistro++;
				}
			}
			
			}catch(Exception Excecao) {
				System.out.println("erro:"+Excecao);
			};
			tabela = new JTable(ModeloAgenda);
	        barraRolagem = new JScrollPane(tabela);
	getContentPane().add(tabela);
}
	}
