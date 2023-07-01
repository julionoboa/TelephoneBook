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
        String entrada;

        //Creando objetos
        ContactService contactService = new ContactService(new ContactDAOImplementation());
        Contact contact = new Contact();

        //Creando scanner
        Scanner sc = new Scanner(System.in);

        //Ciclo para repetir todo el menú
        while (repetir < 0) {

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
                    option = validate(sc);
                }
            }
            //Switch para trabajar las diferentes operaciones
            switch (option) {
                //Opción 1 del menú
                case 1:
                    contactService.createContact(contact);
                    break;

                //Opción 2 del menú
                case 2:
                    contactService.getByFirstName(contact);
                    break;

                //Opción 3 del menú
                case 3:
                    contactService.getByLastName(contact);
                    break;

                //Opción 4 del menú
                case 4:
                    contactService.getByEmail(contact);
                    break;

                //Opción 5 del menú
                case 5:
                    contactService.delete(contact);
                    break;

                //Opción 6 del menú
                case 6:
                    contactService.showAll(contact);
                    break;
            }

            //Pregunta de confirmacion
            System.out.println("");
            System.out.println("¿Desea salir?");
            System.out.println("Si(Y) [Cierra el programa]");
            System.out.println("No(N) [Vuelve al menú principal]");

            //Do-While para validar que se introduzcan las letras Y o N
            do {
                entrada = sc.nextLine();
                entrada = entrada.toUpperCase();
                if (entrada.equalsIgnoreCase("Y")) {
                    repetir = 1;
                    break;
                } else if (entrada.equalsIgnoreCase("N")) {
                    repetir = -1;
                    break;
                } else {
                    System.out.println("Debe ser Y o N");
                }
            } while (true);

        }


    }

    //Método para validar las opciones del menú (del 0 al 6)
    public int validate(Scanner sc) {
        int i = -1;

        while (i < 0) {

            String optionMenu = sc.nextLine();

            try {
                i = Integer.valueOf(optionMenu);
            } catch (Exception e) {
                System.out.println("Debe introducir un número válido");
                i = -1;
            }

        }

        return i;
    }

}
