package io.winters.mall.service;

import io.winters.mall.domain.MallGoodsDO;
import io.winters.mall.util.PageQueryUtil;
import io.winters.mall.util.PageResult;

import java.util.List;

public interface MallGoodsService {

    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    //PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    //String saveNewBeeMallGoods(MallGoodsDO goods);

    /**
     * 批量新增商品数据
     *
     * @param newBeeMallGoodsList
     * @return
     */
    //void batchSaveNewBeeMallGoods(List<MallGoodsDO> newBeeMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    //String updateNewBeeMallGoods(MallGoodsDO goods);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    //Boolean batchUpdateSellStatus(Long[] ids, int sellStatus);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    //MallGoodsDO getNewBeeMallGoodsById(Long id);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    //PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil);

}
