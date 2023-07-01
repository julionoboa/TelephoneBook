package service;

import dao.ContactDAO;
import model.Contact;

import java.math.BigInteger;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactService {
    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }
    private ContactDAO contactDAO;

    public void createContact(Contact contact) {

        if (validateNumber(contact.phoneNumber)) {

            if(validateLength(contact.phoneNumber)){

                if(validateNull(contact.firstName) &&
                        validateNull(contact.lastName) &&
                        validateNull(contact.email) &&
                        validateNull(contact.phoneNumber)) {

                    if (validateEmail(contact.email)) {

                        contactDAO.create(contact);

                    }
                }
            }
        }
    }

    public List<Contact> getByEmail(String email) {
        return contactDAO.getByEmail(email);
    }

    public List<Contact> getByFirstName(String firstName) {
        return contactDAO.getByFirstName(firstName);
    }

    public List<Contact> getByLastName(String lastName) {
        return contactDAO.getByLastName(lastName);
    }

    public List<Contact> getByPhoneNumber(String phoneNumber) {
        return contactDAO.getByPhoneNumber(phoneNumber);
    }

    public int delete(String phoneNumber) {

        int affectedRows = contactDAO.delete(phoneNumber);

        if (affectedRows == 0){

            System.out.println("No existe ningún valor a eliminar, favor revisar el número de teléfono.");

        }
        else {
            System.out.println("Contacto(s) eliminado(s) satisfactoriamente.");

        }
        return affectedRows;
    }

    public List<Contact> showAll(Contact contact) {
        return contactDAO.showAll();
    }


    public boolean validateNumber(String contact) {
        try {
            BigInteger.valueOf(Long.parseLong(contact));
        } catch (Exception e) {
            System.out.println("Debe introducir un número válido");
            return false;
        }
        return true;
    }

    public boolean validateLength(String contact){

        if (contact.length()>10){

            System.out.println("No debe ser mayor a 10 dígitos.");
            return false;

        }
        else if (contact.length()<10){

            System.out.println("Debe introducir todos los dígitos.");
            return false;

        }
        else if (contact.length()==10){

            return true;

        }

        return true;
    }

    public boolean validateNull(String contact){

        if (contact == null){

            System.out.println("No puede contener valores nulos.");
            return false;

        } else if (contact.isBlank()){
            System.out.println("Ningun campo puede estar vacio.");
            return false;
        }

        return true;

    }

    public boolean validateEmail(String email){

        String regex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches() == true){

            return true;

        }else {

            System.out.println("Debe introducir un correo válido.");

        }

        return false;

    }

}
