package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Cerere implements Comparable<Cerere> {
    Utilizator utilizator;
    Date data;
    int prioritate;
    String text;

    public Cerere(Utilizator utilizator, Date data, int prioritate, String text) {
        this.utilizator = utilizator;
        this.data = data;
        this.prioritate = prioritate;
        this.text = text;
    }

    public Cerere(Date data, int prioritate, String text) {
        this.data = data;
        this.prioritate = prioritate;
        this.text = text;
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    /* pentru ordonare crescator in functie de data */
    @Override
    public int compareTo(Cerere cerere) {
        return data.compareTo(cerere.data);
    }

    /* afiseaza informatiile unei cereri */
    public void afiseazaCerere(String fileNameOutput) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String strData = format.format(data);
        try (FileWriter fw = new FileWriter(fileNameOutput, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.print(strData + " - ");
            out.print(text);
            out.println();
            out.close();
        } catch (IOException ioe) {
            System.out.println("An error occurred.");
            ioe.printStackTrace();
        }
    }

    /* afiseaza informatiile unei cereri cand este rezolvata de un
    functionar */
    public void afiseazaCerereFunctionar(String fileNameOutput) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String strData = format.format(data);
        try (FileWriter fw = new FileWriter(fileNameOutput, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.print(strData + " - ");
            out.print(utilizator.nume);
            out.println();
            out.close();
        } catch (IOException ioe) {
            System.out.println("An error occurred.");
            ioe.printStackTrace();
        }
    }

    /* afiseaza cererea in cadrul biroului */
    public void afiseazaCerereBirou(String fileNameOutput) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        String strData = format.format(data);
        try (FileWriter fw = new FileWriter(fileNameOutput, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.print(prioritate + " - ");
            out.print(strData + " - ");
            out.print(text);
            out.println();
            out.close();
        } catch (IOException ioe) {
            System.out.println("An error occurred.");
            ioe.printStackTrace();
        }
    }

}
