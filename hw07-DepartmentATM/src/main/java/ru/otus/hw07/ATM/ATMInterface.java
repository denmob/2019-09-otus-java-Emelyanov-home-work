package ru.otus.hw07.ATM;


import ru.otus.hw07.ATM.MoneyCell.MoneyCellImp;

public interface ATMInterface {

   void withdrawMoney(int value);

   String depositMoney(MoneyValue moneyValue, Integer count);

   void printATMValues();

}
