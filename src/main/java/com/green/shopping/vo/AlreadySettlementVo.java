package com.green.shopping.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AlreadySettlementVo {
    private int id;
    private String user_id;
    private String market_name;
    private int totalprice;
    private long bank_account;
    private String bank_name;
    private String bank_accountowner;
    private String settle_date;
    private String idlist;
}
