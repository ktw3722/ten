package spring.model.ktx_booked;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.model.ten.DAOMyBatisInter;

@Component
public class Ktx_BookedDAO implements DAOMyBatisInter {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void setMybatis(SqlSessionTemplate mybatis) {
		this.mybatis = mybatis;
	}

	@Override
	public int create(Object dto) throws Exception {
		// TODO Auto-generated method stub
		return mybatis.insert("ktx_booked.create", dto);
	}

	@Override
	public List list(Map map) throws Exception {
		// TODO Auto-generated method stub
		return mybatis.selectList("ktx_booked.list",map);
	}

	@Override
	public Object read(Object pk) throws Exception {
		// TODO Auto-generated method stub
		return mybatis.selectOne("ktx_booked.read", pk);
	}

	@Override
	public int update(Object dto) throws Exception {
		// TODO Auto-generated method stub
		return mybatis.update("ktx_booked.update", dto);
	}

	@Override
	public int delete(Object pk) throws Exception {
		// TODO Auto-generated method stub
		return mybatis.delete("ktx_booked.delete", pk);
	}

	@Override
	public int total(Map map) throws Exception {
		// TODO Auto-generated method stub
		return mybatis.selectOne("ktx_booked.total", map);
	}

}
