package hol.agronario.android.ob;

import java.util.Date;

/**
 * Created by Marcos on 06/02/2015.
 */
public class ClasseGramatical {

    private int id;
    private int idIdioma;
    private String nome;
    private Date data;

    public ClasseGramatical() {
    }

    public ClasseGramatical(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }


    public int getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma = idIdioma;
    }

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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return getNome();
    }
}
