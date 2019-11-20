package ru.otus.hw05;

class TestResult {

    private int failedTest;

    private int succeedTest;
    private int countTest;

    private StringBuilder exceptionDesc = new StringBuilder().append("\n");

    int getFailedTest() {
        return failedTest;
    }

    void setFailedTest(int failedTest) {
        this.failedTest = this.failedTest + failedTest;
    }

    int getCountTest() {
        return countTest;
    }

    void setCountTest(int countTest) {
        this.countTest = this.countTest + countTest;
    }

    StringBuilder getExceptionDescription() {
        return exceptionDesc;
    }

    void setExceptionDescription(String exceptionDescription) {
        this.exceptionDesc.append(exceptionDescription);
    }

    int getSucceedTest() {
        return succeedTest;
    }

    void setSucceedTest(int succeedTest) {
        this.succeedTest = this.succeedTest + succeedTest;
    }


}
