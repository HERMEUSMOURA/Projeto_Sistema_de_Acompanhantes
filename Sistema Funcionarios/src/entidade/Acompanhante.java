package entidade;

public class Acompanhante {
	
    private int id; 
    private String nome; 
    private String sobrenome; 
    private String dataNascimento;
    private String email;
    private int leito; 
    

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

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLeito() {
        return leito;
    }

    public void setLeito(int leito) {
        this.leito = leito;
    }

    
    @Override
    public String toString() {
        return this.nome + " " + this.sobrenome;
    }    
}
