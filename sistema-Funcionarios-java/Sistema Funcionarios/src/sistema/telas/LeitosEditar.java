package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sistema.Navegador;
import sistema.BancoDeDados;
import entidade.Leito;
	
public class LeitosEditar extends JPanel {
	
	Leito leitoAtual;
	JLabel labelTitulo, labelLeito;
	JTextField campoLeito;
	JButton botaoGravar;
	
	public LeitosEditar(Leito leito) {
		leitoAtual = leito;
		criarComponentes();
		criarEventos();
	}
	
	private void criarComponentes() {
		setLayout(null);
		
		labelTitulo = new JLabel("Editar de Leitos", JLabel.CENTER);
		labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
		labelLeito = new JLabel("Nome do Leito", JLabel.LEFT);
		campoLeito = new JTextField(leitoAtual.getNome());
		botaoGravar = new JButton("Salvar");
		
		labelTitulo.setBounds(20, 20, 660, 40);
		labelLeito.setBounds(150, 120, 400, 20);
		campoLeito.setBounds(150, 140, 400, 40);
		botaoGravar.setBounds(250, 380, 200, 40);
		
		add(labelTitulo);
		add(labelLeito);
		add(campoLeito);
		add(botaoGravar);
		
		setVisible(true);
	}
	
	private void criarEventos() {
		botaoGravar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				leitoAtual.setNome(campoLeito.getText());
				sqlAtualizarLeito();
			}
		});
	}
	
	private void sqlAtualizarLeito( ) {
		if(campoLeito.getText().length() <=2) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente");
			return;
		}
		
		Connection conexao;
		Statement instrucaoSQL;
		ResultSet resultados;
		
		try {
			conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
			instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			instrucaoSQL.executeUpdate("UPDATE leitos set nome_leito='"+campoLeito.getText()+"' WHERE id="+leitoAtual.getId()+"");
			
			JOptionPane.showMessageDialog(null, "Leito atualizado com sucesso!");
			
			conexao.close();
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o Leito.");
			Logger.getLogger(LeitosInserir.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}

}




















