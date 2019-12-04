package ru.otus.hw07.DepartmentATM;

import ru.otus.hw07.ATM.ATMImp;
import ru.otus.hw07.ATM.MoneyValue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DepartmentATM implements ATMGroup {

        private final List<ATMImp> atms = new ArrayList<>();

        public void addATM(ATMImp atmImp) {
            atms.add(atmImp);
        }

        @Override
        public Map<MoneyValue,Integer> getMoneyATM() {

            Map<MoneyValue,Integer> moneyValueIntegerMap = new HashMap<>();

            for (ATMImp atm : atms) {
                 moneyValueIntegerMap = Stream.concat(moneyValueIntegerMap.entrySet().stream(), atm.getMoneyATM().entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum));
            }
            printAllATMValues(moneyValueIntegerMap);
            return moneyValueIntegerMap;
        }

        private void printAllATMValues(Map<MoneyValue, Integer> moneyValueIntegerMap) {

            System.out.println("Вывод средств собранных со всех АТМ департамента");

            int sum = 0;
            for  (MoneyValue moneyValue: MoneyValue.values()) {
                int moneyValueCount =  moneyValueIntegerMap.get(moneyValue);
                sum = sum + moneyValueCount* moneyValue.getMoneyValue();
                System.out.println(String.format("Количество купюр номиналом %s - " + moneyValueCount,moneyValue.toString()));
            }
            if (sum > 0) {
                System.out.println("Общая сумма = " + sum);
            }

        }

        @Override
        public void printATMValues() {
            for (ATMImp atm:atms) {
                atm.printATMValues();
            }
        }

        @Override
        public void resetStateToInitATM() {
            for (ATMImp atm:atms) {
                atm.resetStateToInitATM();
            }
        }

}
