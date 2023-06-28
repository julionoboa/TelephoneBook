import menu.ContactMenu;

public class Main {
    public static void main(String[] args) {
        ContactMenu menu = new ContactMenu();
        menu.Menu();
    }
}

//SQL Queries
//create database agenda;
//        use agenda;
//        create table contact(
//        id int primary key not null auto_increment,
//        firstName varchar(100),
//        lastName varchar(100),
//        phoneNumber varchar(10) unique,
//        email varchar(100)
//        );