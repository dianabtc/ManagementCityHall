package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Birou<T> {
    /* pentru adaugare ordonata in coada biroului */
    static class CustomCerereComparator implements Comparator<Cerere> {
        public int compare(Cerere cerere1, Cerere cerere2) {
            if (cerere1.prioritate != cerere2.prioritate) {
                return cerere1.prioritate < cerere2.prioritate ? 1 : -1;
            }
            return cerere1.data.compareTo(cerere2.data);
        }
    }

    Queue<Cerere> cereri;
    ArrayList<FunctionarPublic> functionariPublici;

    public Birou() {
        cereri = new PriorityQueue<>(new CustomCerereComparator());
        functionariPublici = new ArrayList<>();
    }

    /* functie care adauga cererea in biroul corespunzator si
    seteaza utilizatorul */
    public Cerere adaugaCerereBirou(Cerere cerere, T utilizator) {
        cerere.setUtilizator((Utilizator)utilizator);
        cereri.add(cerere);
        return cerere;
    }

    /* functie care afiseaza mesajul pentru tipul de cereri din birou */
    public static void afiseazaTipCereri(String tip, String fileNameOutput) {
        try (FileWriter fw = new FileWriter(fileNameOutput, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(tip + " - cereri in birou:");
            out.close();
        } catch (IOException ioe) {
            System.out.println("An error occurred.");
            ioe.printStackTrace();
        }
    }

    /* functie care adauga un functionari in birou */
    public void adaugaFunctionar(FunctionarPublic functionarPublic) {
        functionariPublici.add(functionarPublic);
    }

    /* functie care afiseaza cererea rezolvata de functionar in
    fisierul corespunzator si o scoate din coada biroului */
    public Cerere rezolvaCerere(String numeFunctionar) {
        String fileName = "src/main/resources/output/functionar_";
        fileName = fileName.concat(numeFunctionar);
        fileName = fileName.concat(".txt");
        Cerere cerere = cereri.poll(); //scot cererea din coada
        cerere.afiseazaCerereFunctionar(fileName);
        return cerere;
    }
}
