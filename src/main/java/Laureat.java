import org.hibernate.annotations.Immutable;

import javax.persistence.*;

@Entity
@Immutable
public class Laureat {
    @Column(name = "imieINazwisko")
    private String imieINazwisko;

    public Laureat() {
    }

    @Column(name = "nazwaFilmu")
    private String  nazwaFilmu;
    @Column(name = "wiekAktora")
    private int wiekAktora;
    @Id
    @GeneratedValue //strategy = GenerationType.AUTO)
    @Column(name = "idAktora")
    private String idAktora;
    @Column(name = "rokPrzyznania")
    private int rokPrzyznania;
    @Column(name = "plec")
    private String plec;

    @Override
    public String toString() {
        return "Laureat{" +
                "imieINazwisko='" + imieINazwisko + '\'' +
                ", nazwaFilmu='" + nazwaFilmu + '\'' +
                ", wiekAktora=" + wiekAktora +
                ", idAktora='" + idAktora + '\'' +
                ", rokPrzyznania=" + rokPrzyznania +
                ", plec='" + plec + '\'' +
                '}';
    }

    public Laureat(String imieINazwisko, String nazwaFilmu, int wiekAktora, String idAktora, int rokPrzyznania, int id, String plec) {
        this.imieINazwisko = imieINazwisko;
        this.nazwaFilmu = nazwaFilmu;
        this.wiekAktora = wiekAktora;
        this.idAktora = idAktora;
        this.rokPrzyznania = rokPrzyznania;

        this.plec = plec;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }



    public String getImieINazwisko() {
        return imieINazwisko;
    }

    public void setImieINazwisko(String imieINazwisko) {
        this.imieINazwisko = imieINazwisko;
    }

    public String getNazwaFilmu() {
        return nazwaFilmu;
    }

    public void setNazwaFilmu(String nazwaFilmu) {
        this.nazwaFilmu = nazwaFilmu;
    }

    public int getWiekAktora() {
        return wiekAktora;
    }

    public void setWiekAktora(int wiekAktora) {
        this.wiekAktora = wiekAktora;
    }

    public String getIdAktora() {
        return idAktora;
    }

    public void setIdAktora(String idAktora) {
        this.idAktora = idAktora;
    }

    public int getRokPrzyznania() {
        return rokPrzyznania;
    }

    public void setRokPrzyznania(int rokPrzyznania) {
        this.rokPrzyznania = rokPrzyznania;
    }
}

