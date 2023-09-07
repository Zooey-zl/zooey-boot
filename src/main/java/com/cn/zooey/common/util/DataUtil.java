package com.cn.zooey.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * @Author Fengzl
 * @Date 2023/6/26 11:23
 * @Desc
 **/
public class DataUtil {

    public static void main(String[] args) {
        double d1 = 36.30D;
        double d2 = 12.10D;

        BigDecimal divide = BigDecimal.valueOf(d1).divide(BigDecimal.valueOf(d2), 2, RoundingMode.HALF_UP);

        System.out.println(divide);

        // BigDecimal 的坑
        // 1. 浮点类型的坑；new 浮点型的数据会造成精度损失，建议使用字符串或者使用valueOf
        // 2. 浮点精度的坑；equals 和 compare，equals不仅比较了值还比较了精度，compare是比较值，所以看情况使用
        // 3. 设置精度的坑；Bigdecimal类型的值计算时要设置结果的精度和舍入模式，不然一些特殊情况下会报异常
        // 4. 输出字符串的三种类型：toPlainString() - 不使用任何科学计数法
        //                        toString() - 必要时使用科学计数法
        //                        toEngineeringString() - 在必要的时候使用工程计数法。类似于科学计数法，只不过指数的幂都是3的倍数
        System.out.println(BigDecimal.valueOf(0.1));
        System.out.println(new BigDecimal(0.1));

        System.out.println(new BigDecimal("0.1").equals(new BigDecimal("0.10")));
        System.out.println(new BigDecimal("0.1").compareTo(new BigDecimal("0.10")) == 0);


        NumberFormat currency = NumberFormat.getCurrencyInstance(); //建立货币格式化引用
        NumberFormat percent = NumberFormat.getPercentInstance();  //建立百分比格式化引用
        percent.setMaximumFractionDigits(3); //百分比小数点最多3位

        BigDecimal loanAmount = new BigDecimal("15000.48"); //金额
        BigDecimal interestRate = new BigDecimal("0.008"); //利率
        BigDecimal interest = loanAmount.multiply(interestRate); //相乘

        System.out.println("金额:\t" + currency.format(loanAmount));
        System.out.println("利率:\t" + percent.format(interestRate));
        System.out.println("利息:\t" + currency.format(interest));


    }



}
