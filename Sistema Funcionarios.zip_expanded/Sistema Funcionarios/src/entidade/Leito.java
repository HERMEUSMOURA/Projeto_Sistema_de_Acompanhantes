package entidade;

public class Leito {
	 // vari�vel destinado ao id do leito
    private int id; 
    // vari�vel destinado ao nome do leito
    private String nome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    @Override
    public String toString() {
        return this.nome;
    }    

}
