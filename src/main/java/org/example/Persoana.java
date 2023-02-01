package org.example;

public class Persoana extends Utilizator{

    public Persoana(String nume) {
        super(nume);
    }

    /* scrie textul cererii specifice unei persoane */
    @Override
    public String scrieTextCerere(TipCerere tip) throws CerereIncompatibila {
        String text = "Subsemnatul ";
        text = text.concat(nume);
        text = text.concat(", va rog sa-mi aprobati urmatoarea solicitare: ");
        String tipCerere = tipCerere(tip);
        if (tipCerere.equals("inlocuire buletin") || tipCerere.equals("inlocuire carnet de sofer")) {
            text = text.concat(tipCerere);
            return text;
        }
        throw new CerereIncompatibila("Utilizatorul de tip persoana nu poate inainta o cerere" +
                " de tip " + tipCerere);
    }
}
