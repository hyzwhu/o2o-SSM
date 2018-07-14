package com.hyz.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyz.o2o.BaseTest;
import com.hyz.o2o.entity.Area;
import com.hyz.o2o.web.superadmin.AreaController;

import ch.qos.logback.classic.Logger;

public class AreaDaoTest extends BaseTest {
	@Autowired
	private AreaDao areaDao;

//	@Test
//	public void testQueryArea() {
//		List<Area> areaList = areaDao.queryArea();
//		assertEquals(2, areaList.size());
//		
//	}

	@Test
	public void tesstInsertArea() {
		Logger logger = (Logger) LoggerFactory.getLogger(AreaController.class);
		Area area = new Area();
		area.setAreaName("南苑");
		area.setCreateTime(new Date());
		area.setPriority(1);
		try{int TestValue = areaDao.insertArea(area);
		assertEquals(1, TestValue);
		}catch(Exception e)
		{	
			logger.debug(e.toString());
		}
	}
}
