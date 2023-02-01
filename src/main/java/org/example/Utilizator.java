package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public abstract class Utilizator {
    String nume;
    private ArrayList<Cerere> cereriAsteptare;
    private ArrayList<Cerere> cereriSolutionate;

    public Utilizator(String nume) {
        this.nume = nume;
        cereriAsteptare = new ArrayList<>();
        cereriSolutionate = new ArrayList<>();
    }

    public ArrayList<Cerere> getCereriAsteptare() {
        return cereriAsteptare;
    }

    public void setCereriAsteptare(ArrayList<Cerere> cereriAsteptare) {
        this.cereriAsteptare = cereriAsteptare;
    }

    public ArrayList<Cerere> getCereriSolutionate() {
        return cereriSolutionate;
    }

    public void setCereriSolutionate(ArrayList<Cerere> cereriSolutionate) {
        this.cereriSolutionate = cereriSolutionate;
    }

    /* enumerare interna pentru tipul cererii */
    public String tipCerere(TipCerere tip) {
        String tipCerere = null;
        if (tip.equals(TipCerere.BULETIN)) {
            tipCerere = "inlocuire buletin";
        } else if (tip.equals(TipCerere.SALARIU)) {
            tipCerere = "inregistrare venit salarial";
        } else if (tip.equals(TipCerere.CARNETSOFER)) {
            tipCerere = "inlocuire carnet de sofer";
        } else if (tip.equals(TipCerere.CARNETELEV)) {
            tipCerere = "inlocuire carnet de elev";
        } else if (tip.equals(TipCerere.ACTCONSTITUTIV)) {
            tipCerere = "creare act constitutiv";
        } else if (tip.equals(TipCerere.AUTORIZATIE)) {
            tipCerere = "reinnoire autorizatie";
        } else if (tip.equals(TipCerere.CUPOANEPENSIE)) {
            tipCerere = "inregistrare cupoane de pensie";
        }
        return tipCerere;
    }

    /* scrie textul cererii in functie de utilizator */
    public abstract String scrieTextCerere(TipCerere tip) throws CerereIncompatibila;

    /* creeaza o cerere si scrie in fisier exceptia in cazul in care
    tipul este incompatibil cu utilizatorul */
    public Cerere creeazaCerere(TipCerere tip, int prioritate, Date data, String fileNameOutput) {
        Cerere cerere = null;
        try {
            String textCerere = scrieTextCerere(tip);
            cerere = new Cerere(data, prioritate, textCerere);
        } catch (CerereIncompatibila exceptie) {
            //scriu exceptia in fisier
            try (FileWriter fw = new FileWriter(fileNameOutput, true)) {
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                out.println(exceptie.getMessage());
                out.close();
            } catch (IOException ioe) {
                System.out.println("An error occurred.");
                ioe.printStackTrace();
            }
        }
        return cerere;
    }

    /* scrie in fisier mesajul pentru cererile in asteptare */
    public void afiseazaUtilizatorAsteptare(String fileNameOutput) {
        try (FileWriter fw = new FileWriter(fileNameOutput, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(nume + " - cereri in asteptare:");
            out.close();
        } catch (IOException ioe) {
            System.out.println("An error occurred.");
            ioe.printStackTrace();
        }
    }

    /* scrie in fisier mesajul pentru cererile finalizate */
    public void afiseazaUtilizatorFinalizare(String fileNameOutput) {
        try (FileWriter fw = new FileWriter(fileNameOutput, true)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            out.println(nume + " - cereri in finalizate:");
            out.close();
        } catch (IOException ioe) {
            System.out.println("An error occurred.");
            ioe.printStackTrace();
        }
    }

    /* functie care sterge cererea facuta de utilizator la o anumita data
    din cererile de asteptare ale acestuia */
    public static Cerere retrageCerere(ArrayList<Utilizator> utilizatori, String strData, String
                              numeUtilizator) {
        Cerere cerere = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        for (int i = 0; i < utilizatori.size(); i++) {
            if (numeUtilizator.equals(utilizatori.get(i).nume)) {
                for (int j = 0; j < utilizatori.get(i).cereriAsteptare.size(); j++) {
                    String dataCerere = format.format(utilizatori.get(i).cereriAsteptare.get(j).data);
                    if (strData.equals(dataCerere)) {
                        cerere = utilizatori.get(i).cereriAsteptare.get(j);
                        utilizatori.get(i).cereriAsteptare.remove(j);
                        break;
                    }
                }
                break;
            }
        }
        return cerere;
    }

    /* functie care sterge cererea din cererile in asteptare ale utilizatorului si o adauga
    in cele solutionate */
    public static void retrageCerereAdaugaFinalizata(ArrayList<Utilizator> utilizatori, String strData,
                                                       String numeUtilizator) {
        Cerere cerere = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        for (int i = 0; i < utilizatori.size(); i++) {
            if (numeUtilizator.equals(utilizatori.get(i).nume)) {
                for (int j = 0; j < utilizatori.get(i).cereriAsteptare.size(); j++) {
                    String dataCerere = format.format(utilizatori.get(i).cereriAsteptare.get(j).data);
                    if (strData.equals(dataCerere)) {
                        cerere = utilizatori.get(i).cereriAsteptare.get(j);
                        utilizatori.get(i).cereriSolutionate.add(cerere);
                        utilizatori.get(i).cereriAsteptare.remove(j);
                        break;
                    }
                }
                break;
            }
        }
    }
}
