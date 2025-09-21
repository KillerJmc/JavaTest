package com.test.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.jmc.lang.BuiltIns.printf;
import static com.jmc.lang.BuiltIns.println;

/**
 * 信用卡计算类
 * @author Jmc
 */
public class CreditCardCalcUtils {
    /**
     * 计算最低利息（每月还10%）
     *
     *
     * @param billingAmount 订单金额
     * @param purchaseDate  购买日期
     * @param billingDay    账单日（每月第几天）
     * @param dueDay        还款日（每月第几天）
     */
    public static void calcMinInterest(double billingAmount, LocalDate purchaseDate, int billingDay, int dueDay) {
        println(String.format("""
        购买日：%s
        订单金额：%.2f 元
        """, purchaseDate, billingAmount));

        // 每次还款百分比
        var repaymentRatePerTime = 0.1;
        // 每日利息率
        double interestDayRate = 0.0005;

        /*
         账单日计算
         购买日：2月5日
         账单日：6 (billingDay) -> 2月6日，5 -> 2月5日，4 -> 3月4日

         还款日计算
         账单日：2月5日
         还款日：6 (dueDay) -> 2月6日，5 -> 2月5日，4 -> 3月4日
         */
        // 当月的账单日
        var currBillingDate = purchaseDate.getDayOfMonth() < billingDay ? purchaseDate.withDayOfMonth(billingDay) :
                purchaseDate.plusMonths(1).withDayOfMonth(billingDay);;
        // 当月的还款日
        var currRepaymentDate = currBillingDate.getDayOfMonth() < dueDay ? currBillingDate.withDayOfMonth(dueDay) :
                currBillingDate.plusMonths(1).withDayOfMonth(dueDay);
        // 总利息
        double totalInterest = 0;
        // 累计还款金额
        double totalRepayment = 0;
        // 是否是第一次还款
        var isFirstRepay = true;

        for (int month = 1; month * repaymentRatePerTime <= 1; month++) {
            // 当月产生的利息
            double interest;

            // 第x个月账单日和还款日之间的天数
            var betweenBillingDateAndRepaymentDate = ChronoUnit.DAYS.between(currBillingDate, currRepaymentDate) + 1;

            /*
             利息计算
             第x月利息计算公式如下：

             1. 对于第1个月 (x = 1)：
                利息 = [(第1月出账日 - 购买日) + 0.9 * (第1月还款日 - 第1月出账日)] * 订单金额 * 每日利息率

             2. 对于第2到第10个月 (2 <= x <= 10)：
                利息 = (1 - 0.1 * x) * (第x月还款日 - 第x月出账日) * 订单金额 * 每日利息率

             其中，x 是当前月份。每月订单金额逐渐减少10%
             */
            if (isFirstRepay) {
                // 购买日和账单日之间的天数
                var betweenPurchaseDateAndBillingDate = ChronoUnit.DAYS.between(purchaseDate, currBillingDate) + 1;
                interest = (betweenPurchaseDateAndBillingDate + 0.9 * betweenBillingDateAndRepaymentDate)
                        * billingAmount * interestDayRate;
                isFirstRepay = false;
            } else {
                interest = (1 - 0.1 * month) * betweenBillingDateAndRepaymentDate * billingAmount * interestDayRate;
            }

            // 当月需还的总金额
            var repayAmount = billingAmount * repaymentRatePerTime + interest;

            // 累计还款金额
            totalRepayment += repayAmount;

            printf(
                    """
                    第%d个月:
                      - 账单日：%s
                      - 还款日：%s
                      - 产生利息：%.2f 元
                      - 本月需还：%.2f 元
                      - 累计还款：%.2f 元

                    """,
                    month,
                    currBillingDate,
                    currRepaymentDate,
                    interest,
                    repayAmount,
                    totalRepayment
            );
            
            // 累加总利息
            totalInterest += interest;
            
            // 计算下个月的账单日和还款日
            currBillingDate = currBillingDate.plusMonths(1);
            currRepaymentDate = currRepaymentDate.plusMonths(1);
        }

        // 总还款金额
        double totalRepaymentAmount = billingAmount + totalInterest;
        // 总还款天数
        long totalRepaymentDay = ChronoUnit.DAYS.between(purchaseDate, currRepaymentDate);
        // 月利率 = 总利息 / 订单金额 / 还款月数 * 100%
        double interestRatePerMonth = totalInterest / billingAmount / (1 / repaymentRatePerTime) * 100;
        // 日利率 = 总利息 / 订单金额 / 总还款天数 * 100%
        double interestRatePerDay = totalInterest / billingAmount / totalRepaymentDay * 100;
        printf(
                """
                总利息：%.2f 元
                总还款：%.2f 元
                月利率：%.2f%%
                日利率：%.2f%%
                """,
                totalInterest,
                totalRepaymentAmount,
                interestRatePerMonth,
                interestRatePerDay
        );
    }

    void main() {
        CreditCardCalcUtils.calcMinInterest(
                21000,
                LocalDate.of(2024, 4, 21),
                20,
                14
        );
    }
}
