package lt.ktu.ks.notes;

/**
 * Created by Juozas on 2015-10-03.
 */
public class Melodija {

    public int ID;
    public String Pavadinimas;
    public float Trukme;

    public Melodija() {
    }

    public Melodija(int ID, String pavadinimas, String natos) {
        this.ID = ID;
        this.Pavadinimas = pavadinimas;
        natos = natos.replace(',', ' ');
        String melody[] = natos.split(" ");
        int natuSkaicius = Integer.parseInt(melody[0]);
        this.Trukme = 0;
        for(int i = natuSkaicius + 1; i < melody.length; i++)
        {
            this.Trukme += 1/Float.parseFloat(melody[i]);
        }

    }
}
