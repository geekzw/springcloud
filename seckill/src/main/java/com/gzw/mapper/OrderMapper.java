package com.gzw.mapper;

import com.gzw.daomain.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by gujian on 2017/10/11.
 */
@Mapper
public interface OrderMapper {


    @Insert("insert into t_order(order_no,user_id,com_id,sec_status,com_price) values(" +
            "#{orderNo},#{userId},#{comId},#{secStatus},#{comPrice})")
    int addOrder(Order order);

    @Select("select * from t_order where order_no=#{orderNo}")
    Order findByOrderNo(@Param("orderNo") String orderNo);

    @Select("select * from t_order where user_id=#{userId} limit #{limit} offset #{offset}")
    List<Order> findByUserId(@Param("userId") Integer userId,@Param("offset") Integer offset,@Param("limit") Integer limit);

    @Update("update t_order set sec_status=#{status} where order_no=#{orderNo}")
    int updateStatus(@Param("orderNo") String orderNo,@Param("status") Integer status);
}
