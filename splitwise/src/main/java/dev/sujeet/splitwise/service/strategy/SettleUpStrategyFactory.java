package dev.sujeet.splitwise.service.strategy;

public class SettleUpStrategyFactory {
    public  static SettleUpStrategy getSettleUpStarategy(){
        return new MaxBorrowerLenderMatchSettleUpStrategy();
    }
}
