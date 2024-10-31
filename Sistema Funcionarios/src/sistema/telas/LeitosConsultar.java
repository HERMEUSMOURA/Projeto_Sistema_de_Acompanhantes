package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sistema.Navegador;
import sistema.BancoDeDados;
import entidade.Leito;

public class LeitosConsultar extends JPanel {
    
    private Leito leitoAtual;
    private JLabel labelTitulo, labelLeito;
    private JTextField campoLeito;
    private JButton botaoPesquisar, botaoEditar, botaoExcluir;
    private DefaultListModel<Leito> listasLeitosModelo = new DefaultListModel<>();
    private JList<Leito> listaLeitos;

    public LeitosConsultar(){
        criarComponentes();
        criarEventos();
        Navegador.habilitarMenu();
    }

    private void criarComponentes() {
        setLayout(null);
        
        labelTitulo = new JLabel("Consulta de Leitos", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
        labelLeito = new JLabel("Nome do Leito", JLabel.LEFT);
        campoLeito = new JTextField();
        botaoPesquisar = new JButton("Pesquisar Leito");
        botaoEditar = new JButton("Editar Leito");
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir Leito");
        botaoExcluir.setEnabled(false);
        
        listaLeitos = new JList<>(listasLeitosModelo);
        listaLeitos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelLeito.setBounds(100, 120, 400, 20);
        campoLeito.setBounds(100, 140, 400, 40);
        botaoPesquisar.setBounds(500, 140, 130, 40); 
        listaLeitos.setBounds(100, 200, 400, 240);
        botaoEditar.setBounds(500, 360, 130, 40); 
        botaoExcluir.setBounds(500, 400, 130, 40);
        
        add(labelTitulo);
        add(labelLeito);
        add(campoLeito);
        add(listaLeitos);
        add(botaoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);
        
        setVisible(true);
    }

    private void criarEventos() {
        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlPesquisarLeitos(campoLeito.getText());
            }
        });
        
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navegador.leitosEditar(leitoAtual);
            }
        });
        
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlDeletarLeito();
            }
        });
        
        listaLeitos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                leitoAtual = listaLeitos.getSelectedValue();
                botaoEditar.setEnabled(leitoAtual != null);
                botaoExcluir.setEnabled(leitoAtual != null);
            }
        });
    }

    private void sqlPesquisarLeitos(String nomeLeito) {
        String sql = "SELECT * FROM leitos WHERE nome_leito LIKE ?";
        try (Connection conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
             
            stmt.setString(1, "%" + nomeLeito + "%");
            ResultSet resultados = stmt.executeQuery();

            listasLeitosModelo.clear();

            while (resultados.next()) {                
                Leito leito = new Leito();
                leito.setId(resultados.getInt("id"));
                leito.setNome(resultados.getString("nome_leito"));
                
                listasLeitosModelo.addElement(leito);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar os Leitos.");
            Logger.getLogger(LeitosConsultar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sqlDeletarLeito() {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o Leito " + leitoAtual.getNome() + "?", "Excluir", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM leitos WHERE id=?";
            try (Connection conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
                 PreparedStatement stmt = conexao.prepareStatement(sql)) {
                 
                stmt.setInt(1, leitoAtual.getId());
                stmt.executeUpdate();
                
                listasLeitosModelo.removeElement(leitoAtual);
                listaLeitos.clearSelection();
                JOptionPane.showMessageDialog(null, "Leito deletado com sucesso!");  
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir o Leito.");
                Logger.getLogger(LeitosConsultar.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
