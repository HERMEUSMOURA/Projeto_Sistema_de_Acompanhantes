package sistema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import sistema.telas.LeitossEditar;
import sistema.telas.LeitosConsultar;
import sistema.telas.LeitosInserir;
import sistema.telas.AcompanhantesConsultar;
import sistema.telas.AcompanhantesInserir;
import sistema.telas.AcompanhantesEditar;
import sistema.telas.RelatorioLeitos; // Importa a nova classe RelatorioLeitos
import sistema.telas.Inicio;
import sistema.telas.Login;

public class Navegador {
    private static boolean menuConstruido;
    private static boolean menuHabilitado;
    private static JMenuBar menuBar;
    private static JMenu menuArquivo, menuAcompanhantes, menuLeitos, menuRelatorios;
    private static JMenuItem miSair, miAcompanhantesConsultar, miAcompanhantesCadastrar, miLeitosConsultar, miLeitosCadastrar, miRelatoriosLeitos;
    
    public static void login() {
        Sistema.tela = new Login();
        Sistema.frame.setTitle("Acompanhantes Hemoam");
        Navegador.atualizarTela();    
    }
    
    public static void inicio() {
        Sistema.tela = new Inicio();
        Sistema.frame.setTitle("Acompanhantes Hemoam");
        Navegador.atualizarTela();
    }
    
    public static void leitosCadastrar() {
        Sistema.tela = new LeitosInserir();
        Sistema.frame.setTitle("Acompanhantes Hemoam - Cadastrar Leito");
        Navegador.atualizarTela();        
    }
    
    public static void leitosConsultar() {
        Sistema.tela = new LeitosConsultar();
        Sistema.frame.setTitle("Acompanhantes Hemoam - Consultar Leitos");
        Navegador.atualizarTela();    
    }
    
    public static void leitosEditar(Leito leito) {
        Sistema.tela = new LeitosEditar(leito);
        Sistema.frame.setTitle("Acompanhantes Hemoam - Editar Leito");
        Navegador.atualizarTela();
    }
    
    public static void acompanhantesCadastrar() {
        Sistema.tela = new AcompanhantesInserir();
        Sistema.frame.setTitle("Acompanhantes Hemoam - Cadastrar Acompanhantes");
        Navegador.atualizarTela();
    }
        
    public static void acompanhantesConsultar() {
        Sistema.tela = new AcompanhantesConsultar();
        Sistema.frame.setTitle("Acompanhantes Hemoam - Consultar Acompanhantes");
        Navegador.atualizarTela();
    }

    public static void acompanhantesEditar(Acompanhantes acompanhante) {
        Sistema.tela = new AcompanhantesEditar(acompanhante);  
        Sistema.frame.setTitle("Acompanhantes Hemoam - Editar Acompanhantes");           
        Navegador.atualizarTela();
    }

    public static void relatoriosLeitos() {
        Sistema.tela = new RelatorioLeitos();
        Sistema.frame.setTitle("Acompanhantes Hemoam - Relatórios de Acompanhantes por Leito");
        Navegador.atualizarTela();
    }
    
    private static void atualizarTela() {
        Sistema.frame.getContentPane().removeAll();
        Sistema.tela.setVisible(true);
        Sistema.frame.add(Sistema.tela);
        Sistema.frame.setVisible(true);
    }
    
    private static void construirMenu() {
        if (!menuConstruido) {
            menuConstruido = true;
            
            menuBar = new JMenuBar();
            
            menuArquivo = new JMenu("Arquivo");
            menuBar.add(menuArquivo);
            miSair = new JMenuItem("Sair");
            menuArquivo.add(miSair);
            
            menuAcompanhantes = new JMenu("Acompanhantes");
            menuBar.add(menuAcompanhantes);
            miAcompanhantesCadastrar = new JMenuItem("Cadastrar");
            menuAcompanhantes.add(miAcompanhantesCadastrar);
            miAcompanhantesConsultar = new JMenuItem("Consultar");
            menuAcompanhantes.add(miAcompanhantesConsultar);
            
            menuLeitos = new JMenu("Leitos");
            menuBar.add(menuLeitos);
            miLeitosCadastrar = new JMenuItem("Cadastrar");
            menuLeitos.add(miLeitosCadastrar);
            miLeitosConsultar = new JMenuItem("Consultar");
            menuLeitos.add(miLeitosConsultar);
            
            menuRelatorios = new JMenu("Relatórios");
            menuBar.add(menuRelatorios);
            miRelatoriosLeitos = new JMenuItem("Acompanhantes por Leito");
            menuRelatorios.add(miRelatoriosLeitos);
            
            criarEventosMenu();
        }
    }
    
    public static void habilitarMenu() {
        if (!menuConstruido) construirMenu();
        if (!menuHabilitado) {
            menuHabilitado = true;
            Sistema.frame.setJMenuBar(menuBar);
        }
    }
    
    public static void desabilitarMenu() {
        if (menuHabilitado) {
            menuHabilitado = false;
            Sistema.frame.setJMenuBar(null);
        }
    }
    
    private static void criarEventosMenu() {
        miSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        miAcompanhantesCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acompanhantesCadastrar();
            }
        });
        
        miAcompanhantesConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acompanhantesConsultar();
            }
        });
        
        miLeitosCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leitosCadastrar();
            }
        });
        
        miLeitosConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leitosConsultar();
            }
        });

        miRelatoriosLeitos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                relatoriosLeitos();
            }
        });
    }
}
