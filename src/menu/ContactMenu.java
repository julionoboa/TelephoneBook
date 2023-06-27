package menu;

import dao.impl.ContactDAOImplementation;
import model.Contact;
import service.ContactService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContactMenu {
    public void Menu() {
        //Declarando variables
        int repetir = -1;
        int validate = -1;
        String entrada;

        //Creando objetos
        ContactService contactService = new ContactService(new ContactDAOImplementation());
        Contact contact = new Contact();
        List<Contact> contactList = new ArrayList<>();

        //Creando scanner
        Scanner sc = new Scanner(System.in);

        //Ciclo para repetir todo el menú
        while (repetir < 0){
            //Colocando variable nula para reutilizar en cada reinicio.
            Contact contactFromDB = null;

            //Imprimiendo menú en pantalla
            System.out.println("---AGENDA TELEFÓNICA---");
            System.out.println("Seleccione la opción deseada:");
            System.out.println("1. Crear contacto");
            System.out.println("2. Buscar contacto por nombre");
            System.out.println("3. Buscar contacto por apellido");
            System.out.println("4. Buscar contacto por correo electrónico");
            System.out.println("5. Eliminar contacto");
            System.out.println("6. Mostrar todos los contactos");
            System.out.println("0. Salir");

            //Variable para guardar el número introducido
            int option = validate(sc);

            //Validamos que el número sea correcto (del 0 al 6)
            if (option < 0 || option > 6) {
                while (option < 0 || option > 6) {
                    System.out.println("Introduzca un número del 0 al 6");
                    option = -1;
                    option = validate(sc);
                }
            }
            //Switch para trabajar las diferentes operaciones
            switch (option) {
                //Opción 1 del menú
                case 1:

                    //Pidiendo nombre/s
                    System.out.println("Introduzca el/los nombre/s:");
                    contact.firstName = sc.nextLine();

                    //Ciclo para validar en tiempo real que el nombre introducido sea correcto (not null)
                    while (!contactService.validateNull(contact.firstName)){
                        System.out.println("Introduzca el nombre correctamente");
                        contact.firstName = sc.nextLine();
                    }

                    //Pidiendo apellidos
                    System.out.println("Introduzca el/los apellido/s:");
                    contact.lastName = sc.nextLine();

                    //Ciclo para validar en tiempo real que el apellido introducido sea correcto (not null)
                    while (!contactService.validateNull(contact.lastName)){
                        System.out.println("Introduzca el nombre correctamente");
                        contact.lastName = sc.nextLine();
                    }

                    //Pidiendo número telefónico
                    System.out.println("Introduzca el número telefónico de 10 dígitos:");
                    contact.phoneNumber = sc.nextLine();

                    //Ciclo para validar en tiempo real que el número telefónico introducido sea correcto (not null ni repetido)
                    while (contactService.getByPhoneNumber(contact.phoneNumber) != null){
                        System.out.println("Este teléfono ya pertenece a un contacto...");
                        System.out.println("El teléfono no puede repetirse");
                        System.out.println("Favor agregar otro (o escribir 0 para salir)");
                        contact.phoneNumber = sc.nextLine();

                        //Condicional para romper el flujo en caso de que se introduzca 0 (dentro del While)
                        if (contact.phoneNumber.equals("0")){
                            break;
                        }

                    };
                    //Condicional para romper el flujo en caso de que se introduzca 0 (fuera del While)
                    if (contact.phoneNumber.equals("0")){
                        break;
                    }
                    //Ciclo para validar la longitud, que no sea nulo y que sean números
                    while (!contactService.validateNumber(contact.phoneNumber) ||
                            !contactService.validateLength(contact.phoneNumber) ||
                            !contactService.validateNull(contact.phoneNumber)){

                        //Pidiendo números nuevamente en caso de no cumplir con algún valor
                        System.out.println("Debe introducir el número de manera correcta:");
                        contact.phoneNumber = sc.nextLine();
                    }
                    //Pidiendo correo electrónico
                    System.out.println("Introduzca el correo electrónico:");
                    contact.email = sc.nextLine();

                    //Ciclo para validar que no sea nulo y que sea un correo electrónico con formato correspondiente
                    while (!contactService.validateNull(contact.email) ||
                            !contactService.validateEmail(contact.email)){

                        //Pidiendo números nuevamente en caso de no cumplir con algún valor
                        System.out.println("Debe introducir el correo electrónico de manera correcta:");
                        contact.email = sc.nextLine();
                    }

                    //Creando el contacto
                    contactService.createContact(contact);

                    //Mensaje de confirmación
                    System.out.println("Contacto agregado satisfactoriamente.");
                    break;

                    //Opción 2 del menú
                case 2:

                    //Pidiendo nombre a consultar
                    System.out.println("Introduzca el nombre que desea buscar:");
                    String firstName = sc.nextLine();

                    //Llamando método desde nuestro objeto tipo Contacto
                    contactFromDB = contactService.getByFirstName(firstName);

                    //Ciclo para validar que el contacto exista o el nombre sea incorrecto
                        while (contactFromDB == null){
                            System.out.println("Nombre incorrecto o no existe en la agenda");
                            System.out.println("Introduzca el nombre que desea buscar (o escriba 0 para salir):");

                            //Pidiendo ingresar nombre nuevamente
                            firstName = sc.nextLine();

                            //Condicional para romper flujo en caso de escribir 0 (dentro del While)
                            if (firstName.equals("0")){
                                break;
                            }
                            //Llamando método desde nuestro objeto tipo contacto
                            contactFromDB = contactService.getByFirstName(firstName);
                        }

                        //Condicional para romper flujo en caso de escribir 0 (fuera del While)
                    if (firstName.equals("0")){
                        break;
                    }

                    //Mostrando contactos
                    showContacts(contactFromDB);
                    break;

                    //Opción 3 del menú
                case 3:

                    //Pidiendo apellido a consultar
                    System.out.println("Introduzca el apellido que desea buscar:");
                    String lastName = sc.nextLine();

                    //Llamando método desde nuestro objeto tipo Contacto
                    contactFromDB = contactService.getByLastName(lastName);

                    //Ciclo para validar que el contacto exista o el apellido sea incorrecto
                    while (contactFromDB == null){
                        System.out.println("Apellido incorrecto o no existe en la agenda");
                        System.out.println("Introduzca el apellido que desea buscar (o escriba 0 para salir):");

                        //Pidiendo ingresar nombre nuevamente
                        lastName = sc.nextLine();

                        //Condicional para romper flujo en caso de escribir 0 (dentro del While)
                        if (lastName.equals("0")){
                            break;
                        }

                        //Llamando método desde nuestro objeto tipo Contacto
                        contactFromDB = contactService.getByLastName(lastName);
                    }

                    //Condicional para romper flujo en caso de escribir 0 (fuera del While)
                    if (lastName.equals("0")){
                        break;
                    }

                    //Llamando método para mostrar contactos
                    showContacts(contactFromDB);
                    break;

                    //Opción 4 del menú
                case 4:

                    //Pidiendo correo electrónico a consultar
                    System.out.println("Introduzca el correo electrónico que desea consultar:");
                    String email = sc.nextLine();

                    //Llamando método desde nuestro objeto tipo Contacto
                    contactFromDB = contactService.getByEmail(email);

                    //Ciclo para validar que el contacto exista o el correo electrónico sea incorrecto
                    while (contactFromDB == null){
                        System.out.println("Correo electrónico incorrecto o no existe en la agenda");
                        System.out.println("Introduzca el correo electrónico que desea buscar (o escriba 0 para salir):");

                        //Pidiendo ingresar correo electrónico nuevamente
                        email = sc.nextLine();

                        //Condicional para romper flujo en caso de escribir 0 (dentro del While)
                        if (email.equals("0")){
                            break;
                        }

                        //Llamando método desde nuestro objeto tipo Contacto
                        contactFromDB = contactService.getByEmail(email);
                    }

                    //Condicional para romper flujo en caso de escribir 0 (fuera del While)
                    if (email.equals("0")){
                        break;
                    }

                    //Llamando método para mostrar contactos
                    showContacts(contactFromDB);
                    break;

                    //Opción 5 del menú
                case 5:

                    //Pidiendo teléfono a eliminar
                    System.out.println("Introduzca el teléfono a eliminar:");
                    String phoneNumber = sc.nextLine();

                    //Llamando método para validar número de teléfono desde nuestro objeto contactService
                    contactService.validateNumber(phoneNumber);

                    //Ciclo para validar que no exista el teléfono a eliminar
                    while (contactService.delete(phoneNumber) == 0){

                        //Pidiendo teléfono nuevamente
                        System.out.println("Introduzca nuevamente el valor a eliminar (o escriba 0 para salir):");
                        phoneNumber = sc.nextLine();

                        //Condicional para romper flujo en caso de escribir 0
                        if (phoneNumber.equals("0")){
                            break;
                        }
                    }
                    break;

                    //Opción 6 del menú
                case 6:

                    //A través de nuestro objeto tipo ArrayList, llamamos al método showAll de nuestro objeto contactService
                    contactList = contactService.showAll(contact);

                    //Ciclo para mostrar contactos (for:each)
                    for (Contact i : contactList) {
                        showContacts(i);
                }
            }

                        //Pregunta de confirmacion
                        System.out.println("");
                        System.out.println("¿Desea salir?");
                        System.out.println("Si(Y) [Cierra el programa]");
                        System.out.println("No(N) [Vuelve al menú principal]");

                        //Do-While para validar que se introduzcan las letras Y o N
                        do{
                            entrada = sc.nextLine();
                            entrada = entrada.toUpperCase();
                            if (entrada.equalsIgnoreCase("Y")){
                                repetir = 1;
                                break;
                            }
                            else if (entrada.equalsIgnoreCase("N")){
                                repetir = -1;
                                break;
                            }
                            else {
                                System.out.println("Debe ser Y o N");
                            }
                        }  while (true);

        }


    }

    //Método para validar las opciones del menú (del 0 al 6)
    public int validate(Scanner sc) {
        int i = -1;

        while (i < 0 ) {

            String optionMenu = sc.nextLine();

            try {
                i = Integer.valueOf(optionMenu);
            }
            catch (Exception e){
                System.out.println("Debe introducir un número válido");
                i = -1;
            }

        }

        return i;
    }

    //Método para mostrar todos los contactos.
    public Contact showContacts(Contact contact){
        System.out.println("-------------------------------");
        System.out.println("Nombre: " + contact.firstName);
        System.out.println("Apellido: " + contact.lastName);
        System.out.println("Teléfono: " + contact.phoneNumber);
        System.out.println("Correo electrónico: " + contact.email);
        System.out.println("-------------------------------");
        return null;
    }
}
