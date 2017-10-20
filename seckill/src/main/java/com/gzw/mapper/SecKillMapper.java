package com.gzw.mapper;

import com.gzw.daomain.SecKill;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by gujian on 2017/10/11.
 */
@Mapper
public interface SecKillMapper {

    @Insert("insert into t_seckill(com_name,com_price,storage_count,start_time,end_time)" +
            " values(#{comName},#{comPrice},#{storageCount},#{startTime},#{endTime})")
    int addSecKillCom(SecKill secKill);

    @Select("select * from t_seckill limit #{limit} offset #{offset}")
    List<SecKill> getSecKills(@Param("offset") int offset,@Param("limit") int limit);

    @Select("select * from t_seckill where id=#{id}")
    SecKill getSecKillById(@Param("id") Integer id);

    @Update("update t_seckill set storage_count=storage_count-1 where id=#{comId}")
    int inCrementStorage(@Param("comId") Integer comId,@Param("currentDate") String currentDate);
}
