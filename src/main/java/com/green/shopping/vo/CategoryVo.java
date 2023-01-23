package com.green.shopping.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CategoryVo {
    private String Name;
    private String Parent_Num;
    private String Num;
    private int Lv;
    public CategoryVo() {
    }
}
