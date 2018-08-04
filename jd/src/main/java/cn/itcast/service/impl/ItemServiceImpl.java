package cn.itcast.service.impl;

import cn.itcast.dao.impl.ItemDaoImpl;
import cn.itcast.pojo.ResultModel;
import cn.itcast.service.ItemService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDaoImpl itemDao;

    public ResultModel queryProduct(String queryString, String catalog_name, String price, String sort, Integer page, Integer rows) {
        SolrQuery solrQuery = new SolrQuery();
        if (queryString != null && !"".equals(queryString)){
            solrQuery.setQuery(queryString);
        }else {
            solrQuery.setQuery("*:*");
        }

        if (catalog_name != null && !"".equals(catalog_name)){
            solrQuery.addFilterQuery("product_catalog_name:"+catalog_name);
        }

        if (price != null && !"".equals(price)){
            String[] prices = price.split("-");
            solrQuery.addFilterQuery("product_price:["+ prices[0] + " TO "+ prices[1] + "]");
        }

        if ("1".equals(sort)){
            solrQuery.setSort("product_price",SolrQuery.ORDER.asc);
        }else {
            solrQuery.setSort("product_price", SolrQuery.ORDER.desc);
        }

        int startN = (page - 1) * rows;
        solrQuery.setStart(startN);
        solrQuery.setRows(rows);

        solrQuery.setHighlight(true);

        solrQuery.addHighlightField("product_name");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");

        solrQuery.set("df","product_keywords");

        ResultModel resultModel = itemDao.queryProducts(solrQuery);
        Integer totalCount = resultModel.getTotalCount();
        Integer totalPage = totalCount /  rows;
        if (totalCount % rows > 0){
            totalPage ++;
        }
        resultModel.setTotalPages(totalPage);
        resultModel.setCurPage(page);

        return resultModel;
    }
}
