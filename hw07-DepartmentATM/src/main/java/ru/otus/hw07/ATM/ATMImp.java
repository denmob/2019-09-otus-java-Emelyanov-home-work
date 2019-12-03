package ru.otus.hw07.ATM;


import ru.otus.hw07.ATM.MoneyCell.MoneyCellImp;
import ru.otus.hw07.ATM.MoneyWithdraw.MoneyWithdrawImp;
import ru.otus.hw07.DepartmentATM.ATMGroup;

import java.util.HashMap;
import java.util.Map;

public class ATMImp implements ATM, ATMGroup {

    private MoneyCellImp moneyCellImp;
    private final String name;
    private final StateMoneyCell stateMoneyCell;

    private ATMImp(Builder moneyBuilder) {
        stateMoneyCell = new StateMoneyCell(new MoneyCellImp(moneyBuilder.getMapMoney()));
        this.moneyCellImp = stateMoneyCell.getMoneyCellInit();
        this.name = moneyBuilder.name;
    }

    @Override
    public void withdrawMoney(int value) {
        System.out.println("Необходимо выдать сумму: "+value);
        MoneyWithdrawImp moneyWithdraw = new MoneyWithdrawImp(value,moneyCellImp.clone());
        if (moneyWithdraw.validateMoneyToWithdraw()) {
            for  (MoneyValueV2 moneyValue: MoneyValueV2.values())
                moneyCellImp.getMoney(moneyValue, moneyWithdraw.getMoneyValueCount(moneyValue));
        }
    }

    @Override
    public String depositMoney(MoneyValueV2 moneyValue, Integer count) {
        if (count>0) {
            moneyCellImp.putMoney(moneyValue, count);
            return "Средства успешно внесены.";
        } else return "Количество купюр должно быть больше 0.";
    }

    @Override
    public void printATMValues() {
        System.out.println("ATM name " + this.name);
        int sum = 0;
        for  (MoneyValueV2 moneyValue: MoneyValueV2.values()){
            int moneyValueCount =  moneyCellImp.getMoneyValueCount(moneyValue);
            sum = sum + moneyValueCount* moneyValue.getMoneyValue();
            System.out.println(String.format("Количество купюр номиналом %s - " + moneyValueCount,moneyValue.toString()));
        }
        if (sum > 0) {
            System.out.println("Общая сумма денежных стредств в ATM = " + sum);
        }
    }

    @Override
    public void resetStateToInitATM() {
        this.moneyCellImp = stateMoneyCell.getMoneyCellInit();
    }

    @Override
    public Map<MoneyValueV2,Integer> getMoneyATM() {
        Map<MoneyValueV2,Integer> moneyValueIntegerMap = moneyCellImp.getAllMoney();
        moneyCellImp = stateMoneyCell.getMoneyCellEmpty();
        return moneyValueIntegerMap;
    }

    public static class Builder {
        Map<MoneyValueV2,Integer> mapMoney = new HashMap<>();
        private final String name;
        public Builder(String name) { this.name = name; }
        private Map<MoneyValueV2, Integer> getMapMoney() { return mapMoney; }
        public Builder addMoneyValue10 (int amount) { mapMoney.put(MoneyValueV2.UNIT_10,amount); return this; }
        public Builder addMoneyValue20 (int amount) { mapMoney.put(MoneyValueV2.UNIT_20,amount); return this; }
        public Builder addMoneyValue50 (int amount) { mapMoney.put(MoneyValueV2.UNIT_50,amount); return this; }
        public Builder addMoneyValue100 (int amount) { mapMoney.put(MoneyValueV2.UNIT_100,amount); return this; }
        public Builder addMoneyValue200 (int amount) { mapMoney.put(MoneyValueV2.UNIT_200,amount); return this; }
        public Builder addMoneyValue500 (int amount) { mapMoney.put(MoneyValueV2.UNIT_500,amount); return this; }
        public ATMImp build() {
            return new ATMImp(this);
        }
    }
}


