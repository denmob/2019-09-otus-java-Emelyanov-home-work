package ru.otus.hw07.ATM;


import ru.otus.hw07.ATM.MoneyCell.MoneyCellImp;
import ru.otus.hw07.ATM.MoneyWithdraw.MoneyWithdrawImp;
import ru.otus.hw07.DepartmentATM.ATMGroupInterface;

import java.util.HashMap;
import java.util.Map;

public class ATMImp implements ATMInterface, ATMGroupInterface {

    private final MoneyCellImp moneyCellImp;

    private ATMImp(Builder moneyBuilder) {
        this.moneyCellImp = new MoneyCellImp(moneyBuilder.getMapMoney());
    }

    @Override
    public void withdrawMoney(int value) {
        System.out.println("Необходимо выдать сумму: "+value);
        MoneyWithdrawImp moneyWithdraw = new MoneyWithdrawImp(value,moneyCellImp.clone());

        if (moneyWithdraw.validateMoneyToWithdraw()) {
            for  (MoneyValue moneyValue: MoneyValue.values())
                moneyCellImp.getMoney(moneyValue, moneyWithdraw.getMoneyValueCount(moneyValue));
        }
    }

    @Override
    public String depositMoney(MoneyValue moneyValue, Integer count) {
        if (count>0) {
            moneyCellImp.putMoney(moneyValue, count);
            return "Средства успешно внесены.";
        } else return "Количество купюр должно быть больше 0.";
    }

    @Override
    public void printATMValues() {
        int sum = 0;
        for  (MoneyValue moneyValue: MoneyValue.values()){
            int moneyValueCount =  moneyCellImp.getMoneyValueCount(moneyValue);
            sum = sum + moneyValueCount* Integer.parseInt(moneyValue.toString());
            System.out.println(String.format("Количество купюр номиналом %s - " + moneyValueCount,moneyValue.toString()));
        }
        if (sum > 0) {
            System.out.println("Общая сумма денежных стредств в ATM = " + sum);
        }
    }

    @Override
    public Integer getMoneyATM() {
        return moneyCellImp.getAllMoney();
    }

    public static class Builder {
        Map<MoneyValue,Integer> mapMoney = new HashMap<>();
        public Builder() { }
        private Map<MoneyValue, Integer> getMapMoney() { return mapMoney; }
        public Builder addMoneyValue10 (int amount) { mapMoney.put(MoneyValue.unit10,amount); return this; }
        public Builder addMoneyValue20 (int amount) { mapMoney.put(MoneyValue.unit20,amount); return this; }
        public Builder addMoneyValue50 (int amount) { mapMoney.put(MoneyValue.unit50,amount); return this; }
        public Builder addMoneyValue100 (int amount) { mapMoney.put(MoneyValue.unit100,amount); return this; }
        public Builder addMoneyValue200 (int amount) { mapMoney.put(MoneyValue.unit200,amount); return this; }
        public Builder addMoneyValue500 (int amount) { mapMoney.put(MoneyValue.unit500,amount); return this; }
        public ATMImp build() {
            for  (MoneyValue moneyValue: MoneyValue.values()){
                mapMoney.putIfAbsent(moneyValue, 0);
            } return new ATMImp(this);
        }
    }
}


