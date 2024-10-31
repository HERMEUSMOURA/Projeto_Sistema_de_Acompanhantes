package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import sistema.BancoDeDados;
import sistema.Navegador;
import entidade.Leito;
import entidade.Acompanhante;

public class AcompanhantesInserir extends JPanel {

    JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEmail, labelLeito;
    JTextField campoNome, campoSobrenome, campoEmail;
    JFormattedTextField campoDataNascimento;
    JComboBox<Leito> comboBoxLeito;
    JButton botaoGravar;

    public AcompanhantesInserir() {
        criarComponentes();
        criarEventos();
        Navegador.habilitarMenu();
    }

    private void criarComponentes() {
        setLayout(null);

        labelTitulo = new JLabel("Cadastro de Acompanhante", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelNome = new JLabel("Nome:", JLabel.LEFT);
        campoNome = new JTextField();
        labelSobrenome = new JLabel("Sobrenome:", JLabel.LEFT);
        campoSobrenome = new JTextField();
        labelDataNascimento = new JLabel("Data de Nascimento", JLabel.LEFT);
        campoDataNascimento = new JFormattedTextField();
        try {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install(campoDataNascimento);
        } catch (ParseException ex) {
            Logger.getLogger(AcompanhantesInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelEmail = new JLabel("E-mail:", JLabel.LEFT);
        campoEmail = new JTextField();
        labelLeito = new JLabel("Leito:", JLabel.LEFT);
        comboBoxLeito = new JComboBox<>();

        botaoGravar = new JButton("Adicionar");

        labelTitulo.setBounds(20, 20, 660, 40);
        labelNome.setBounds(150, 80, 400, 20);
        campoNome.setBounds(150, 100, 400, 40);
        labelSobrenome.setBounds(150, 140, 400, 20);
        campoSobrenome.setBounds(150, 160, 400, 40);
        labelDataNascimento.setBounds(150, 200, 400, 20);
        campoDataNascimento.setBounds(150, 220, 400, 40);
        labelEmail.setBounds(150, 260, 400, 20);
        campoEmail.setBounds(150, 280, 400, 40);
        labelLeito.setBounds(150, 320, 400, 20);
        comboBoxLeito.setBounds(150, 340, 400, 40);
        botaoGravar.setBounds(560, 400, 130, 40);

        add(labelTitulo);
        add(labelNome);
        add(campoNome);
        add(labelSobrenome);
        add(campoSobrenome);
        add(labelDataNascimento);
        add(campoDataNascimento);
        add(labelEmail);
        add(campoEmail);
        add(labelLeito);
        add(comboBoxLeito);
        add(botaoGravar);

        sqlCarregarLeito();

        setVisible(true);
    }

    private void criarEventos() {
        botaoGravar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Acompanhante novoAcompanhante = new Acompanhante();
                novoAcompanhante.setNome(campoNome.getText());
                novoAcompanhante.setSobrenome(campoSobrenome.getText());
                novoAcompanhante.setDataNascimento(campoDataNascimento.getText());
                novoAcompanhante.setEmail(campoEmail.getText());
                Leito leitoSelecionado = (Leito) comboBoxLeito.getSelectedItem();
                if (leitoSelecionado != null) novoAcompanhante.setLeito(leitoSelecionado.getId());

                sqlInserirAcompanhante(novoAcompanhante);
            }
        });
    }

    private void sqlCarregarLeito() {
        Connection conexao;
        Statement instrucaoSQL;
        ResultSet resultados;

        try {
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * from leitos order by nome_leito asc");
            comboBoxLeito.removeAllItems();

            while (resultados.next()) {
                Leito leito = new Leito();
                leito.setId(resultados.getInt("id"));
                leito.setNome(resultados.getString("nome_leito"));
                comboBoxLeito.addItem(leito);
            }
            comboBoxLeito.updateUI();

            conexao.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao carregar os leitos");
            Logger.getLogger(AcompanhantesInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sqlInserirAcompanhante(Acompanhante novoAcompanhante) {
        if (campoNome.getText().length() <= 2) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente");
            return;
        }
        if (campoSobrenome.getText().length() <= 3) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o sobrenome corretamente");
            return;
        }

        Boolean emailValidado = false;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(campoEmail.getText());
        emailValidado = m.matches();

        if (!emailValidado) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o email corretamente.");
            return;
        }

        Connection conexao;
        PreparedStatement instrucaoSQL;

        try {
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);

            String template = "INSERT INTO acompanhantes (nome, sobrenome, data_nascimento, email, leito) VALUES (?, ?, ?, ?, ?)";
            instrucaoSQL = conexao.prepareStatement(template);
            instrucaoSQL.setString(1, novoAcompanhante.getNome());
            instrucaoSQL.setString(2, novoAcompanhante.getSobrenome());
            instrucaoSQL.setString(3, novoAcompanhante.getDataNascimento());
            instrucaoSQL.setString(4, novoAcompanhante.getEmail());
            if (novoAcompanhante.getLeito() > 0) {
                instrucaoSQL.setInt(5, novoAcompanhante.getLeito());
            } else {
                instrucaoSQL.setNull(5, java.sql.Types.INTEGER);
            }
            instrucaoSQL.executeUpdate();

            JOptionPane.showMessageDialog(null, "Acompanhante adicionado com sucesso!");
            Navegador.inicio();

            conexao.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o Acompanhante.");
            Logger.getLogger(AcompanhantesInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
