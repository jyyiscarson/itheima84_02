package cn.itcast.dao;

import cn.itcast.pojo.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;

public interface ItemDao  {
    public ResultModel queryProducts(SolrQuery solrQuery);
}
