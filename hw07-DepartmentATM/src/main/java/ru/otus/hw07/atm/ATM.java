package ru.otus.hw07.atm;


public interface ATM {

   void withdrawMoney(int value);

   String depositMoney(MoneyValue moneyValue, Integer count);

   void printATMValues();

   void resetStateToInitATM();

}
