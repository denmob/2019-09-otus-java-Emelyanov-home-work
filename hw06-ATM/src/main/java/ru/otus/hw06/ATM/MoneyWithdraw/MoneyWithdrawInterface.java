package ru.otus.hw06.ATM.MoneyWithdraw;

import ru.otus.hw06.ATM.ATMImp;

public interface MoneyWithdrawInterface {

    void setUnit(Integer unitMoneyValue, Integer unitMoneyCount);

    Integer getUnit(ATMImp.MoneyValue moneyValue);

    void printMoneyUnitForWithdraw();

}
