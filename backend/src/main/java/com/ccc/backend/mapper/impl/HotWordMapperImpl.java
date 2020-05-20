package com.ccc.backend.mapper.impl;

import com.ccc.backend.mapper.HotWordMapper;
import com.ccc.backend.pojo.Attitude;
import com.ccc.backend.pojo.HotWord;
import com.ccc.backend.pojo.HotWordValue;
import com.ccc.backend.pojo.PosNegNue;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: Xin(Shawn) Wu
 * @created: 2020/05/20 23:03
 */

@Repository
public class HotWordMapperImpl implements HotWordMapper {
    @Autowired
    private CouchDbConnector connector;

    @Override
    public List<HotWord> getAll() {
        List<HotWord> reslut = new ArrayList<>();
        ViewQuery query = new ViewQuery()
                .designDocId("_design/twitter")
                .viewName("_view").viewName("hotwords").groupLevel(1);

        ViewResult result = connector.queryView(query);
        for (ViewResult.Row row : result) {
            HotWord hotWord = new HotWord();
            HotWordValue hotWordValue = new HotWordValue();
            System.out.println(row);
            System.out.println(row.getKey());
            System.out.println(row.getValue());

            String word = row.getKey();
            hotWord.setWord(word.substring(2,word.length()-2));
            hotWordValue.setPositive(row.getValueAsNode().get("positive").asInt());
            hotWordValue.setNegative(row.getValueAsNode().get("negative").asInt());
            hotWordValue.setNeutral(row.getValueAsNode().get("neutral").asInt());
            hotWordValue.setAverage(row.getValueAsNode().get("average").floatValue());

            hotWord.setValue(hotWordValue);
            reslut.add(hotWord);
        }
        return reslut;
    }
}