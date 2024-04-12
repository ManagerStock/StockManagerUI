package model;

import java.util.Date;

public class Fournisseur extends User{
    public Fournisseur(String firstName, String lastName, String email, String address, String phoneNumber, Date dateNaissance) {
        super(firstName, lastName, email, address, phoneNumber, dateNaissance);
    }
}
