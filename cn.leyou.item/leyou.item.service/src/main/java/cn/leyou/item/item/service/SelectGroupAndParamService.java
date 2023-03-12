package cn.leyou.item.item.service;

import cn.leyou.item.mapper.HSelectGroupMapper;
import cn.leyou.item.mapper.HSelectParamMapper;
import cn.leyou.item.pojo.SpecGroup;
import cn.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SelectGroupAndParamService{
    @Autowired
    private HSelectGroupMapper hSelectGroupMapper;
    @Autowired
    private HSelectParamMapper hSelectParamMapper;

    /**
     *根据条件查询spu规格参数
     * @param cid
     * @return
     */
    public List<SpecGroup> queryGroup(Long cid) {
        SpecGroup group=new SpecGroup();
        group.setCid(cid);
        return this.hSelectGroupMapper.select(group);
    }

    /**
     * 通过id来查询指定的商品信息
     * @param gid
     * @return
     */
    public List<SpecParam> queryParam(Long cid,Long gid,Boolean searching,Boolean generic) {
        SpecParam param=new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setGeneric(generic);
        param.setSearching(searching);
        List<SpecParam> params=hSelectParamMapper.select(param);
        return params;
    }

    /**
     * 组的新增||修改分组
     * @param cid
     * @param name
     * @param id
     */
    @Transactional
    public void addGroup(Long cid, String name,Long id) {
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        group.setName(name);
        group.setId(id);
        if (id==null){//id大于零表示修改数据
            hSelectGroupMapper.insertSelective(group);
        }else {
            hSelectGroupMapper.updateByPrimaryKeySelective(group);
        }
//        hSelectGroupMapper.insertSelective();
// 这个方法会将空的字符串不写入查询语句，但是前者会且将值设置为空
    }

    /**
     * 组的删除
     * @param id
     */
    @Transactional
    public void deleteGroup(Long id) {
        hSelectGroupMapper.deleteByPrimaryKey(id);
    }


}
