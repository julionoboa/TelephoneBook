package service;

import dao.ContactDAO;
import model.Contact;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactService {
    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    private ContactDAO contactDAO;

    Scanner sc = new Scanner(System.in);
    List<Contact> contactList = new ArrayList<>();

    public int repetir = -1;

    public void createContact(Contact contact) {

        while (repetir < 0) {
            //Pidiendo nombre/s
            System.out.println("Introduzca el/los nombre/s:");
            contact.firstName = sc.nextLine();

            //Ciclo para validar en tiempo real que el nombre introducido sea correcto (not null)
            while (!validateNull(contact.firstName)) {
                System.out.println("Introduzca el nombre correctamente");
                contact.firstName = sc.nextLine();
            }

            //Pidiendo apellidos
            System.out.println("Introduzca el/los apellido/s:");
            contact.lastName = sc.nextLine();

            //Ciclo para validar en tiempo real que el apellido introducido sea correcto (not null)
            while (!validateNull(contact.lastName)) {
                System.out.println("Introduzca el apellido correctamente");
                contact.lastName = sc.nextLine();
            }

            //Pidiendo número telefónico
            System.out.println("Introduzca el número telefónico de 10 dígitos:");
            contact.phoneNumber = sc.nextLine();
            while (!validateNull(contact.phoneNumber)) {
                System.out.println("Introduzca el número correctamente.");
                contact.phoneNumber = sc.nextLine();
            }

            contactList = contactDAO.getByPhoneNumber(contact);

            //Ciclo para validar en tiempo real que el número telefónico introducido sea correcto (not null ni repetido)
            while (contactList != null && contactList.size() != 0) {
                System.out.println("Este teléfono ya pertenece a un contacto...");
                System.out.println("El teléfono no puede repetirse");
                System.out.println("Favor agregar otro (o escribir 0 para salir)");
                contact.phoneNumber = sc.nextLine();
                contactList = contactDAO.getByPhoneNumber(contact);

                //Condicional para romper el flujo en caso de que se introduzca 0 (dentro del While)
                if (contact.phoneNumber.equals("0")) {
                    break;
                }

            }
            ;
            //Condicional para romper el flujo en caso de que se introduzca 0 (fuera del While)
            if (contact.phoneNumber.equals("0")) {
                break;
            }
            //Ciclo para validar la longitud, que no sea nulo y que sean números
            while (!validateNumber(contact.phoneNumber) ||
                    !validateLength(contact.phoneNumber) ||
                    !validateNull(contact.phoneNumber)) {

                //Pidiendo números nuevamente en caso de no cumplir con algún valor
                System.out.println("Debe introducir el número de manera correcta:");
                contact.phoneNumber = sc.nextLine();
            }
            //Pidiendo correo electrónico
            System.out.println("Introduzca el correo electrónico:");
            contact.email = sc.nextLine();

            //Ciclo para validar que no sea nulo y que sea un correo electrónico con formato correspondiente
            while (!validateNull(contact.email) ||
                    !validateEmail(contact.email)) {

                //Pidiendo números nuevamente en caso de no cumplir con algún valor
                System.out.println("Debe introducir el correo electrónico de manera correcta:");
                contact.email = sc.nextLine();
            }


            if (validateNumber(contact.phoneNumber)) {

                if (validateLength(contact.phoneNumber)) {

                    if (validateNull(contact.firstName) &&
                            validateNull(contact.lastName) &&
                            validateNull(contact.email) &&
                            validateNull(contact.phoneNumber)) {

                        if (validateEmail(contact.email)) {

                            contactDAO.create(contact);

                        }
                    }
                }
            }
            //Mensaje de confirmación
            System.out.println("Contacto agregado satisfactoriamente.");
            repetir = 0;
        }
        repetir = -1;
    }

    public List<Contact> getByEmail(Contact contact) {

        while (repetir < 0) {

            //Pidiendo correo electrónico a consultar
            System.out.println("Introduzca el correo electrónico que desea consultar:");
            contact.email = sc.nextLine();

            while (!validateNull(contact.email)) {
                System.out.println("Introduzca las letras correctamente");
                contact.email = sc.nextLine();
            }
            ;

            //Llamando método desde nuestro objeto tipo Contacto
            contactList = contactDAO.getByEmail(contact);

            //Ciclo para validar que el contacto exista o el correo electrónico sea incorrecto
            while (contactList == null || contactList.size() == 0) {
                System.out.println("Correo electrónico incorrecto o no existe en la agenda");
                System.out.println("Introduzca el correo electrónico que desea buscar (o escriba 0 para salir):");

                //Pidiendo ingresar correo electrónico nuevamente
                contact.email = sc.nextLine();

                //Condicional para romper flujo en caso de escribir 0 (dentro del While)
                if (contact.email.equals("0")) {
                    break;
                }

                //Llamando método desde nuestro objeto tipo Contacto
                contactList = contactDAO.getByEmail(contact);
            }

            //Condicional para romper flujo en caso de escribir 0 (fuera del While)
            if (contact.email.equals("0")) {
                break;
            }

            //Llamando método para mostrar contactos

            for (Contact i : contactList) {
                showContacts(i);
            }
            repetir = 0;
        }
        repetir = -1;
        return contactDAO.getByEmail(contact);
    }

    public List<Contact> getByFirstName(Contact contact) {

        while (repetir < 0) {
            //Pidiendo nombre a consultar
            System.out.println("Introduzca el nombre que desea buscar:");
            contact.firstName = sc.nextLine();

            while (!validateNull(contact.firstName)) {
                System.out.println("Introduzca el nombre correctamente");
                contact.firstName = sc.nextLine();
            }
            ;

            //Llamando método desde nuestro objeto tipo Contacto
            contactList = contactDAO.getByFirstName(contact);

            //Ciclo para validar que el contacto exista o el nombre sea incorrecto
            while (contactList == null || contactList.size() == 0) {
                System.out.println("Nombre incorrecto o no existe en la agenda");
                System.out.println("Introduzca el nombre que desea buscar (o escriba 0 para salir):");

                //Pidiendo ingresar nombre nuevamente
                contact.firstName = sc.nextLine();
                validateNull(contact.firstName);
                //Condicional para romper flujo en caso de escribir 0 (dentro del While)
                if (contact.firstName.equals("0")) {
                    break;
                }
                //Llamando método desde nuestro objeto tipo contacto
                contactList = contactDAO.getByFirstName(contact);

            }

            //Condicional para romper flujo en caso de escribir 0 (fuera del While)
            if (contact.firstName.equals("0")) {
                break;
            }

            //Mostrando contactos

            for (Contact i : contactList) {
                showContacts(i);
            }
            repetir = 0;
        }
        repetir = -1;
        return contactDAO.getByFirstName(contact);
    }

    public List<Contact> getByLastName(Contact contact) {

        while (repetir < 0) {
            //Pidiendo apellido a consultar
            System.out.println("Introduzca el apellido que desea buscar:");
            contact.lastName = sc.nextLine();
            while (!validateNull(contact.lastName)) {
                System.out.println("Introduzca el apellido correctamente");
                contact.firstName = sc.nextLine();
            }
            ;

            //Llamando método desde nuestro objeto tipo Contacto
            try {
                contactList = contactDAO.getByLastName(contact);
            } catch (RuntimeException e) {
                System.out.println("Error obteniendo contacto por apellido. El programa se cerrara.");
                System.exit(0);
            }

            //Ciclo para validar que el contacto exista o el apellido sea incorrecto
            while (contactList == null || contactList.size() == 0) {
                System.out.println("Apellido incorrecto o no existe en la agenda");
                System.out.println("Introduzca el apellido que desea buscar (o escriba 0 para salir):");

                //Pidiendo ingresar nombre nuevamente
                contact.lastName = sc.nextLine();

                //Condicional para romper flujo en caso de escribir 0 (dentro del While)
                if (contact.lastName.equals("0")) {
                    break;
                }

                //Llamando método desde nuestro objeto tipo Contacto
                contactList = contactDAO.getByLastName(contact);
            }

            //Condicional para romper flujo en caso de escribir 0 (fuera del While)
            if (contact.lastName.equals("0")) {
                break;
            }

            //Llamando método para mostrar contactos
            for (Contact i : contactList) {
                showContacts(i);
            }
            repetir = 0;
        }
        repetir = -1;
        return contactDAO.getByLastName(contact);
    }

    public List<Contact> getByPhoneNumber(Contact contact) {
        return contactDAO.getByPhoneNumber(contact);
    }

    public int delete(Contact contact) {
        //Pidiendo teléfono a eliminar
        System.out.println("Introduzca el teléfono a eliminar:");
        contact.phoneNumber = sc.nextLine();
        while (!validateNull(contact.phoneNumber)) {
            System.out.println("Introduzca el teléfono correctamente");
            contact.firstName = sc.nextLine();
        }
        ;

        //Llamando método para validar número de teléfono desde nuestro objeto contactService
        validateNumber(contact.phoneNumber);

        //Ciclo para validar que no exista el teléfono a eliminar
        while (contactDAO.delete(contact) == 0) {

            //Pidiendo teléfono nuevamente
            System.out.println("Introduzca nuevamente el valor a eliminar (o escriba 0 para salir):");
            contact.phoneNumber = sc.nextLine();

            //Condicional para romper flujo en caso de escribir 0
            if (contact.phoneNumber.equals("0")) {
                break;
            }
        }

        int affectedRows = contactDAO.delete(contact);

        if (affectedRows == 0) {

            System.out.println("No existe ningún valor a eliminar, favor revisar el número de teléfono.");

        } else {
            System.out.println("Contacto(s) eliminado(s) satisfactoriamente.");

        }
        return affectedRows;
    }

    public List<Contact> showAll(Contact contact) {
        //A través de nuestro objeto tipo ArrayList, llamamos al método showAll de nuestro objeto contactService
        contactList = contactDAO.showAll();

        //Ciclo para mostrar contactos (for:each)
        for (Contact i : contactList) {
            showContacts(i);
        }
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

    public boolean validateLength(String contact) {

        if (contact.length() > 10) {

            System.out.println("No debe ser mayor a 10 dígitos.");
            return false;

        } else if (contact.length() < 10) {

            System.out.println("Debe introducir todos los dígitos.");
            return false;

        } else if (contact.length() == 10) {

            return true;

        }

        return true;
    }

    public boolean validateNull(String contact) {

        if (contact == null) {

            System.out.println("No puede contener valores nulos.");
            return false;

        } else if (contact.isBlank()) {
            System.out.println("Ningun campo puede estar vacio.");
            return false;
        }

        return true;

    }

    public boolean validateEmail(String email) {

        String regex = "^[\\w!#$%&amp;'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&amp;'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches() == true) {

            return true;

        } else {

            System.out.println("Debe introducir un correo válido.");

        }

        return false;

    }

    //Método para mostrar todos los contactos.
    public Contact showContacts(Contact contact) {
        System.out.println("-------------------------------");
        System.out.println("Nombre: " + contact.firstName);
        System.out.println("Apellido: " + contact.lastName);
        System.out.println("Teléfono: " + contact.phoneNumber);
        System.out.println("Correo electrónico: " + contact.email);
        System.out.println("-------------------------------");
        return null;
    }

}
