package com.demxs.tdm.service.resource.biaozhunwuzhi;

import com.demxs.tdm.dao.resource.biaozhunwuzhi.BiaozhunwzCfwzDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzCfwz;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzCfwzTree;
import com.demxs.tdm.comac.common.constant.BiaozhunwzConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准物质存放位置Service
 * @author zhangdengcai
 * @version 2017-06-17
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BiaozhunwzCfwzService extends CrudService<BiaozhunwzCfwzDao, BiaozhunwzCfwz> {
    @Autowired
    private BiaozhunwzCfwzDao biaozhunwzCfwzDao;

    @Override
    public BiaozhunwzCfwz get(String id) {
        return super.get(id);
    }

    @Override
    public List<BiaozhunwzCfwz> findList(BiaozhunwzCfwz biaozhunwzCfwz) {
        return super.findList(biaozhunwzCfwz);
    }

    @Override
    public Page<BiaozhunwzCfwz> findPage(Page<BiaozhunwzCfwz> page, BiaozhunwzCfwz biaozhunwzCfwz) {
        return super.findPage(page, biaozhunwzCfwz);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(BiaozhunwzCfwz biaozhunwzCfwz) {
        //存放位置改为无效时，更改所有后代位置为“无效”
        if (StringUtils.isNotBlank(biaozhunwzCfwz.getId()) && (BiaozhunwzConstans.youxiaox.WUXIAO).equals(biaozhunwzCfwz.getYouxiaox())) {
            getCfwzSpring(biaozhunwzCfwz.getId());//获取所有后代位置id
            if(cfwzIdList!=null && !cfwzIdList.isEmpty()){
                BiaozhunwzCfwz cfwz = new BiaozhunwzCfwz();
                cfwz.setArrIDS(cfwzIdList.toArray(new String[cfwzIdList.size()]));
                List<BiaozhunwzCfwz> cfwzs = this.dao.findList(cfwz);
                if(cfwzs!=null && !cfwzs.isEmpty()){
                    for (BiaozhunwzCfwz wz : cfwzs) {
                        wz.setYouxiaox(BiaozhunwzConstans.youxiaox.WUXIAO);
                        super.save(wz);
                    }
                }
            }
        }
        super.save(biaozhunwzCfwz);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void delete(BiaozhunwzCfwz biaozhunwzCfwz) {
        super.delete(biaozhunwzCfwz);
    }

    /**
     * 批量删除
     * @param ids
     */
    public void deleteMore(String ids){
        String[] idArray = null;
        if(StringUtils.isNotBlank(ids)){
            if(ids.contains(",")){
                idArray = ids.split(",");
            }else{
                idArray = new String[1];
                idArray[0] = ids;
            }
        }
        BiaozhunwzCfwz biaozhunwzCfwz = new BiaozhunwzCfwz();
        biaozhunwzCfwz.setArrIDS(idArray);
        biaozhunwzCfwzDao.deleteMore(biaozhunwzCfwz);
    }

    public Page<BiaozhunwzCfwz> getChildren(Page<BiaozhunwzCfwz> page, BiaozhunwzCfwz biaozhunwzCfwz){
        biaozhunwzCfwz.setPage(page);
//        if(UserUtils.getUser()!=null){
//            biaozhunwzCfwz.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//        }
        List<BiaozhunwzCfwz> values = this.dao.getChildren(biaozhunwzCfwz);
        page.setList(values);
        return page;
    }

    /**
     * 获取标准物质存放位置树
     * @param rootId 根id
     * @param rootMc 根名称
     * @return
     */
    public BiaozhunwzCfwzTree getTree(String rootId, String rootMc){
        BiaozhunwzCfwzTree node  = new BiaozhunwzCfwzTree(rootId, "", rootMc);
        return createTree(node);
    }


    /**
     * 拼装标准物质存放位置树
     * @param node
     * @return
     */
    public BiaozhunwzCfwzTree createTree(BiaozhunwzCfwzTree node) {
        List<BiaozhunwzCfwzTree> childTreeNodes = new ArrayList<BiaozhunwzCfwzTree>();

        List<BiaozhunwzCfwz> BiaozhunwzCfwzs = findList(new BiaozhunwzCfwz());
        if(BiaozhunwzCfwzs!=null && BiaozhunwzCfwzs.size()>0) {
            //查询节点对象下的所有子节点
            for (BiaozhunwzCfwz biaozhunwzCfwz : BiaozhunwzCfwzs) {
                if ((node.getNodeValue()).equals(biaozhunwzCfwz.getFuzhuj()) && (BiaozhunwzConstans.youxiaox.YOUXIAO).equals(biaozhunwzCfwz.getYouxiaox())) {
                    BiaozhunwzCfwzTree cNode = new BiaozhunwzCfwzTree(biaozhunwzCfwz.getId(), biaozhunwzCfwz.getFuzhuj(), biaozhunwzCfwz.getWeizhimc());
                    childTreeNodes.add(cNode);
                }
            }

            //遍历子节点
            for (BiaozhunwzCfwzTree child: childTreeNodes) {
                BiaozhunwzCfwzTree n = createTree(child); //递归
                node.getChildren().add(n);
            }
        }

        return node;
    }

    /**
     * 存放位置全名称
     */
    String cunfangddmc = "";

    /**
     * 递归拼接
     * @param cfwzId
     * @return
     */
    public String cfdd(String cfwzId){
        BiaozhunwzCfwz biaozhunwzCfwz = get(cfwzId);
        if(biaozhunwzCfwz!=null){
            if(!"".equals(cunfangddmc)){
                cunfangddmc += "-" + biaozhunwzCfwz.getWeizhimc();
            }else{
                cunfangddmc += biaozhunwzCfwz.getWeizhimc();
            }

            if(StringUtils.isNotBlank(biaozhunwzCfwz.getFuzhuj()) && !"0".equals(biaozhunwzCfwz.getFuzhuj())){
                cfdd(biaozhunwzCfwz.getFuzhuj());//递归
            }
        }
        return cunfangddmc;
    }


    /**
     * 获取存放位置全名称
     * 结果处理
     * @param cunfangwzId
     * @return
     */
    public String cunfangwzmc(String cunfangwzId){
        String cfwzmc = cfdd(cunfangwzId);
        //逆序
        if(StringUtils.isNotBlank(cfwzmc) && cfwzmc.contains("-")){
            String[] mcs = cfwzmc.split("-");
            String mc = "";
            for(int i=mcs.length-1; i>=0; i--){
                if(!"".equals(mc)){
                    mc += "-" + mcs[i];
                }else{
                    mc += mcs[i];
                }
            }
            cfwzmc = mc;
        }

        cunfangddmc = "";//清空全局变量
        return cfwzmc;
    }

    //设备类型后代ID集合
    List<String> cfwzIdList = new ArrayList<String>();

    /**
     * 创建标准物质存放位置后代ID集合
     * @param cfwzId 存放位置ID
     */
    public void getCfwzSpring(String cfwzId){
        if(StringUtils.isNotBlank(cfwzId)){
            BiaozhunwzCfwz cfwz = new BiaozhunwzCfwz();
            cfwz.setFuzhuj(cfwzId);
            cfwz.setArrIDS(null);
            List<BiaozhunwzCfwz> cfwzs = this.dao.findList(cfwz);

            if(cfwzs!=null && cfwzs.size()>0){
                for (BiaozhunwzCfwz wz : cfwzs) {
                    if(StringUtils.isNotBlank(wz.getFuzhuj())){
                        if(cfwzId.equals(wz.getFuzhuj())){
                            cfwzIdList.add(wz.getId());
                            getCfwzSpring(wz.getId());
                        }
                    }
                }
            }
        }
    }
}