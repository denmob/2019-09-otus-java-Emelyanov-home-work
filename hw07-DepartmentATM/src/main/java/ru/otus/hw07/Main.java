package ru.otus.hw07;


import ru.otus.hw07.ATM.ATMImp;
import ru.otus.hw07.ATM.MoneyValue;
import ru.otus.hw07.DepartmentATM.DepartmentATM;

public class Main {

    public static void main(String[] args)  {

        ATMImp atm1 = new ATMImp.Builder()
                .addMoneyValue20(15)
                .addMoneyValue50(5)
                .addMoneyValue100(2)
                .addMoneyValue200(5)
                .addMoneyValue500(3)
                .build();

        ATMImp atm2 = new ATMImp.Builder()
                .addMoneyValue10(100)
                .addMoneyValue20(10)
                .addMoneyValue50(50)
                .addMoneyValue100(20)
                .addMoneyValue200(5)
                .build();

        DepartmentATM departmentATM = new DepartmentATM();
        departmentATM.addATM(atm1);
        departmentATM.addATM(atm2);

        departmentATM.printATMValues();

        Integer iAllMoney = departmentATM.getMoneyATM();
        System.out.println("getMoneyATM first "+iAllMoney);

        departmentATM.printATMValues();

        iAllMoney = departmentATM.getMoneyATM();
        System.out.println("getMoneyATM second "+iAllMoney);

//        atm.printATMValues();
//
//        System.out.println("Добавление банкнот АТМ");
//        atm.depositMoney(MoneyValue.unit20,3);
//        atm.depositMoney(MoneyValue.unit50,5);
//        atm.depositMoney(MoneyValue.unit100,2);
//        atm.depositMoney(MoneyValue.unit200,5);
//        atm.depositMoney(MoneyValue.unit20,5);
//
//        atm.printATMValues();
//
//        System.out.println("Снятие суммы из АТМ");
//        atm.withdrawMoney(540);
//
//        atm.printATMValues();
    }
}
