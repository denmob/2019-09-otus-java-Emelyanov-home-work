package ru.otus.hw07.ATM;

public enum MoneyValueV2 {

    UNIT_10 (10),
    UNIT_20 (20),
    UNIT_50 (50),
    UNIT_100 (100),
    UNIT_200 (200),
    UNIT_500 (500);

    private final int moneyValue;

    MoneyValueV2(int moneyValue) {
        this.moneyValue = moneyValue;
    }

    public int getMoneyValue() {
        return this.moneyValue;
    }

}
