package ru.otus.hw07.DepartmentATM;

import ru.otus.hw07.ATM.ATMImp;


import java.util.ArrayList;
import java.util.List;

public class DepartmentATM implements ATMGroupInterface{

        private final List<ATMImp> atms = new ArrayList<>();

        public void addATM(ATMImp atmImp) {
            atms.add(atmImp);
        }

        @Override
        public Integer getMoneyATM() {
        int sum = 0;
            for (ATMImp atm:atms) {
                sum = sum + atm.getMoneyATM();
            }
          return sum;
        }

    @Override
    public void printATMValues() {
        for (ATMImp atm:atms) {
            atm.printATMValues();
        }
    }

}
