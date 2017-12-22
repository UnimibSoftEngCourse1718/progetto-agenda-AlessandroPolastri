package polastri.alessandro.agendapersonale;

import android.os.Parcel;
import android.os.Parcelable;

public class Contatto implements Parcelable {

    private String nome;
    private String cognome;
    private String tipo;
    private long numTel;

    public Contatto(){
        this.nome = "";
        this.cognome = "";
        this.tipo = "";
    }

    public Contatto(String nome, String cognome, String tipo, long numTel){
        setNome(nome);
        setCognome(cognome);
        setTipo(tipo);
        setNumTel(numTel);
    }

    public void setNome(String nome){
        if(nome != null)
            this.nome = nome;
    }

    public void setCognome(String cognome){
        if(cognome != null)
            this.cognome = cognome;
    }

    public void setTipo(String tipo){
        if(tipo != null)
            this.tipo = tipo;
    }

    public void setNumTel(long numTel){
        if(numTel > 0)
            this.numTel = numTel;
    }

    public String getNome(){
        return nome;
    }

    public String getCognome(){
        return cognome;
    }

    public long getNumTel(){
        return numTel;
    }

    public String toString(){
        return (getNome() + " " + getCognome() + " " + getNumTel());
    }

    public static final Parcelable.Creator<Contatto> CREATOR
            = new Parcelable.Creator<Contatto>() {
        public Contatto createFromParcel(Parcel in) {
            return new Contatto(in);
        }

        public Contatto[] newArray(int size) {
            return new Contatto[size];
        }
    };

    private Contatto(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        nome = in.readString();
        cognome = in.readString();
        tipo = in.readString();
        numTel = in.readLong();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(nome);
        out.writeString(cognome);
        out.writeString(tipo);
        out.writeLong(numTel);
    }

    public int describeContents() {
        return 0;
    }
}
