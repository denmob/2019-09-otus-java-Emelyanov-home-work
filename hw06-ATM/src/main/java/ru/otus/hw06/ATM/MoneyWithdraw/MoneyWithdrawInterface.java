package ru.otus.hw06.ATM.MoneyWithdraw;

import ru.otus.hw06.ATM.MoneyValue;

public interface MoneyWithdrawInterface {

    boolean validateMoneyToWithdraw();

    int getMoneyValueCount(MoneyValue unitMoneyValue);

}
