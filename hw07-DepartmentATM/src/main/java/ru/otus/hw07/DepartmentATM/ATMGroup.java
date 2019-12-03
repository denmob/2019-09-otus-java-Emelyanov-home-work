package ru.otus.hw07.DepartmentATM;

import ru.otus.hw07.ATM.MoneyValueV2;

import java.util.Map;

public interface ATMGroup {

    Map<MoneyValueV2,Integer> getMoneyATM();

    void printATMValues();

    void resetStateToInitATM();

}
