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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
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

public class AcompanhantesEditar extends JPanel {
	Acompanhante acompanhanteAtual;
	 JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEmail, labelLeito;
	 JTextField campoNome, campoSobrenome, campoEmail;
	 JFormattedTextField campoDataNascimento;
	 JComboBox<Leito> comboboxLeito;
	 JButton botaoGravar;  
	            
	 public AcompanhantesEditar(Acompanhante acompanhante){
		 acompanhanteAtual = acompanhante;
	     criarComponentes();
	     criarEventos();
	     Navegador.habilitarMenu();
	  }

	  private void criarComponentes() {
	     setLayout(null);
	     String textoLabel = "Editar Acompanhante "+acompanhanteAtual.getNome()+" "+acompanhanteAtual.getSobrenome();
	     labelTitulo = new JLabel(textoLabel, JLabel.CENTER);
	     labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));      
	     labelNome = new JLabel("Nome:", JLabel.LEFT);
	     campoNome = new JTextField(acompanhanteAtual.getNome());     
	     labelSobrenome = new JLabel("Sobrenome:", JLabel.LEFT); 
	     campoSobrenome = new JTextField(acompanhanteAtual.getSobrenome());     
	     labelDataNascimento = new JLabel("Data de Nascimento:", JLabel.LEFT);
	     campoDataNascimento = new JFormattedTextField(acompanhanteAtual.getDataNascimento());
	     try {
	            MaskFormatter dateMask= new MaskFormatter("##/##/####");
	            dateMask.install(campoDataNascimento);
	        } catch (ParseException ex) {
	            Logger.getLogger(AcompanhantesInserir.class.getName()).log(Level.SEVERE, null, ex);
	        }
	     labelEmail = new JLabel("E-mail:", JLabel.LEFT);
	     campoEmail = new JTextField(acompanhanteAtual.getEmail());     
	     labelLeito = new JLabel("Leito:", JLabel.LEFT);
	     comboboxLeito = new JComboBox();     
	     botaoGravar = new JButton("Salvar");
	        
	     
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
	    comboboxLeito.setBounds(150, 340, 400, 40);
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
	    add(comboboxLeito);
	    add(botaoGravar);
	        
	    sqlCarregarLeitos(acompanhanteAtual.getLeito());
	       
	    setVisible(true);
	    
	  }

	  private void criarEventos() {
	    botaoGravar.addActionListener(new ActionListener() {
	    @Override
	    	public void actionPerformed(ActionEvent e) {
	    	acompanhanteAtual.setNome(campoNome.getText());
	    	acompanhanteAtual.setSobrenome(campoSobrenome.getText());
	    	acompanhanteAtual.setDataNascimento(campoDataNascimento.getText());
	    	acompanhanteAtual.setEmail(campoEmail.getText());
	            
	            sqlAtualizarFuncionario();
	                        
	         }
	     });
	   }

	   private void sqlCarregarLeitos(int leitoAtual) {        
	        // conex�o
		   Connection conexao;
	        // instrucao SQL
	       Statement instrucaoSQL;
	        // resultados
	       ResultSet resultados;
	        
	       try {
	            // conectando ao banco de dados
	    	   conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha); 
	           instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
	           resultados = instrucaoSQL.executeQuery("SELECT * from leitos order by nome_leito asc");
	           
	           comboboxLeitos.removeAll();
	           
	           while (resultados.next()) {
	        	   Leito leito = new Leito();
	               leito.setId(resultados.getInt("id"));
	               leito.setNome(resultados.getString("nome_leito"));
	               comboboxLeito.addItem(leito);
	                
	               if(leitoAtual == leito.getId()) comboboxLeito.setSelectedItem(leito);
	            }
	            
	           comboboxLeito.updateUI();
	            
	           conexao.close();
	            
	        } catch (SQLException ex) {
	        	JOptionPane.showMessageDialog(null, "Ocorreu um erro ao carregar os leitos.");
	            Logger.getLogger(AcompanhantesInserir.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

	    private void sqlAtualizarAcompanhante() {
	        
	        // validando nome
	        if(campoNome.getText().length() <= 2){
	            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente.");
	            return;
	        }
	        
	        // validando sobrenome
	        if(campoSobrenome.getText().length() <= 3){
	            JOptionPane.showMessageDialog(null, "Por favor, preencha o sobrenome corretamente.");
	            return;
	        }
	        
	        
	        
	        // validando email
	        Boolean emailValidado = false;
	        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	        Pattern p = Pattern.compile(ePattern);
	        Matcher m = p.matcher(campoEmail.getText());
	        emailValidado = m.matches();
	        
	        if(!emailValidado){
	            JOptionPane.showMessageDialog(null, "Por favor, preencha o email corretamente.");
	            return;
	        }
	        
	        // conex�o
	        Connection conexao;
	        // instrucao SQL
	        PreparedStatement instrucaoSQL;
	        // resultados
	        ResultSet resultados;
	        
	        try {
	            // conectando ao banco de dados
	            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
	            
	            String template = "UPDATE acompanhante set nome=?, sobrenome=?, data_nascimento=?, email=?, cargo=?";
	            template = template+" WHERE id="+acompanhanteAtual.getId();
	            instrucaoSQL = conexao.prepareStatement(template);
	            instrucaoSQL.setString(1, campoNome.getText());
	            instrucaoSQL.setString(2, campoSobrenome.getText());
	            instrucaoSQL.setString(3, campoDataNascimento.getText());
	            instrucaoSQL.setString(4, campoEmail.getText());
	            
	            
	            JOptionPane.showMessageDialog(null, "Acompanhante atualizado com sucesso!");
	            Navegador.inicio();
	            
	            conexao.close();
	            
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o Acompanhante.");
	            Logger.getLogger(AcompanhantesInserir.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }

}
