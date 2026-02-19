package dev.sujeet.splitwise.service.strategy;

import dev.sujeet.splitwise.entity.Expense;
import dev.sujeet.splitwise.entity.Transaction;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settleUp(List<Expense> expense);
}
