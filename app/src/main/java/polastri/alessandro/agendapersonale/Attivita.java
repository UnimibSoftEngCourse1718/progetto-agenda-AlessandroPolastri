package polastri.alessandro.agendapersonale;

import android.os.Parcel;
import android.os.Parcelable;

public class Attivita implements Parcelable {

    private String nome;
    private String data;
    private String nota;

    public Attivita(){
        this.nome = "";
        this.data = "";
        this.nota = "";
    }
    public Attivita(String nome, String data, String nota){
        this.nome = nome;
        this.data = data;
        this.nota = nota;
    }
    public void setNome(String nome){
        if(nome != null) this.nome = nome;
    }

    public void setData(String data){
        if(data != null) this.data = data;
    }

    public void setNota(String nota){
        if(nota != null) this.nota = nota;
    }

    public String getNome(){
        return nome;
    }

    public String getData(){
        return data;
    }

    public String getNota(){
        return nota;
    }

    public String toString(){
        return (getNome() + getData() + getNota());
    }

    public static final Parcelable.Creator<Attivita> CREATOR
            = new Parcelable.Creator<Attivita>() {
        public Attivita createFromParcel(Parcel in) {
            return new Attivita(in);
        }

        public Attivita[] newArray(int size) {
            return new Attivita[size];
        }
    };

    private Attivita(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        nome = in.readString();
        data = in.readString();
        nota = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(nome);
        out.writeString(data);
        out.writeString(nota);
    }

    public int describeContents() {
        return 0;
    }
}
