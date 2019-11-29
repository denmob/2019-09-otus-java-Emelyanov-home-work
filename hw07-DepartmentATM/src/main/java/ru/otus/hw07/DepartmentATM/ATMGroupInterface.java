package ru.otus.hw07.DepartmentATM;

import ru.otus.hw07.ATM.MoneyValue;

import java.util.Map;

public interface ATMGroupInterface {

    Map<MoneyValue,Integer> getMoneyATM();

    void printATMValues();

    void resetStateToInitATM();

}
