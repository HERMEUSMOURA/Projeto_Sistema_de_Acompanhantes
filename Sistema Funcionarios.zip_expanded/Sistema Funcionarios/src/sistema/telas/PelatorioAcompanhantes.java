package sistema.telas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import sistema.Navegador;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import sistema.BancoDeDados;

public class RelatorioLeitos extends JPanel {
    JLabel labelTitulo, labelDescricao;
    
    public RelatorioLeitos() {
        criarComponentes();
        criarEventos();
        Navegador.habilitarMenu();
    }
    
    private void criarComponentes() {
        setLayout(null);
        
        // Ajusta o título e descrição
        labelTitulo = new JLabel("Relatórios", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelDescricao = new JLabel("Esse gráfico representa a quantidade de acompanhantes por leito", JLabel.CENTER);
        
        // Cria o conjunto de dados para o gráfico
        DefaultPieDataset dadosGrafico = this.criarDadosGrafico();
        
        JFreeChart someChart = ChartFactory.createPieChart3D("", dadosGrafico, false, true, false);
        PiePlot plot = (PiePlot) someChart.getPlot();
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(null);
        plot.setOutlinePaint(null);
        someChart.setBackgroundPaint(null);
        
        PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{0}: {1} ({2})",
            new DecimalFormat("0"), new DecimalFormat("0%"));
        plot.setLabelGenerator(gen);
       
        ChartPanel chartPanel = new ChartPanel(someChart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(660, 340);
            }
        };
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelDescricao.setBounds(20, 50, 660, 40);
        chartPanel.setBounds(20, 100, 660, 340);
        
        add(labelTitulo);
        add(labelDescricao);
        add(chartPanel);
        setVisible(true);   
    }
    
    private DefaultPieDataset criarDadosGrafico() {
        DefaultPieDataset dados = new DefaultPieDataset();
        
        Connection conexao;
        Statement instrucaoSQL;
        ResultSet resultados;
        
        try {
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            
            // Consulta SQL atualizada para buscar dados de acompanhantes por leitos
            String query = "SELECT leitos.nome_leito, count(*) AS quantidade " +
                           "FROM leitos " +
                           "LEFT JOIN acompanhantes ON leitos.id = acompanhantes.leito " +
                           "GROUP BY leitos.nome_leito " +
                           "ORDER BY nome_leito ASC";
            System.out.println(query);
            resultados = instrucaoSQL.executeQuery(query);
            
            while (resultados.next()) {
                dados.setValue(resultados.getString("nome_leito"), resultados.getInt("quantidade"));
            }
            
            return dados;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao criar o relatório. \n\n" + ex.getMessage());
            Navegador.inicio();
        }
        
        return null;
    }
    
    private void criarEventos() {
        // Eventos podem ser adicionados conforme a necessidade
    }
}
