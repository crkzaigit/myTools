package com.cdmcs.qx2cdjw.service;

import com.cdmcs.cdjw.tyjk.CdjwInfClientService;
import com.cdmcs.qx2cdjw.bean.ColumnBean;
import com.cdmcs.qx2cdjw.inf.CustomSetData;
import com.cdmcs.qx2cdjw.inf.DynamicBind;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cdmcs on 2015/3/28.
 */
public class SynData2Table {
    private static Logger logger = Logger.getLogger(SynData2Table.class);

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private String endPoint;
    private DataSource dataSource;

    /**
     * 保存主体信息
     * @param tableName 保存数据的表名
     * @param jkid 接口ID
     * @param key 接口密钥
     * @param args 参数
     * @param dynamicBind 动态绑定数据接口
     * @throws Exception
     * @return 最后一条的主键值
     */
    public List<Object> saveZtxx2Table(String owner,String tableName,String tableKey,final String jkid,final String key,final Map<String,String> args,DynamicBind dynamicBind) throws Exception{
        return this.save2Table(owner,tableName,tableKey,new CustomSetData() {
            public Object setData() throws Exception{
                return new CdjwInfClientService(endPoint).ztxx(jkid, key, args);
            }
        },dynamicBind);
    }
    /**
     * 保存数据
     * @param tableName 保存数据的表名
     * @param customSetData 用户数据提供接口
     * @param dynamicBind 动态绑定数据接口
     * @throws Exception
     * @return 最后一条数据主键
     */
    public List<Object> save2Table(String owner,String tableName,String tableKey,CustomSetData customSetData,DynamicBind dynamicBind) throws Exception{
        Connection connection = null ;
        Statement statement = null ;
        ResultSet resultSet = null ;
        PreparedStatement preparedStatement_insert = null;
        PreparedStatement preparedStatement_delete = null;
        List<Object> tableKeyValue = new ArrayList<Object>() ;

        boolean isFound = false;

        String sql = null ;
        List<ColumnBean> columns = new ArrayList<ColumnBean>();
        try{
            owner = owner.toUpperCase();
            tableName = tableName.toUpperCase();
            String insPre = "INSERT INTO "+owner+"."+tableName +" (";
            String insSuf = " VALUES(";

            connection = this.dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            sql = new String("select a.COLUMN_ID,a.COLUMN_NAME,b.comments,a.DATA_TYPE,a.DATA_LENGTH,a.NULLABLE " +
                    "  from all_tab_columns a " +
                    "  join all_col_comments b " +
                    "    on a.TABLE_NAME = b.table_name " +
                    "   and a.COLUMN_NAME = b.column_name " +
                    " where a.TABLE_NAME='"+tableName+"' " +
                    "   and a.OWNER=b.OWNER " +
                    "   and a.OWNER='"+owner+"' " +
                    " order by a.COLUMN_ID ");
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                isFound = true;

                ColumnBean bean = new ColumnBean();
                bean.setColumn_id(resultSet.getInt(1));
                bean.setColumn_name(resultSet.getString(2));
                bean.setComments(resultSet.getString(3));
                bean.setData_type(resultSet.getString(4));
                bean.setData_length(resultSet.getInt(5));
                bean.setNullable(resultSet.getString(6));
                columns.add(bean);

                //组装批量执行SQL的插入语句
                insPre += (bean.getColumn_name()+",") ;
                insSuf += ("?,") ;
            }
            if(!isFound){
                throw new RuntimeException(owner+"."+tableName+" table not found");
            }
            List<List<ColumnBean>> batchList = dynamicBind.process(columns, customSetData.setData());
            String preSql = insPre.substring(0,insPre.length()-1)+")"+insSuf.substring(0,insSuf.length()-1)+")";
            logger.debug(preSql);
            preparedStatement_insert = connection.prepareStatement(preSql);
            if(batchList == null || batchList.size() ==0){
                return tableKeyValue ;
//                String errMsg = "DynamicBind error, return null";
//                if(tableName.toUpperCase().indexOf("T_BMBZJ_SJJH_QY_JBXX")>=0){
//                    errMsg = " Enterprise basic information not found ";
//                }else if (tableName.toUpperCase().indexOf("T_BMBZJ_SJJH_QY_ZZ")>=0){
//                    errMsg = " Enterprise qualification information not found ";
//                }else if (tableName.toUpperCase().indexOf("VW_ZBBA_SYSDW_ZBDL")>=0){
//                    errMsg = " bidding agency information not found ";
//                }else if (tableName.toUpperCase().indexOf("T_BMBZJ_YW_QYYHXX")>=0){
//                    errMsg = " bidding agency information not found ";
//                }else if (tableName.toUpperCase().indexOf("T_BMBZJ_YW_QYYHXX_LSSQ")>=0){
//                    errMsg = " bidding agency information not found ";
//                }else{
//                    throw new RuntimeException(errMsg);
//                }
            }
            //先删除
            sql = "DELETE FROM "+owner+"."+tableName+" WHERE "+tableKey+" = ?";
            preparedStatement_delete = connection.prepareStatement(sql);
            //插入
            for(List<ColumnBean> row : batchList){
                for(ColumnBean bean : row){
                    logger.debug(bean.getColumn_id() + " = " + bean.getValue());
                    preparedStatement_insert.setObject(bean.getColumn_id(),bean.getValue());
                    if(bean.getColumn_name().equalsIgnoreCase(tableKey)){
                        tableKeyValue.add(bean.getValue());
                        preparedStatement_delete.setObject(1,bean.getValue());
                        preparedStatement_delete.addBatch();
                    }
                }
                preparedStatement_insert.addBatch();
            }
            preparedStatement_delete.executeBatch();
            preparedStatement_insert.executeBatch();
            connection.commit();
            return tableKeyValue;
        }catch (Exception e){
            e.printStackTrace();
            if(connection!=null) connection.rollback();
            throw e;
        }finally {
            if(resultSet!=null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }finally {
                    if(preparedStatement_insert != null){
                        try{
                            preparedStatement_insert.close();
                        }catch (SQLException e){
                            e.printStackTrace();
                        }finally {
                            if(statement!=null){
                                try{
                                    statement.close();
                                }catch (SQLException e){
                                    e.printStackTrace();
                                }finally {
                                    if(connection!=null){
                                        try{
                                            connection.close();
                                        }catch (SQLException e){
                                            e.printStackTrace();
                                        }finally {

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
