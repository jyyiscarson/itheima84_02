package cn.itcast.dao.impl;

import cn.itcast.dao.ItemDao;
import cn.itcast.pojo.Item;
import cn.itcast.pojo.ResultModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ItemDaoImpl  implements ItemDao {

    @Autowired
    private SolrServer solrServer;

    public ResultModel queryProducts(SolrQuery solrQuery) {
        ResultModel resultModel = new ResultModel();
        try {
            QueryResponse response = solrServer.query(solrQuery);
            SolrDocumentList results = response.getResults();
            Long numFound = results.getNumFound();
            resultModel.setTotalCount(numFound.intValue());

            List<Item> itemList = new ArrayList<Item>();

            for (SolrDocument doc : results) {
                Item item = new Item();
                String id = (String) doc.get("id");
               // System.out.println("文档与Id:"+id);
                item.setPid(id);
                String product_name = (String) doc.get("product_name");

                Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
                if ( highlighting!= null && highlighting.size()>0){
                    Map<String, List<String>> stringListMap = highlighting.get(id);
                    List<String> hList = stringListMap.get("product_name");
                    if (hList!=null && hList.size()>0){
                        product_name = hList.get(0);
                    }
                }


                //System.out.println("商品标题:"+product_name);
                item.setName(product_name);



                Float product_price = (Float)doc.get("product_price");
                item.setPrice(product_price);
               // System.out.println("价格:"+product_price);

//                String product_description = (String) doc.get("product_description");
//                System.out.println("商品描述:"+product_description);

                String product_picture = (String)doc.get("product_picture");
                item.setPicture(product_picture);
           //     System.out.println("商品图片:"+product_picture);

//                String product_catalog_name = (String) doc.get("product_catalog_name");
//                System.out.println("商品分类:"+product_catalog_name);
                itemList.add(item);
            }
            resultModel.setProductList(itemList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultModel;
    }
}
