package ru.otus.hw07.ATM;


public interface ATM {

   void withdrawMoney(int value);

   String depositMoney(MoneyValueV2 moneyValue, Integer count);

   void printATMValues();

   void resetStateToInitATM();

}
