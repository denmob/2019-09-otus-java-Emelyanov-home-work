package ru.otus.hw06.atm.moneywithdraw;

import ru.otus.hw06.atm.MoneyValue;

public interface MoneyWithdrawInterface {

    boolean validateMoneyToWithdraw();

    int getMoneyValueCount(MoneyValue unitMoneyValue);

}
