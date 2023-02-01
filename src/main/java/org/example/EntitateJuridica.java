package org.example;

public class EntitateJuridica extends Utilizator{
    private String reprezentant;

    public EntitateJuridica(String nume, String reprezentant) {
        super(nume); //numele companiei
        this.reprezentant = reprezentant;
    }

    /* scrie textul cererii specifice unei entitati juridice */
    @Override
    public String scrieTextCerere(TipCerere tip) throws CerereIncompatibila {
        String text = "Subsemnatul ";
        text = text.concat(reprezentant);
        text = text.concat(", reprezentant legal al companiei ");
        text = text.concat(nume);
        text = text.concat(", va rog sa-mi aprobati urmatoarea solicitare: ");
        String tipCerere = tipCerere(tip);
        if (tipCerere.equals("creare act constitutiv") || tipCerere.equals("reinnoire autorizatie")) {
            text = text.concat(tipCerere);
            return text;
        }
        throw new CerereIncompatibila("Utilizatorul de tip entitate juridica nu poate inainta o cerere" +
                " de tip " + tipCerere);
    }
}
