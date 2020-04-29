package ru.otus.hw06.atm;


public interface ATMInterface {

  void withdrawMoney(int value);

  void depositMoney(MoneyValue moneyValue, Integer count);

  void printATMValues();
}
