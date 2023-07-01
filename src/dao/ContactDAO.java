package dao;

import model.Contact;

import java.util.List;

public interface ContactDAO {
    void create(Contact contact);
    List<Contact> getByEmail(Contact contact);
    List<Contact> getByFirstName(Contact contact);
    List<Contact> getByLastName(Contact contact);
    List<Contact> getByPhoneNumber(Contact contact);
    int delete(Contact contact);
    List<Contact> showAll();
}
