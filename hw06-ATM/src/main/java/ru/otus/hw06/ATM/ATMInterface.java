package ru.otus.hw06.ATM;


public interface ATMInterface {

   void withdrawMoney(int value);

   String depositMoney(ATMImp.MoneyValue moneyValue, Integer count);

   void printATMValues();


}
