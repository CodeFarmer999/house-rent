package com.turing.dao;

import java.util.List;

/**
 * @author Turing
 * @date 2022/5/9 0:51
 **/
public interface BaseDao<T> {

    /**
     * Title: insert Description: 通过对象插入数据
     */
    int insert(T t) throws Exception;

    /**
     * Title: update Description: 通过对象更改对应id的数据
     */
    int update(T t) throws Exception;

    /**
     * Title: delete Description: 通过对象的id属性删除数据
     */
    int delete(T t) throws Exception;

    /**
     * Title: selectById Description: 通过对象的id的属性查询数据
     */
    T selectById(T t) throws Exception;

    /**
     * Title: selectAll Description: 查询所有的数据
     */
    List<T> selectAll() throws Exception;
}
 
