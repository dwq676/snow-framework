package com.zoe.snow.dao.hbase;

import com.zoe.snow.dao.DataSourceBean;
import com.zoe.snow.dao.DataSourceManager;
import com.zoe.snow.dao.Mode;
import com.zoe.snow.dao.orm.WhereContext;
import com.zoe.snow.log.Logger;
import com.zoe.snow.model.Model;
import com.zoe.snow.model.PageList;
import com.zoe.snow.model.enums.InterventionType;
import com.zoe.snow.util.Validator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * HBaseDaoImpl
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/4/3
 */
@Repository("snow.dao.hbase")
public class HBaseDaoImpl implements HBaseDao {
    final int MAX_VERSIONS = 10;
    @Autowired
    private HBaseClient hBaseClient;

    @Override
    public String getOrmName() {
        return "hbase";
    }

    @Override
    public <T extends Model> T findById(Class<T> modelClass, String id, String... datasource) {
        return null;
    }

    @Override
    public <T extends Model> T findOne(HBaseQuery query) {
        return null;
    }

    @Override
    public <T extends Model> PageList<T> query(HBaseQuery query) {
        return null;
    }

    @Override
    public int count(HBaseQuery query) {
        return 0;
    }

    @Override
    public <T extends Model> boolean save(T model, InterventionType interventionType, String... datasource) {
        return false;
    }

    @Override
    public boolean update(HBaseQuery query) {
        return false;
    }

    @Override
    public <T extends Model> boolean delete(T model, String... datasource) {
        return false;
    }

    @Override
    public boolean delete(HBaseQuery query) {
        return false;
    }

    @Override
    public JSONObject getAsJsonObject(HBaseQuery query) {
        JSONObject jsonObject = new JSONObject();
        try {
            /*
            System.out.println("tablename:" + new String(table.getTableName()));
            ClientProtos.Scan s = new ClientProtos.Scan();
            ResultScanner scanner = table.getScanner(s);*/
            if (query == null)
                return jsonObject;
            HTable table = new HTable(hBaseClient.get(Mode.Write, "hbase"), query.getFromType());
            List<Get> gets = new ArrayList<>();
            for (WhereContext whereContext : query.getWhereContexts()) {
                Get get = new Get(Bytes.toBytes(whereContext.getValue()[0].toString()));
                get.setMaxVersions(MAX_VERSIONS);
                gets.add(get);
            }
            DataSourceBean dataSourceBean = DataSourceManager.getDataSourceBean("hbase");
            Result[] results = table.get(gets);
            for (Result result : results) {
                JSONObject fJsonObject = new JSONObject();
                for (String f : query.getSelect().split(",")) {
                    List<Cell> values = result.getColumnCells(Bytes.toBytes(dataSourceBean.getSchema()), Bytes.toBytes(f));
                    //familyJsonObject.put(f,Bytes.toString(CellUtil.cloneValue(cell)));
                    JSONArray cellValues = new JSONArray();
                    for (Cell cell : values) {
                        cellValues.add(Bytes.toString(CellUtil.cloneValue(cell)).replace("\"", "'"));
                    }
                    fJsonObject.put(f, cellValues);
                }
                if (!Validator.isEmpty(Bytes.toString(result.getRow())))
                    jsonObject.put(Bytes.toString(result.getRow()), fJsonObject);
            }


        } catch (Exception e) {
            Logger.error(e, "hbase查询数据时发生了错误");
        }
        return jsonObject;
    }

    @Override
    public JSONArray getAsJsonArray(HBaseQuery query) {
        JSONArray jsonArray = new JSONArray();
        return jsonArray;
    }
}
