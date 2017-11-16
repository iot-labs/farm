package net.iotlabs.db;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisManager {
	
	private static final SqlSessionFactory sqlMapper;
	static {
		try {
			String resource = "net/iotlabs/db/Configuration.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	private MyBatisManager() {
	}
	/**
	 * iBatis에서 사용할 sqlMap 정보를 담고 있다.
	 * 자신을 리턴하지 않고 sqlMap을 리턴, 변형된 싱글턴 패턴
	 * @return iBatis에서 사용할 sqlMap 정보를 담고 있다.
	 */
	public static SqlSessionFactory getSqlMapper() {
		return sqlMapper; // 자신을 리턴하지 않고 sqlMap을 리턴, 변형된 싱글턴 패턴
	}
}
