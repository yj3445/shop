package com.itshop.web.manager;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.itshop.web.dao.mysql.TDictionaryDAO;
import com.itshop.web.dao.mysql.TDictionaryTypeDAO;
import com.itshop.web.dao.po.TDictionary;
import com.itshop.web.dao.po.TDictionaryType;
import com.itshop.web.dao.po.TIdcFirewallOrder;
import com.itshop.web.dto.PageParam;
import com.itshop.web.dto.request.TIdcFirewallOrderQueryParam;
import com.itshop.web.dto.response.DictionaryResp;
import com.itshop.web.dto.response.DictionaryTypeResp;
import com.itshop.web.dto.response.IdcFireWallOrderResp;
import com.itshop.web.util.PageInfoUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class DictionaryManager {

    @Autowired
    TDictionaryTypeDAO tDictionaryTypeDAO;

    @Autowired
    TDictionaryDAO  dictionaryDAO;

    /**
     * 根据字典类型id得到字典值
     *
     * @param dictionaryTypeId
     * @return
     */
    public DictionaryTypeResp getDictionaryTypeById(Integer dictionaryTypeId) {
        DictionaryTypeResp resp = new DictionaryTypeResp();
        TDictionaryType type = tDictionaryTypeDAO.selectByPrimaryKey(dictionaryTypeId);
        if (type != null) {
            BeanUtils.copyProperties(type,resp);
            resp.setItems(getDictionaryByTypeId(dictionaryTypeId));
        }
        return resp;
    }

    private List<DictionaryResp> getDictionaryByTypeId(Integer dictionaryTypeId) {
        List<DictionaryResp> items = Lists.newArrayList();
        List<TDictionary> dictionaries = dictionaryDAO.selectByDictionaryTypeId(dictionaryTypeId);
        if(!CollectionUtils.isEmpty(dictionaries)){
            dictionaries.forEach(d ->{
                DictionaryResp dictionaryResp = new DictionaryResp();
                BeanUtils.copyProperties(d,dictionaryResp);
                items.add(dictionaryResp);
            });
        }
        return items;
    }

    /**
     * 得到字典类型列表
     *
     * @param pageParam 分页参数
     * @return PageInfo
     */
    public PageInfo<DictionaryTypeResp> selectByQueryParam(PageParam pageParam) {
        String orderBy;
        if (StringUtils.isEmpty(pageParam.getSortName())) {
            orderBy = "create_time desc ";
        } else {
            orderBy = pageParam.getSortName() + (pageParam.getReverse() ? " desc" : " asc");
        }
        PageInfo<TDictionaryType> pageInfo = PageHelper.offsetPage(pageParam.getOffset(), pageParam.getPageSize())
                .setOrderBy(orderBy)
                .doSelectPageInfo(
                        new ISelect() {
                            @Override
                            public void doSelect() {
                                tDictionaryTypeDAO.selectByQueryParam();
                            }
                        }
                );
        return PageInfoUtil.convert(pageInfo, DictionaryTypeResp.class);
    }
}
