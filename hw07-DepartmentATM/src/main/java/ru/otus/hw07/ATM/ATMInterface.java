package ru.otus.hw07.ATM;


public interface ATMInterface {

   void withdrawMoney(int value);

   String depositMoney(MoneyValue moneyValue, Integer count);

   void printATMValues();

   void resetStateToInitATM();

}
