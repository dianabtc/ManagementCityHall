package org.example;

public class Elev extends Utilizator{
    private String scoala;

    public Elev(String nume, String scoala) {
        super(nume);
        this.scoala = scoala;
    }

    /* scrie textul cererii specifice unui elev */
    @Override
    public String scrieTextCerere(TipCerere tip) throws CerereIncompatibila {
        String text = "Subsemnatul ";
        text = text.concat(nume);
        text = text.concat(", elev la scoala ");
        text = text.concat(scoala);
        text = text.concat(", va rog sa-mi aprobati urmatoarea solicitare: ");
        String tipCerere = tipCerere(tip);
        if (tipCerere.equals("inlocuire buletin") || tipCerere.equals("inlocuire carnet de elev")) {
            text = text.concat(tipCerere);
            return text;
        }
        throw new CerereIncompatibila("Utilizatorul de tip elev nu poate inainta o cerere" +
                " de tip " + tipCerere);
    }
}
