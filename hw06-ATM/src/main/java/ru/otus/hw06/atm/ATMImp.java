package ru.otus.hw06.atm;

import ru.otus.hw06.atm.moneycell.MoneyCellImp;
import ru.otus.hw06.atm.moneywithdraw.MoneyWithdrawImp;
import java.util.HashMap;
import java.util.Map;

public class ATMImp implements ATMInterface {

    private final MoneyCellImp moneyCellImp;

    private ATMImp(Builder moneyBuilder) {
        this.moneyCellImp = new MoneyCellImp(moneyBuilder.getMapMoney());
    }

    @Override
    public void withdrawMoney(int value) {
        System.out.println("Необходимо выдать сумму: "+value);
        MoneyWithdrawImp moneyWithdraw = new MoneyWithdrawImp(value,moneyCellImp.copy());

        if (moneyWithdraw.validateMoneyToWithdraw()) {
            for  (MoneyValue moneyValue: MoneyValue.values())
                moneyCellImp.getMoney(moneyValue, moneyWithdraw.getMoneyValueCount(moneyValue));
        }
    }

    @Override
    public void depositMoney(MoneyValue moneyValue, Integer count) {
        if (count>0) {
            moneyCellImp.putMoney(moneyValue, count);
        }
    }

    @Override
    public void printATMValues() {
        int sum = 0;
        for  (MoneyValue moneyValue: MoneyValue.values()){
            int moneyValueCount =  moneyCellImp.getMoneyValueCount(moneyValue);
            sum = sum + moneyValueCount* Integer.parseInt(moneyValue.toString());
            System.out.println(String.format("Количество купюр  %s номиналом %s ", moneyValueCount,moneyValue.toString()));
        }
        if (sum > 0) {
            System.out.println("Общая сумма денежных стредств в ATM = " + sum);
        }
    }

    public static class Builder {
        Map<MoneyValue,Integer> mapMoney = new HashMap<>();
        public Builder() { }
        private Map<MoneyValue, Integer> getMapMoney() { return mapMoney; }
        public Builder addMoneyValue10 (int amount) { mapMoney.put(MoneyValue.UNIT_10,amount); return this; }
        public Builder addMoneyValue20 (int amount) { mapMoney.put(MoneyValue.UNIT_20,amount); return this; }
        public Builder addMoneyValue50 (int amount) { mapMoney.put(MoneyValue.UNIT_50,amount); return this; }
        public Builder addMoneyValue100 (int amount) { mapMoney.put(MoneyValue.UNIT_100,amount); return this; }
        public Builder addMoneyValue200 (int amount) { mapMoney.put(MoneyValue.UNIT_200,amount); return this; }
        public Builder addMoneyValue500 (int amount) { mapMoney.put(MoneyValue.UNIT_500,amount); return this; }
        public ATMImp build() {
            for  (MoneyValue moneyValue: MoneyValue.values()){
                mapMoney.putIfAbsent(moneyValue, 0);
            } return new ATMImp(this);
        }
    }
}


