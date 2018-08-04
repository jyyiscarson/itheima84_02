package cn.itcast.controller;

import cn.itcast.pojo.ResultModel;
import cn.itcast.service.impl.ItemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.management.Agent;

@Controller
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @RequestMapping("list")
    public String showList(String queryString, String catalog_name, String price, @RequestParam(defaultValue = "1") String sort, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "30") Integer rows, Model model){

        ResultModel rModel = itemService.queryProduct(queryString, catalog_name, price, sort, page, rows);;
        model.addAttribute("queryString",queryString);
        model.addAttribute("catalog_name",catalog_name);
        model.addAttribute("price",price);
        model.addAttribute("sort",sort);
        model.addAttribute("result",rModel);
        return "product_list";
    }
}
