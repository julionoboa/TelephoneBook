package dao;

import model.Contact;

import java.util.List;

public interface ContactDAO {
    void create(Contact contact);
    Contact getByEmail(String email);
    Contact getByFirstName(String firstName);
    Contact getByLastName(String lastName);
    Contact getByPhoneNumber(String phoneNumber);
    int delete(String phoneNumber);
    List<Contact> showAll();
}
