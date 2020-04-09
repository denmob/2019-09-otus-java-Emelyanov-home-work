package ru.otus.hw07.department;

import ru.otus.hw07.atm.MoneyValue;

import java.util.Map;

public interface ATMGroup {

    Map<MoneyValue,Integer> getMoneyATM();

    void printATMValues();

    void resetStateToInitATM();

}
