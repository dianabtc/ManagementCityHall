package org.example;

public class Angajat extends Utilizator{
    private String companie;

    public Angajat(String nume, String companie) {
        super(nume);
        this.companie = companie;
    }

    /* scrie textul cererii specifice unui angajat */
    @Override
    public String scrieTextCerere(TipCerere tip) throws CerereIncompatibila {
        String text = "Subsemnatul ";
        text = text.concat(nume);
        text = text.concat(", angajat la compania ");
        text = text.concat(companie);
        text = text.concat(", va rog sa-mi aprobati urmatoarea solicitare: ");
        String tipCerere = tipCerere(tip);
        if (tipCerere.equals("inlocuire buletin") || tipCerere.equals("inlocuire carnet de sofer")
            || tipCerere.equals("inregistrare venit salarial")) {
            text = text.concat(tipCerere);
            return text;
        }
        throw new CerereIncompatibila("Utilizatorul de tip angajat nu poate inainta o cerere" +
                " de tip " + tipCerere);
    }
}
