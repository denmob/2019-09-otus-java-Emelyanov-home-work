package ru.otus.hw07.ATM.MoneyWithdraw;

import ru.otus.hw07.ATM.MoneyValue;

public interface MoneyWithdraw {

    boolean validateMoneyToWithdraw();

    int getMoneyValueCount(MoneyValue unitMoneyValue);

}
