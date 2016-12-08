package com.zoe.snow.model;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回可以为空的列表，减少二手异常发生机率
 * 例如：Result<PageList<User>> pageList= result.getData();
 * 如果在获取result时服务端发生异常，对异常进行了控制，并对异常写了日志，返回null
 * 那么getData()直接返回null，再调用getList()就产生二手异常
 * 因此可以对于空数据可以直接返回NullAble
 *
 * @author Dai Wenqing
 * @date 2016/10/28
 */
@Component("snow.model.nullable.list")
public class NullAbleList extends PageList<Model> {
    /*@Override
    public String getId() {
        return "";
    }

    @Override
    public void setId(String id) {

    }
*/

    @Override
    public List<Model> getList() {
        return new ArrayList<>();
    }


    @Override
    public JSONObject toJson() {
        return new JSONObject();
    }
}
