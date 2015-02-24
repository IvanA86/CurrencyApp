package clases;

/**
 * Created by Iván Agós on 23/02/2015.
 */
public class Moneda {

    String nomMoneda;
    String idMoneda;
    String nomPais;
    String idPais;

    public Moneda(String nomMoneda, String idMoneda, String nomPais, String idPais) {
        this.nomMoneda = nomMoneda;
        this.idMoneda = idMoneda;
        this.nomPais = nomPais;
        this.idPais = idPais;
    }

    public String getNomMoneda() {
        return nomMoneda;
    }

    public String getIdMoneda() {
        return idMoneda;
    }

    public String getNomPais() {
        return nomPais;
    }

    public String getIdPais() {
        return idPais;
    }

    public void setNomMoneda(String nomMoneda) {
        this.nomMoneda = nomMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public void setNomPais(String nomPais) {
        this.nomPais = nomPais;
    }

    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }
}
