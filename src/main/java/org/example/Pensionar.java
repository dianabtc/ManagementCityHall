package org.example;

public class Pensionar extends Utilizator{
    public Pensionar(String nume) {
        super(nume);
    }

    /* scrie textul cererii specifice unui pensionar */
    @Override
    public String scrieTextCerere(TipCerere tip) throws CerereIncompatibila {
        String text = "Subsemnatul ";
        text = text.concat(nume);
        text = text.concat(", va rog sa-mi aprobati urmatoarea solicitare: ");
        String tipCerere = tipCerere(tip);
        if (tipCerere.equals("inlocuire buletin") || tipCerere.equals("inlocuire carnet de sofer")
            || tipCerere.equals("inregistrare cupoane de pensie")) {
            text = text.concat(tipCerere);
            return text;
        }
        throw new CerereIncompatibila("Utilizatorul de tip pensionar nu poate inainta o cerere" +
                " de tip " + tipCerere);
    }
}
