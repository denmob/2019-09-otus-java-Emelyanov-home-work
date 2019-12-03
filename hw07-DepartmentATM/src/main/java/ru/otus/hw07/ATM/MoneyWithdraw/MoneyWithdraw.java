package ru.otus.hw07.ATM.MoneyWithdraw;

import ru.otus.hw07.ATM.MoneyValueV2;

public interface MoneyWithdraw {

    boolean validateMoneyToWithdraw();

    int getMoneyValueCount(MoneyValueV2 unitMoneyValue);

}
