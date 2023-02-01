package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SellerVo {
    private String user_id;
    private String bank_name;
    private long bank_account;
    private String bank_accountowner;
    private long business_number;
    private String description;
    private String market_name;

    private String mainImg;

    public SellerVo() {
    }

    public SellerVo(String user_id, String bank_name, int bank_account, String bank_accountowner, int business_number, String description,String market_name,String mainImg) {
        this.user_id = user_id;
        this.bank_name = bank_name;
        this.bank_account = bank_account;
        this.bank_accountowner = bank_accountowner;
        this.business_number = business_number;
        this.description = description;
        this.market_name = market_name;
        this.mainImg = mainImg;
    }
}
