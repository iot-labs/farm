package net.iotlabs.db;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;

public class CommonDao {
	
    private Gson gson = new Gson();
    
    public static void main(String[] args) {
    	CommonDao dao = new CommonDao();
    	
    	String query = "MainMapper.test1";
    	HashMap p = new HashMap();
    	p.put("resultSeq", "19325");
    	
    	
    	HashMap<String, String> result = dao.commonSelectOne(query, p);
    	System.out.println(result.get("workerName"));
    }
	
	public HashMap<String, String> commonSelectOne(String query, HashMap p) {
		
		HashMap result = null;
		SqlSession session = MyBatisManager.getSqlMapper().openSession();
        try {
        	result = (HashMap<String, String>)session.selectOne(query, p);
        } finally {
        	session.close();
        }
        return result;
	}
	
	public HashMap<String, Object> commonSelectOneObject(String query, HashMap p) {
		
		HashMap result = null;
		SqlSession session = MyBatisManager.getSqlMapper().openSession();
        try {
        	result = (HashMap<String, Object>)session.selectOne(query, p);
        } finally {
        	session.close();
        }
        return result;
	}
	
	public ArrayList<HashMap> commonSelectList(String query, HashMap p) {
        ArrayList<HashMap> list = new ArrayList();
        SqlSession session = MyBatisManager.getSqlMapper().openSession();
        try {
        	list = (ArrayList<HashMap>) session.selectList(query, p);
        } finally {
        	session.close();
        }
        return list;
	}
	
	public int commonInsert(String query, HashMap p) {
		
        int result = 0;
        SqlSession session = MyBatisManager.getSqlMapper().openSession();
        try {
        	result = session.insert(query, p);
        	if (result == 1) { 
        		session.commit();
        	} else {
        		session.rollback();
        	}
        } finally {
        	session.close();
        }
        return result;
	}
	
	
	public int commonUpdate( String query, HashMap p){
		
        int result = 0;
        SqlSession session = MyBatisManager.getSqlMapper().openSession();
        try {
        	result = session.update(query, p);
        	System.out.println("##### update result : " + result);
        	if (result == 1) { 
        		session.commit();
        	} else {
        		session.rollback();
        		System.out.println("##### update ROLLBACK !!!! : " + result);
        	}
        } finally {
        	session.close();
        }
        return result;
	}
	
	public int commonDelete( String query, HashMap p){
		
		System.out.println(" ###### CommonDao.commonDelete ###### ");
		
        int count = 0;
        SqlSession session = MyBatisManager.getSqlMapper().openSession();
        try {
        	count = session.delete(query, p);
        	session.commit();
        } finally {
        	session.close();
        }
        
        return count;
	}
}
