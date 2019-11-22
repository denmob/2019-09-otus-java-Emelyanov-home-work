package ru.otus.hw06;

class TestResult {

    private int failedTest;

    private int succeedTest;
    private int countTest;

    private StringBuilder exceptionDesc = new StringBuilder().append("\n");

    int getFailedTest() {
        return failedTest;
    }
    int getSucceedTest() {
        return succeedTest;
    }
    int getCountTest() {
        return countTest;
    }

    StringBuilder getExceptionDescription() {
        return exceptionDesc;
    }

    void addExceptionDescription(String exceptionDescription) {
        this.exceptionDesc.append(exceptionDescription);
    }

    void increaseFailedTest() {
        this.failedTest++;
    }
    void increaseCountTest() {
        this.countTest++;
    }
    void increaseSucceededTests() { this.succeedTest++; }

}
