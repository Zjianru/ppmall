package com.ppmall.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.pojo
 * 2019/1/15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PpmallCategory implements Serializable {
    /** 类别Id*/
    private Integer id;

    /** 父类别id当id=0时说明是根节点,一级类别 */
    private Integer parentId;

    /** 类别名称 */
    private String name;

    /** 类别状态1-正常,2-已废弃 */
    private Boolean status;

    /** 排序编号,同类展示顺序,数值相等则自然排序 */
    private Integer sortOrder;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}