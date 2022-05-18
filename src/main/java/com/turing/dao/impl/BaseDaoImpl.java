package com.turing.dao.impl;

import com.turing.dao.BaseDao;
import com.turing.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Turing
 * @date 2022/5/9 0:21
 **/

public abstract class BaseDaoImpl<T> extends Thread implements BaseDao<T> {
    // 操作语句常量
    private static final String INSERT = "insert";
    private static final String UPDATE = "update";
    private static final String DELETE = "delete";
    private static final String SELECTBYID = "selectbyid";
    private static final String SELECTALL = "selectall";
    /**
     * runner：执行sql操作
     */
    private static QueryRunner runner = null;
    /**
     * entityClass：子类的类
     */
    private Class<T> entityClass = null;
    /**
     * tableName：子类的类名(表名)
     */
    private String tableName = null;
    /**
     * idIndex：子类表的id下标
     */
    private int idIndex = 0;
    private Field[] fields = null;

    /**
     * 创建子类对象时会调用父类的无参构造方法，从而实现赋值
     */
    public BaseDaoImpl() {
        // 初始化信息
        init();
    }

    /**
     * Title: Description: 提供表名，修改泛型的表名
     * 创建子类对象时会调用父类的无参构造方法，从而实现赋值
     * @param tableName
     */
    public BaseDaoImpl(String tableName) {
        // 初始化信息
        init();
        this.tableName = humpTransfer(tableName);
        System.out.println(this.tableName);
    }

    /**
     * Title: insert Description: 通过对象插入数据
     *
     * @return 执行后，影响的行数
     */
    @Override
    public int insert(T t) {
        int row = -1;
        try {
            row = runner.update(getSql(INSERT), setArgs(INSERT, t));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return row;
    }

    /**
     * Title: update Description: 通过对象更改对应id的数据
     *
     * @return 执行后，影响的行数
     */
    @Override
    public int update(T t) {
        int row = -1;
        try {
            row = runner.update(getSql(UPDATE), setArgs(UPDATE, t));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return row;
    }

    /**
     * Title: delete Description: 通过对象的id属性删除数据
     *
     * @return 执行后，影响的行数
     */
    @Override
    public int delete(T t) {
        int row = -1;
        try {
            return runner.update(getSql(DELETE), setArgs(DELETE, t));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return row;
    }

    /**
     * Title: selectById Description: 通过对象的id的属性查询数据
     *
     * @return 执行后，查找的一个对象
     */
    @Override
    public T selectById(T t) {
        try {
            t = runner.query(getSql(SELECTBYID), new BeanHandler<T>(entityClass), setArgs(SELECTBYID, t));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return t;
    }

    /**
     * Title: selectAll Description: 查询所有的数据
     */
    @Override
    public List<T> selectAll() {
        List<T> result = null;
        try {
            result = runner.query(getSql(SELECTALL), new BeanListHandler<T>(entityClass));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Title: getSql Description: 获取sql语句
     *
     * @throws Exception
     */
    private String getSql(String operation) throws Exception {
        StringBuffer sql = new StringBuffer();
        switch (operation) {
            // 插入语句 insert into <table> values(?,?...);
            case INSERT:
                sql.append("insert into ").append(tableName).append(" values(");
                for (int i = 0; i < fields.length; i++) {
                    sql.append("?,");
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append(");");
                break;
            // 修改语句 update <table> set *=?,*=?... where <id>=?;
            case UPDATE:
                sql.append("update ").append(tableName).append(" set ");
                for (int i = 0; i < fields.length; i++) {
                    if (i != idIndex) {
                        sql.append(humpTransfer(fields[i].getName())).append("=?,");
                    }
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append(" where ").append(humpTransfer(fields[idIndex].getName())).append("=?;");
                break;
            // 删除语句 delete from <table> where <id>=?;
            case DELETE:
                sql.append("delete from ").append(tableName).append(" where ").append(humpTransfer(fields[idIndex].getName()))
                        .append("=?;");
                break;
            // 通过id查询语句 select * from <table> where <id>=?;
            case SELECTBYID:
                sql.append("select * from ").append(tableName).append(" where ").append(humpTransfer(fields[idIndex].getName()))
                        .append("=?;");
                break;
            // 查询全部语句 select * from <table>
            case SELECTALL:
                sql.append("select * from ").append(tableName);
                break;
            // 操作参数传入异常，产生错误，阻止程序继续运行
            default:
                throw new Exception("getSql方法的传入参数有误");
        }
        return sql.toString();
    }

    /**
     * Title: setArgs Description: 把参数包装为数组
     *
     * @throws Exception
     */
    private Object[] setArgs(String operation, T t) throws Exception {
        Object[] args = null;
        switch (operation) {
            // 插入语句 [*,*,*...]
            case INSERT:
                args = new Object[fields.length];
                for (int i = 0; i < args.length; i++) {
                    fields[i].setAccessible(true);
                    args[i] = fields[i].get(t);
                }
                break;
            // 修改语句 [*,*,*...,<id>]
            case UPDATE:
                args = new Object[fields.length];
                for (int i = 0; i < args.length; i++) {
                    fields[i].setAccessible(true);
                    if (i < idIndex) {
                        args[i] = fields[i].get(t);
                    } else if (i == idIndex) {
                        args[args.length - 1] = fields[idIndex].get(t);
                    } else {
                        args[i - 1] = fields[i].get(t);
                    }
                }
                break;
            // 删除语句 删除和通过id查询都只需要<id>参数，可以共用
            case DELETE:
                // 通过id查询语句
            case SELECTBYID:
                fields[idIndex].setAccessible(true);
                args = new Object[]{fields[idIndex].get(t)};
                break;
            // 操作参数传入异常，产生错误，阻止程序继续运行
            default:
                throw new Exception("setArgs方法的第一个传入参数有误");
        }
        return args;
    }

    /**
     * Title: init Description: 初始化信息
     */
    @SuppressWarnings("unchecked") // 不报错提示
    private void init() {
        // 获取QueryRunner对象，进行sql语句操作
        runner = C3P0Utils.getQueryRunner();
        // 获取泛型参数
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        // 获取泛型的类
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
        // 获取子类的表名 .substring(0, getClass().getSimpleName().length() - 4)
        tableName = entityClass.getSimpleName().toLowerCase();
        // 排除常量字段
        List<Field> var = new ArrayList<Field>();
        for (Field field : entityClass.getDeclaredFields()) {
            // 如果不是final修饰字段，则添加到列表中
            if (!java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                var.add(field);
            }
        }
        fields = new Field[var.size()];
        fields = var.toArray(fields);
    }

    public static String humpTransfer(String str) {
        Pattern compile = Pattern.compile("[A-Z]");
        Matcher matcher = compile.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(sb,  "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}

