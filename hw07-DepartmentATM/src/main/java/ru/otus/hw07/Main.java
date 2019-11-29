package ru.otus.hw07;


import ru.otus.hw07.ATM.ATMImp;
import ru.otus.hw07.ATM.MoneyValue;
import ru.otus.hw07.DepartmentATM.DepartmentATM;

public class Main {

    public static void main(String[] args)  {

        System.out.println("Инициализация АТМ1");
        ATMImp atm1 = new ATMImp.Builder("АТМ1")
                .addMoneyValue20(15)
                .addMoneyValue50(5)
                .addMoneyValue100(2)
                .addMoneyValue200(5)
                .addMoneyValue500(3)
                .build();

        System.out.println("Инициализация АТМ2");
        ATMImp atm2 = new ATMImp.Builder("АТМ2")
                .addMoneyValue10(100)
                .addMoneyValue20(10)
                .addMoneyValue50(50)
                .addMoneyValue100(20)
                .addMoneyValue200(5)
                .build();


        System.out.println("Инициализация АТМ3");
        ATMImp atm3 = new ATMImp.Builder("АТМ3")
                .addMoneyValue10(10)
                .addMoneyValue20(20)
                .addMoneyValue100(10)
                .addMoneyValue200(5)
                .build();

        System.out.println("Инициализация департамента с АТМ");
        DepartmentATM departmentATM = new DepartmentATM();
        departmentATM.addATM(atm1);
        departmentATM.addATM(atm2);
        departmentATM.addATM(atm3);

        System.out.println("Вывод состояния каждого АТМ");
        departmentATM.printATMValues();

        System.out.println("\n"+"Сбор денег со всех ATM");
        departmentATM.getMoneyATM();

        System.out.println("\n"+"Вывод состояния каждого АТМ после сбора денег ");
        departmentATM.printATMValues();

        System.out.println("\n"+"Восстановить состояние всех  ATM до начального");
        departmentATM.resetStateToInitATM();

        System.out.println("Вывод состояния каждого АТМ");
        departmentATM.printATMValues();
    }
}
