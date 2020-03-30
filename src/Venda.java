import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;


public class Venda extends JFrame{
	JTextField cPreco,cQTDE,cID;
	JLabel lPreco,lQTDE,lProduto;
	JButton btnAdicionar,btnApagar;
	JPanel pnlPreco,pnlQTDE,pnlID,pnlBotoes,pnlGeral;
	
	public Connection Conexao;
	public String EnderecoDB,strRegistro,strComandoSQL,Spreco,Sqtde,Sid;
	public int rsRegistro;
	public Statement Comando;
	
	public Venda(Frame Proprietario,String Titulo,boolean Modal) {
		setLayout(null);
		setSize(800,200);
		setBackground(Color.GRAY);
		setVisible(false);
		setResizable(true);
		setLayout(new GridLayout(2,1));
		setTitle("Vendas");
		setLocation(50,190);
		
		cPreco = new JTextField(10);
		cQTDE = new JTextField(10);
		cID = new JTextField(10);
		
		pnlPreco = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlPreco.add(new JLabel("Preço:"));
		pnlPreco.add(cPreco);
		
		pnlQTDE = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlQTDE.add(new JLabel("QTDE:"));
		pnlQTDE.add(cQTDE);
		
		pnlID = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlID.add(new JLabel("Código:"));
		pnlID.add(cID);
		
		pnlBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnAdicionar = new JButton("Adicionar");
		btnApagar = new JButton("Apagar");
		
		btnAdicionar.setPreferredSize(new Dimension(300,20));
		btnApagar.setPreferredSize(new Dimension(300,20));
		pnlBotoes.add(btnAdicionar);
		pnlBotoes.add(btnApagar);
		pnlBotoes.setBackground(Color.GRAY);
		
		pnlPreco.setBackground(Color.GRAY);
		pnlQTDE.setBackground(Color.GRAY);
		pnlID.setBackground(Color.GRAY);
		pnlBotoes.setBackground(Color.GRAY);
		
		pnlGeral = new JPanel(new GridLayout(1,1));
		pnlGeral.add(pnlID);
		pnlGeral.add(pnlPreco);
		pnlGeral.add(pnlQTDE);
		
		FuncaoBotoes Fbotoes = new FuncaoBotoes();
		btnAdicionar.addActionListener(Fbotoes);
		btnApagar.addActionListener(Fbotoes);
		
		pnlBotoes.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlGeral.setBackground(Color.gray);
		getContentPane().add(pnlGeral,BorderLayout.NORTH);
		getContentPane().add(pnlBotoes,BorderLayout.SOUTH);
		getContentPane().setBackground(Color.gray);
	}
	public class FuncaoBotoes implements ActionListener{
		public void actionPerformed(ActionEvent eventObjeto) {
			if(eventObjeto.getSource() == btnAdicionar) {
				Spreco = cPreco.getText().toString();
				Sqtde = cQTDE.getText().toString();
				Sid = cID.getText().toString();
				
				if((Spreco == "")&&(Sqtde == "")&&(Sid == "")) {
					System.out.println("Tem coisa errada ai");
					JOptionPane.showMessageDialog(null, "Preencha todos os valores corretamente!!");
				}else {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver");
						EnderecoDB = "jdbc:mysql://31.170.163.101:3306/fernandodev_estoque";
						Conexao = DriverManager.getConnection(EnderecoDB, "fernandodev_estoque", "123456");
						Comando = Conexao.createStatement();
						strComandoSQL = "INSERT INTO Venda (Produto,Preco,Quantidade)"+"VALUES("+"\""+Sid+"\","+
								"\""+Spreco+"\","+
								"\""+Sqtde+"\")";
						Comando = Conexao.createStatement();
						rsRegistro = Comando.executeUpdate(strComandoSQL);
						Comando.close();
						Conexao.close();
						JOptionPane.showMessageDialog(null, "Venda realizada com sucesso!!");
					} catch (Exception e) {
						System.out.println("erro:"+e);
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
					
					strComandoSQL = "DELETE FROM Venda WHERE Num_Venda = "+strRegistro;
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
		}
	}
	
}

