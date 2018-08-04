package cn.itcast.service;

import cn.itcast.pojo.ResultModel;

public interface ItemService {

    public ResultModel queryProduct(String queryString,String catalog_name,String price,String sort,Integer page,Integer rows);
}
