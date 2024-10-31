package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sistema.BancoDeDados;
import entidade.Leito;

public class LeitosInserir extends JPanel {
	
    private JLabel labelTitulo, labelLeito;
    private JTextField campoLeito; 
    private JButton botaoGravar;
	
    public LeitosInserir() {
        criarComponentes();
        criarEventos();
    }
	
    private void criarComponentes() {
        setLayout(null);
		
        labelTitulo = new JLabel("Cadastro de Leito", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelLeito = new JLabel("Nome do leito", JLabel.LEFT);
        campoLeito = new JTextField();
        botaoGravar = new JButton("Adicionar Leito");
		
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
                Leito novoLeito = new Leito();
                novoLeito.setNome(campoLeito.getText());
                sqlInserirLeito(novoLeito);
            }
        });
    }
	
    private void sqlInserirLeito(Leito novoLeito) {
		
        if (campoLeito.getText().length() <= 3) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente.");
            return;
        }
		
        String sql = "INSERT INTO leitos (nome_leito) VALUES (?)";
		
        try (Connection conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
			
            stmt.setString(1, novoLeito.getNome());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Leito adicionado com sucesso!");

            
            campoLeito.setText("");			
		
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o Leito.");	
            Logger.getLogger(LeitosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
