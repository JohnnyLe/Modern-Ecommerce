/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.util;

import java.util.LinkedList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author VS9 X64Bit
 */
@Component
public class QueryBuilder {

    private final List<String> selectClaseArr;
    private final List<String> fromClauseArr;
    private final List<String> whereClauseArr;
    private final List<String> orderClauseArr;

    public QueryBuilder() {
        this.selectClaseArr = new LinkedList<>();
        this.fromClauseArr = new LinkedList<>();
        this.whereClauseArr = new LinkedList<>();
        this.orderClauseArr = new LinkedList<>();
    }

    public int addSelectData(String sql) {
        return this.selectClaseArr.add(sql) ? this.selectClaseArr.size() : -1;
    }

    public void removeSelectData(int index) {
        this.selectClaseArr.remove(index);
    }

    public int addJoinData(String sql) {
        return this.fromClauseArr.add(sql) ? this.fromClauseArr.size() : -1;
    }

    public void removeJoinData(int index) {
        this.fromClauseArr.remove(index);
    }

    public int addWhereCondition(String sql) {
        return this.whereClauseArr.add(sql) ? this.whereClauseArr.size() : -1;
    }

    public void removeWhereCondition(int index) {
        this.whereClauseArr.remove(index);
    }

    public int addOrderCondition(String sql) {
        return this.orderClauseArr.add(sql) ? this.orderClauseArr.size() : -1;
    }

    public void removeOrderCondition(int index) {
        this.orderClauseArr.remove(index);
    }

    public String buildQuery() {
        if (selectClaseArr.isEmpty() && fromClauseArr.isEmpty()) {
            // sql error, don't have any select data
            return null;
        } else {
            StringBuilder strBuilder = new StringBuilder();

            // building SELECT clause
            if (!selectClaseArr.isEmpty()) {
                strBuilder.append("SELECT ");
                appendQuery(selectClaseArr, strBuilder);
            }

            // building FROM clause
            if (!fromClauseArr.isEmpty()) {
                strBuilder.append("FROM ");
                appendQuery(fromClauseArr, strBuilder);
            }

            // building WHERE clause
            if (!whereClauseArr.isEmpty()) {
                strBuilder.append("WHERE ");
                appendQuery(whereClauseArr, strBuilder);
            }

            // building ORDER clause
            if (!orderClauseArr.isEmpty()) {
                strBuilder.append("ORDER BY ");
                appendQuery(orderClauseArr, strBuilder);
            }

            return strBuilder.toString();
        }
    }

    private void appendQuery(List<String> data, StringBuilder strBuilder) {
        for (int i = 0; i < data.size(); i++) {
            if (i != 0) {
                strBuilder.append(", ");
            }
            strBuilder.append(data.get(i)).append(" ");
        }
    }

}
