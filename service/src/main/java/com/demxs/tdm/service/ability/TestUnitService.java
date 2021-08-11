package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestUnitDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.ability.TestUnit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 试验项Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestUnitService extends CrudService<TestUnitDao, TestUnit> {
	@Resource
	private AptitudeService aptitudeService;
	@Override
	public TestUnit get(String id) {
		TestUnit testUnit = dao.get(id);
//		setDetailInfo(testUnit);
		return testUnit;
	}

	private void setDetailInfo(TestUnit testUnit) {
		String deviceCredentials = testUnit.getDeviceCredentials();
		if(StringUtils.isNotEmpty(deviceCredentials)){
			for (String dc : deviceCredentials.split(",")) {
				testUnit.addDeviceCredentialsList(aptitudeService.get(dc));
			}
		}
	}

	@Override
	public List<TestUnit> findList(TestUnit testUnit) {
		return super.findList(testUnit);
	}

	/**
	 * 根据设备资质 查找试验项
	 * @param dcIds
	 * @return
	 */
	public List<TestUnit> findByDeviceCredentilas(List<Aptitude> dcIds){
		if (dcIds == null) {
			return new ArrayList<>();
		}
		List<TestUnit> list = new ArrayList<>();
		TestUnit filter = new TestUnit();
		for (Aptitude dcId : dcIds) {
			filter.setDeviceCredentials(dcId.getId());
			list.addAll(this.findList(filter));
		}
		return list;
	}

	@Override
	public Page<TestUnit> findPage(Page<TestUnit> page, TestUnit testUnit) {
		Page<TestUnit> testUnitPage = super.findPage(page, testUnit);
//		for(TestUnit tu : testUnitPage.getList()){
//			setDetailInfo(tu);
//		}
		return testUnitPage;
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestUnit testUnit) {
		super.save(testUnit);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestUnit testUnit) {
		super.delete(testUnit);
	}
	
}