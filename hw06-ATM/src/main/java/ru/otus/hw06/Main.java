package ru.otus.hw06;

import ru.otus.hw06.ATM.ATMImp;

public class Main {

    public static void main(String[] args)  {

        ATMImp atm = new ATMImp();

        System.out.println("Инициализация АТМ");
        atm.depositMoney(ATMImp.MoneyValue.unit20,3);
        atm.depositMoney(ATMImp.MoneyValue.unit50,5);
        atm.depositMoney(ATMImp.MoneyValue.unit100,2);
        atm.depositMoney(ATMImp.MoneyValue.unit200,5);
        atm.depositMoney(ATMImp.MoneyValue.unit20,5);
        System.out.println("-----------------");

        System.out.println("Вывод состояния АТМ");
        atm.printATMValues();
        System.out.println("-----------------");

        System.out.println("Снятие суммы из АТМ");
        atm.withdrawMoney(540);
        System.out.println("-----------------");

        System.out.println("Вывод состояния АТМ");
        atm.printATMValues();
        System.out.println("-----------------");
    }
}
