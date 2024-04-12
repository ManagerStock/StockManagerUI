package model;

import java.util.Date;

public class Client extends User{

    public Client(String firstName, String lastName, String email, String address, String phoneNumber, Date dateNaissance) {
        super(firstName, lastName, email, address, phoneNumber, dateNaissance);
    }
}
