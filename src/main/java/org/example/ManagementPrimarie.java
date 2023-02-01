package org.example;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ManagementPrimarie {
    ArrayList<Utilizator> utilizatori;
    Birou<Angajat> birouAngajati;
    Birou<Elev> birouElevi;
    Birou<EntitateJuridica> birouEntitatiJuridice;
    Birou<Pensionar> birouPensionari;
    Birou<Persoana> birouPersoane;

    public ManagementPrimarie() {
        utilizatori = new ArrayList<>();
        birouAngajati = new Birou<>();
        birouElevi = new Birou<>();
        birouEntitatiJuridice = new Birou<>();
        birouPensionari = new Birou<>();
        birouPersoane = new Birou<>();
    }

    public TipCerere cerereEnum(String cerere) {
        TipCerere tip = null;
        if (cerere.equals("inlocuire buletin")) {
            tip = TipCerere.BULETIN;
        } else if (cerere.equals("inregistrare venit salarial")) {
            tip = TipCerere.SALARIU;
        } else if (cerere.equals("inlocuire carnet de sofer")) {
            tip = TipCerere.CARNETSOFER;
        } else if (cerere.equals("inlocuire carnet de elev")) {
            tip = TipCerere.CARNETELEV;
        } else if (cerere.equals("creare act constitutiv")) {
            tip = TipCerere.ACTCONSTITUTIV;
        } else if (cerere.equals("reinnoire autorizatie")) {
            tip = TipCerere.AUTORIZATIE;
        } else if (cerere.equals("inregistrare cupoane de pensie")) {
            tip = TipCerere.CUPOANEPENSIE;
        }
        return tip;
    }

    /* afiseaza cererile din coada si reface coada */
    public Queue<Cerere> afiseazaCereri(Queue<Cerere> cereri, ArrayList<Cerere> cereriArray,
                                        String fileNameOutput) {
        while (!cereri.isEmpty()) {
            Cerere cerere = cereri.poll();
            cereriArray.add(cerere);
            cerere.afiseazaCerereBirou(fileNameOutput);
        }
        //refac coada biroului
        cereri.addAll(cereriArray);
        return cereri;
    }

    public static void main(String[] args) throws IOException, ParseException {
        String fileNameInput = "src/main/resources/input/";
        fileNameInput = fileNameInput.concat(args[0]);
        String fileNameOutput = "src/main/resources/output/";
        fileNameOutput = fileNameOutput.concat(args[0]);
        ManagementPrimarie primarie = new ManagementPrimarie();
        try (BufferedReader br = new BufferedReader(new FileReader(fileNameInput))) {
            String line;
            String[] lineArray;
            while ((line = br.readLine()) != null) {
                lineArray = line.split(";");
                if (lineArray[0].equals("adauga_utilizator")) {
                    adaugaUtilizator(primarie, lineArray);
                }
                if (lineArray[0].equals("cerere_noua")) {
                    cerereNoua(fileNameOutput, primarie, lineArray);
                }
                if (lineArray[0].equals("afiseaza_cereri_in_asteptare")) {
                    afiseazaCereriAsteptare(fileNameOutput, primarie, lineArray);
                }
                if (lineArray[0].equals("retrage_cerere")) {
                    retrageCerere(primarie, lineArray);
                }
                if (lineArray[0].equals("afiseaza_cereri")) {
                    afiseazaCereri(fileNameOutput, primarie, lineArray);
                }
                if (lineArray[0].equals("adauga_functionar")) {
                    adaugaFunctionar(primarie, lineArray);
                }
                if (lineArray[0].equals("rezolva_cerere")) {
                    rezolvaCerere(primarie, lineArray);
                }
                if (lineArray[0].equals("afiseaza_cereri_finalizate")) {
                    afiseazaCereriFinalizate(fileNameOutput, primarie, lineArray);
                }
            }
        } catch (IOException ioe) {
            System.out.println("An error occurred.");
            ioe.printStackTrace();
        }
    }

    private static void adaugaUtilizator(ManagementPrimarie primarie, String[] lineArray) {
        //adaug utilizatorul in sistemul de management al primariei
        if (lineArray[1].equals(" angajat")) {
            String nume = lineArray[2].substring(1);
            String companie = lineArray[3].substring(1);
            Angajat angajat = new Angajat(nume, companie);
            primarie.utilizatori.add(angajat);
        } else if (lineArray[1].equals(" elev")) {
            String nume = lineArray[2].substring(1);
            String scoala = lineArray[3].substring(1);
            Elev elev = new Elev(nume, scoala);
            primarie.utilizatori.add(elev);
        } else if (lineArray[1].equals(" entitate juridica")) {
            String nume = lineArray[2].substring(1);
            String reprezentant = lineArray[3].substring(1);
            EntitateJuridica entitateJuridica = new EntitateJuridica(nume, reprezentant);
            primarie.utilizatori.add(entitateJuridica);
        } else if (lineArray[1].equals(" pensionar")) {
            String nume = lineArray[2].substring(1);
            Pensionar pensionar = new Pensionar(nume);
            primarie.utilizatori.add(pensionar);
        } else if (lineArray[1].equals(" persoana")) {
            String nume = lineArray[2].substring(1);
            Persoana persoana = new Persoana(nume);
            primarie.utilizatori.add(persoana);
        }
    }

    private static void cerereNoua(String fileNameOutput, ManagementPrimarie primarie, String[] lineArray) {
        int i;
        Date data = null;
        Cerere cerere = null;
        String numeUtilizator = lineArray[1].substring(1);
        String tipCerere = lineArray[2].substring(1);
        TipCerere tip = primarie.cerereEnum(tipCerere);
        String strData = lineArray[3].substring(1);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);
        try {
            data = format.parse(strData);
        } catch (ParseException exc) {
            System.out.println("Unparseable data");
            exc.printStackTrace();
        }
        String strPrioritate = lineArray[4].substring(1);
        int prioritate = Integer.parseInt(strPrioritate);
        //caut utilizatorul care vrea sa faca cererea in colectia primariei
        for (i = 0; i < primarie.utilizatori.size(); i++) {
            if (numeUtilizator.equals(primarie.utilizatori.get(i).nume)) {
                cerere = primarie.utilizatori.get(i).creeazaCerere(tip,
                        prioritate, data, fileNameOutput);
                break;
            }
        }
        if (cerere != null) { //caz de reusita
            //adaug cererea in biroul corespunzator utilizatorului
            //care a inaintat-o
            if (primarie.utilizatori.get(i) instanceof Angajat) {
                cerere = primarie.birouAngajati.adaugaCerereBirou(cerere,
                        (Angajat) primarie.utilizatori.get(i));
            } else if (primarie.utilizatori.get(i) instanceof Elev) {
                cerere = primarie.birouElevi.adaugaCerereBirou(cerere,
                        (Elev) primarie.utilizatori.get(i));
            } else if (primarie.utilizatori.get(i) instanceof EntitateJuridica) {
                cerere = primarie.birouEntitatiJuridice.adaugaCerereBirou(cerere,
                        (EntitateJuridica) primarie.utilizatori.get(i));
            } else if (primarie.utilizatori.get(i) instanceof Pensionar) {
                cerere = primarie.birouPensionari.adaugaCerereBirou(cerere,
                        (Pensionar) primarie.utilizatori.get(i));
            } else if (primarie.utilizatori.get(i) instanceof Persoana) {
                cerere = primarie.birouPersoane.adaugaCerereBirou(cerere,
                        (Persoana) primarie.utilizatori.get(i));
            }
            //adaug cererea in cererile de asteptare ale utilizatorului si sortez
            primarie.utilizatori.get(i).getCereriAsteptare().add(cerere);
            Collections.sort(primarie.utilizatori.get(i).getCereriAsteptare());
        }
    }

    private static void afiseazaCereriAsteptare(String fileNameOutput, ManagementPrimarie primarie, String[] lineArray) {
        int i;
        String numeUtilizator = lineArray[1].substring(1);
        for (i = 0; i < primarie.utilizatori.size(); i++) {
            //gasesc utilizatorul
            if (numeUtilizator.equals(primarie.utilizatori.get(i).nume)) {
                primarie.utilizatori.get(i).afiseazaUtilizatorAsteptare(fileNameOutput);
                for (int j = 0; j < primarie.utilizatori.get(i).getCereriAsteptare().size(); j++) {
                    //afisez fiecare cerere
                    primarie.utilizatori.get(i).getCereriAsteptare().get(j).afiseazaCerere(fileNameOutput);
                }
                break;
            }
        }
    }

    private static void retrageCerere(ManagementPrimarie primarie, String[] lineArray) {
        int i;
        String numeUtilizator = lineArray[1].substring(1);
        String strData = lineArray[2].substring(1);
        //sterg cerera din cererile de asteptare ale utilizatorului
        Cerere cerere = Utilizator.retrageCerere(primarie.utilizatori, strData, numeUtilizator);
        for (i = 0; i < primarie.utilizatori.size(); i++) {
            if (numeUtilizator.equals(primarie.utilizatori.get(i).nume)) {
                break;
            }
        }
        //sterg cererea din biroul corespunzator
        if (primarie.utilizatori.get(i) instanceof Angajat) {
            primarie.birouAngajati.cereri.remove(cerere);
        } else if (primarie.utilizatori.get(i) instanceof Elev) {
            primarie.birouElevi.cereri.remove(cerere);
        } else if (primarie.utilizatori.get(i) instanceof EntitateJuridica) {
            primarie.birouEntitatiJuridice.cereri.remove(cerere);
        } else if (primarie.utilizatori.get(i) instanceof Pensionar) {
            primarie.birouPensionari.cereri.remove(cerere);
        } else if (primarie.utilizatori.get(i) instanceof Persoana) {
            primarie.birouPersoane.cereri.remove(cerere);
        }
    }

    private static void afiseazaCereri(String fileNameOutput, ManagementPrimarie primarie, String[] lineArray) {
        //ArrayList auxiliar pentru a putea reface coada fiecarui birou
        ArrayList<Cerere> cereriArray = new ArrayList<>();
        String tipUtilizator = lineArray[1].substring(1);
        //afisez mesajul
        Birou.afiseazaTipCereri(tipUtilizator, fileNameOutput);
        if (tipUtilizator.equals("angajat")) {
            primarie.birouAngajati.cereri = primarie.afiseazaCereri(primarie.birouAngajati.cereri,
                    cereriArray, fileNameOutput);
        } else if (tipUtilizator.equals("elev")) {
            primarie.birouElevi.cereri = primarie.afiseazaCereri(primarie.birouElevi.cereri,
                    cereriArray, fileNameOutput);
        } else if (tipUtilizator.equals("entitate juridica")) {
            primarie.birouEntitatiJuridice.cereri = primarie.afiseazaCereri(primarie.birouEntitatiJuridice.cereri,
                    cereriArray, fileNameOutput);
        } else if (tipUtilizator.equals("pensionar")) {
            primarie.birouPensionari.cereri = primarie.afiseazaCereri(primarie.birouPensionari.cereri,
                    cereriArray, fileNameOutput);
        } else if (tipUtilizator.equals("persoana")) {
            primarie.birouPersoane.cereri = primarie.afiseazaCereri(primarie.birouPersoane.cereri,
                    cereriArray, fileNameOutput);
        }
    }

    private static void adaugaFunctionar(ManagementPrimarie primarie, String[] lineArray) {
        String tipUtilizator = lineArray[1].substring(1);
        String nume = lineArray[2].substring(1);
        FunctionarPublic functionarPublic = new FunctionarPublic(nume);
        if (tipUtilizator.equals("angajat")) {
            primarie.birouAngajati.adaugaFunctionar(functionarPublic);
        } else if (tipUtilizator.equals("elev")) {
            primarie.birouElevi.adaugaFunctionar(functionarPublic);
        } else if (tipUtilizator.equals("entitate juridica")) {
            primarie.birouEntitatiJuridice.adaugaFunctionar(functionarPublic);
        } else if (tipUtilizator.equals("pensionar")) {
            primarie.birouPensionari.adaugaFunctionar(functionarPublic);
        } else if (tipUtilizator.equals("persoana")) {
            primarie.birouPersoane.adaugaFunctionar(functionarPublic);
        }
    }


    private static void afiseazaCereriFinalizate(String fileNameOutput, ManagementPrimarie primarie, String[] lineArray) {
        int i;
        String numeUtilizator = lineArray[1].substring(1);
        for (i = 0; i < primarie.utilizatori.size(); i++) {
            if (numeUtilizator.equals(primarie.utilizatori.get(i).nume)) {
                primarie.utilizatori.get(i).afiseazaUtilizatorFinalizare(fileNameOutput);
                for (int j = 0; j < primarie.utilizatori.get(i).getCereriSolutionate().size(); j++) {
                    primarie.utilizatori.get(i).getCereriSolutionate().get(j).afiseazaCerere(fileNameOutput);
                }
                break;
            }
        }
    }

    private static void rezolvaCerere(ManagementPrimarie primarie, String[] lineArray) {
        String tipUtilizator = lineArray[1].substring(1);
        String numeFunctionar = lineArray[2].substring(1);
        Cerere cerere = null;
        if (tipUtilizator.equals("angajat")) {
            cerere = primarie.birouAngajati.rezolvaCerere(numeFunctionar);
        } else if (tipUtilizator.equals("elev")) {
            cerere = primarie.birouElevi.rezolvaCerere(numeFunctionar);
        } else if (tipUtilizator.equals("entitate juridica")) {
            cerere = primarie.birouEntitatiJuridice.rezolvaCerere(numeFunctionar);
        } else if (tipUtilizator.equals("pensionar")) {
            cerere = primarie.birouPensionari.rezolvaCerere(numeFunctionar);
        } else if (tipUtilizator.equals("persoana")) {
            cerere = primarie.birouPersoane.rezolvaCerere(numeFunctionar);
        }
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
        String strData = format.format(cerere.data);
        Utilizator.retrageCerereAdaugaFinalizata(primarie.utilizatori, strData, cerere.utilizator.nume);
    }
}