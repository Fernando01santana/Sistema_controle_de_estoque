import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FuncaoBotoes implements ActionListener{
Tela_inicial a = new Tela_inicial();
CRUD_Produtos cadastro = new CRUD_Produtos(null,"Cadastro",true);


	@Override
	public void actionPerformed(ActionEvent evento) {
		
		if(evento.getSource() == a.btnProdutos) {
			CRUD_Produtos AdicionarProduto = new CRUD_Produtos(null, "Adicao produto", true);
			AdicionarProduto.setResizable(false);
			AdicionarProduto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			AdicionarProduto.setVisible(true);
			AdicionarProduto.setBackground(Color.GRAY);
		}
		if(evento.getSource() == a.btnVenda) {
			Venda vendas = new Venda(null,"Venda",true);
			vendas.setResizable(false);
			vendas.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			vendas.setVisible(true);
			vendas.setBackground(Color.GRAY);
		}
	
		
		if(evento.getSource() == a.btnEstoque) {
			Estoque tabela = new Estoque(null,"estoque",true);
			tabela.setResizable(false);
			tabela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			tabela.setVisible(true);
			tabela.setBackground(Color.GRAY);
			
		}
		if(evento.getSource() == a.btnSair) {
			Object Botoes[] = {"sim","n�o"};
			int intResposta = JOptionPane.showOptionDialog(null, "Deseja mesmo sair?", "Sa�da", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, Botoes, Botoes[1]);
			if(intResposta == 0) {
				System.exit(0);
				}
		}

		
	}
}
		
	



