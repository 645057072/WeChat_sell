package com.hlx.sell.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DynamicUpdate
@Table(name = "seller_info")
public class SellerInfo {
    @Id
    private String Id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String passWord;
    @Column(name = "openid")
    private String openId;
//    @Column(name = "createtime")
////    private Date createTime;
////    @Column(name = "updatetime")
////    private Date updateTime;
}
