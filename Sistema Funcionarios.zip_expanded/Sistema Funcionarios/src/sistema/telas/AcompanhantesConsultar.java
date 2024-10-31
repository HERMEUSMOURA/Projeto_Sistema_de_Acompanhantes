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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sistema.BancoDeDados;
import sistema.Navegador;
import entidade.Funcionario;

public class AcompanhantesConsultar extends JPanel {
    
    Acompanhnate acompanhanteAtual;
    JLabel labelTitulo, labelAcompanhante;
    JTextField campoFuncionario;
    JButton botaoPesquisar, botaoEditar, botaoExcluir;
    DefaultListModel< Acompanhnate> listasAcompanhantesModelo = new DefaultListModel();
    JList<Acompanhnate> listaAcompanhantes;
            
    public AcompanhnatesConsultar(){
        criarComponentes();
        criarEventos();
        Navegador.habilitarMenu();
    }

    private void criarComponentes() {
        setLayout(null);
        
        labelTitulo = new JLabel("Consulta de Acompanhantes", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
        labelAcompanhante = new JLabel("Nome do Acompanhante", JLabel.LEFT);
        campoAcompanhante = new JTextField();
        botaoPesquisar = new JButton("Pesquisar Acompanhante");
        botaoEditar = new JButton("Editar Acompanhante");
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir Acompanhante");
        botaoExcluir.setEnabled(false);
        listasAcompanhantesModelo = new DefaultListModel();
        listaAcompanhantes = new JList();
        listaAcompanhantes.setModel(listasAcompanhantesModelo);
        listaAcompanhante.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelAcompanhante.setBounds(150, 120, 400, 20);
        campoAcompanhante.setBounds(150, 140, 400, 40);
        botaoPesquisar.setBounds(560, 140, 130, 40); 
        listaAcompanhantes.setBounds(150, 200, 400, 240);
        botaoEditar.setBounds(560, 360, 130, 40); 
        botaoExcluir.setBounds(560, 400, 130, 40);
        
        add(labelTitulo);
        add(labelAcompanhante);
        add(campoAcompanhante);
        add(listaAcompanhante);
        add(botaoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);
        
        setVisible(true);
    }

    private void criarEventos() {
        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlPesquisarAcompanhantes(campoAcompanhante.getText());
            }
        });
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Navegador.AcompanhantesEditar(acompanhanteAtual);
            }
        });
        botaoExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sqlDeletarAcompanhante();
            }
        });
        listaAcompanhantes.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
            	acompanhanteAtual = listaAcompanhantes.getSelectedValue();
                if(acompanhanteAtual == null){
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                }else{
                    botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
            }
        });
    }

    private void sqlPesquisarAcompanhantes(String nome) {        
        
        Connection conexao;
        
        Statement instrucaoSQL;
        
        ResultSet resultados;
        
        try {
            
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            
           instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * FROM acompanhantes WHERE nome like '%"+nome+"%' order by nome ASC");
            
            listasAcompanhantesModelo.clear();

            while (resultados.next()) {                
            	Acompanhante acompanhante = new Acompanhante();
            	acompanhante.setId(resultados.getInt("id"));
            	acompanhante.setNome(resultados.getString("nome"));
            	acompanhante.setSobrenome(resultados.getString("sobrenome"));
            	acompanhante.setDataNascimento(resultados.getString("data_nascimento"));
            	acompanhante.setEmail(resultados.getString("email"));
               
                listasAcompanhantesModelo.addElement(acompanhante);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar acomp�nhantes.");
            Logger.getLogger(AcompanhantesInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sqlDeletarAcompanhante() {
        String pergunta = "Deseja realmente excluir o Acompanhante "+acompanhanteAtual.getNome()+"?";
        int confirmacao = JOptionPane.showConfirmDialog(null, pergunta, "Excluir", JOptionPane.YES_NO_OPTION);
        if(confirmacao == JOptionPane.YES_OPTION){
            
            Connection conexao;
            
            Statement instrucaoSQL;
            
            ResultSet resultados;

            try {
                
                conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);

                
                instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                instrucaoSQL.executeUpdate("DELETE FROM Acompanhantes WHERE id="+acompanhanteAtual.getId()+"");            

                JOptionPane.showMessageDialog(null, "Acompanhante deletado com sucesso!");
                Navegador.inicio();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir Acompanhante.");
                Logger.getLogger(AcompanhantesInserir.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}