package com.choa.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.choa.util.DBConnector;
import com.choa.util.RowMaker;
@Repository
//NoticeDAO noticeDAO = new NoticeDAO
public class NoticeDAO {


	//만들어진 쏘스를 주입시켜줘라 = inject
	@Inject
	private DataSource dataSource; //데이터 주입 시켜야함.

	//Inject 를 하면 setter 필요없음
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//	}

	
			public int hitup(int num) throws Exception{
				Connection con = dataSource.getConnection();
				
				PreparedStatement st= null;
				int result = 0;
				String sql = "update notice set hit = hit+1 where num =?";
				st= con.prepareStatement(sql);
				st.setInt(1, num);
				result = st.executeUpdate();
				
				return result;
			}


	public int getTotalCount() throws Exception{
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		ResultSet rs =  null;
		int totalCount = 0;
		
		String sql = "select nvl(count(num),0) from notice";
		st= con.prepareStatement(sql);
		rs = st.executeQuery();
		rs.next();
		totalCount = rs.getInt(1);
		
		DBConnector.disconnect(st, con, rs);
		
		return totalCount;
		
	}
	
	
	
	// view

	public NoticeDTO noticeView(int num) throws Exception {

		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		NoticeDTO noticeDTO = null;
		String sql = "select * from notice where num =?";

		st = con.prepareStatement(sql);
		st.setInt(1, num);
		rs = st.executeQuery();
		if (rs.next()) {
			noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setContents(rs.getString("contents"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
		}
		// close
		rs.close();
		st.close();
		con.close();
		return noticeDTO;
	}

	// List

	public List<NoticeDTO> noticeList(RowMaker rowMaker) throws Exception {

		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		NoticeDTO noticeDTO = null;
		List<NoticeDTO> ar = new ArrayList<NoticeDTO>();
		String sql = "select * from (select rownum R,N.* from (select * from notice order by num desc) N) where R between ? and ?";
		st = con.prepareStatement(sql);
		st.setInt(1, rowMaker.getStartRow());
		st.setInt(2, rowMaker.getLastRow());
		
		rs = st.executeQuery();
		while (rs.next()) {
			noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setWriter(rs.getString("writer"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setContents(rs.getString("contents"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
			ar.add(noticeDTO);

		}
		// close
		DBConnector.disconnect(st, con, rs);
		return ar;

	}

	public int noticeWrite(NoticeDTO noticeDTO) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		int result = 0;

		String sql = "insert into notice values(notice_seq.nextVal,?,?,?,?,?)";
		st = con.prepareStatement(sql);
		st.setString(1, noticeDTO.getWriter());
		st.setString(2, noticeDTO.getTitle());
		st.setString(3, noticeDTO.getContents());
		st.setDate(4, noticeDTO.getReg_date());
		st.setInt(5, noticeDTO.getHit());

		result = st.executeUpdate();

		DBConnector.disconnect(st, con);
		return result;

	}

	public int noticeUpdate(NoticeDTO noticeDTO) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		int result = 0;

		String sql = "update notice set title =? ,contents=? where num =?";
		st = con.prepareStatement(sql);
		st.setString(1, noticeDTO.getTitle());
		st.setString(2, noticeDTO.getContents());
		st.setInt(3, noticeDTO.getNum());
		result = st.executeUpdate();
DBConnector.disconnect(st,con);
		return result;

	}

	public int noticeDelete(int num) throws Exception {
		Connection con = dataSource.getConnection();
		PreparedStatement st = null;
		int result = 0;

		String sql = "delete notice where num =?";
		st = con.prepareStatement(sql);
		st.setInt(1, num);
		result = st.executeUpdate();

		st.close();
		con.close();
		return result;

	}

}
