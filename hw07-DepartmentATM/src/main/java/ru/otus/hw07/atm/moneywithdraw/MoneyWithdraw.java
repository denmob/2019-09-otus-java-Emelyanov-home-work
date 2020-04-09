package ru.otus.hw07.atm.moneywithdraw;

import ru.otus.hw07.atm.MoneyValue;

public interface MoneyWithdraw {

    boolean validateMoneyToWithdraw();

    int getMoneyValueCount(MoneyValue unitMoneyValue);

}
