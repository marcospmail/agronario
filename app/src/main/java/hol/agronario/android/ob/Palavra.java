package hol.agronario.android.ob;

import java.util.Date;

/**
 * Created by Marcos on 06/02/2015.
 */
public class Palavra {

    private int id;
    private String palavra;
    private String textoPalavra;
    private Date data;
    private String pronuncia;
    private int idIdioma;
    private int idClasseGramatical;
    private int status;

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getIdIdioma() {
        return idIdioma;
    }

    public void setIdIdioma(int idIdioma) {
        this.idIdioma = idIdioma;
    }

    public int getIdClasseGramatical() {
        return idClasseGramatical;
    }

    public void setIdClasseGramatical(int idClasseGramatical) {
        this.idClasseGramatical = idClasseGramatical;
    }

    public String getPronuncia() {
        return pronuncia;
    }

    public void setPronuncia(String pronuncia) {
        this.pronuncia = pronuncia;
    }

    public String getTextoPalavra() {
        return textoPalavra;
    }

    public void setTextoPalavra(String textoPalavra) {
        this.textoPalavra = textoPalavra;
    }
}
