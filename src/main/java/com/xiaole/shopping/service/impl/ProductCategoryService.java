package com.xiaole.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaole.shopping.entity.Product;
import com.xiaole.shopping.entity.ProductCategory;
import com.xiaole.shopping.mapper.ProductCategoryMapper;
import com.xiaole.shopping.mapper.ProductMapper;
import com.xiaole.shopping.service.IProductCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaole.shopping.vo.ProductCategoryVO;
import com.xiaole.shopping.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
@Service
public class ProductCategoryService extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements IProductCategoryService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductCategoryVO> getAllProductCategoryVO() {
//        /*实体类转VO*/
//        List<ProductCategory> productCategoriesList = productCategoryMapper.selectList(null);
//        /*VO*/
//        List<ProductCategoryVO> productCategoryVOList =new ArrayList<>();
//        for (ProductCategory productCategory: productCategoriesList
//             ) {
//            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
//            //可采用这种方式优化属性复制
//            BeanUtils.copyProperties(productCategory,productCategoryVO);
////            productCategoryVO.setId(productCategory.getId());
////            productCategoryVO.setName(productCategory.getName());
//            productCategoryVOList.add(productCategoryVO);
        /**先查询一级分类*/
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type",1);
        List<ProductCategory> levelOne = productCategoryMapper.selectList(queryWrapper);
//        List<ProductCategoryVO> level_one_VO =new ArrayList<>();
//        for (ProductCategory pro:level_one
//             ) {
//            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
//            BeanUtils.copyProperties(pro,productCategoryVO);
//            level_one_VO.add(productCategoryVO);
//        }
        //可采用stream流去处理
        List<ProductCategoryVO> level_one_VO = levelOne.stream().map(e -> new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
        /**图片赋值*/
        /**商品信息赋值*/
        for (int i = 0; i < level_one_VO.size(); i++) {
            level_one_VO.get(i).setBannerImg("banner"+i+".png"); //遍历底部图片
            level_one_VO.get(i).setTopImg("top"+i+".png"); //遍历一级菜单左上的图片
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("categorylevelone_id",level_one_VO.get(i).getId());
            List<Product> list = productMapper.selectList(queryWrapper);
            List<ProductVO> ProductVOlist = list.stream().map(e -> new ProductVO(e.getId(), e.getName(), e.getPrice(), e.getFileName())).collect(Collectors.toList());
            level_one_VO.get(i).setProductVOlist(ProductVOlist);
        }
        for (ProductCategoryVO levelOneProVO:level_one_VO
             ) {
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("type",2);
            queryWrapper.eq("parent_id",levelOneProVO.getId());
            List<ProductCategory> levelTwo = productCategoryMapper.selectList(queryWrapper);
            List<ProductCategoryVO> level_two_VO = levelTwo.stream().map(e -> new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
            levelOneProVO.setChildren(level_two_VO);
            for (ProductCategoryVO levelTwoProVO:level_two_VO
            ) {
                queryWrapper = new QueryWrapper();
                queryWrapper.eq("type",3);
                queryWrapper.eq("parent_id",levelTwoProVO.getId());
                List<ProductCategory> levelThree = productCategoryMapper.selectList(queryWrapper);
                List<ProductCategoryVO> level_three_VO = levelThree.stream().map(e -> new ProductCategoryVO(e.getId(),e.getName())).collect(Collectors.toList());
                levelTwoProVO.setChildren(level_three_VO);
            }
        }
        return level_one_VO;
    }
}
