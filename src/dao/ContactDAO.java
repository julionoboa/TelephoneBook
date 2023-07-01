package dao;

import model.Contact;

import java.util.List;

public interface ContactDAO {
    void create(Contact contact);
    List<Contact> getByEmail(String email);
    List<Contact> getByFirstName(String firstName);
    List<Contact> getByLastName(String lastName);
    List<Contact> getByPhoneNumber(String phoneNumber);
    int delete(String phoneNumber);
    List<Contact> showAll();
}
