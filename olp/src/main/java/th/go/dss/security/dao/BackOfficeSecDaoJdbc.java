package th.go.dss.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;

import th.go.dss.olp.dao.OlpDaoJdbc;

public class BackOfficeSecDaoJdbc implements BackOfficeSecDao {

	private static final Logger logger = LoggerFactory.getLogger(OlpDaoJdbc.class);

	
	private JdbcTemplate jdbcTemplate;
	
	public void setDataSource(DataSource ds) {
		this.jdbcTemplate = new JdbcTemplate(ds);
	}
	
	private RowMapper<GrantedAuthority> authorityRowMapper = new RowMapper<GrantedAuthority>() {
		public GrantedAuthority mapRow(ResultSet rs, int rowNum)
				throws SQLException {
				
				
			return new GrantedAuthorityImpl("ROLE_" + rs.getString(1));
		}
	};

	
	@Override
	public List<GrantedAuthority> getGrantedAuthority(Authentication auth) {
		// TODO Auto-generated method stub
		String sql = "" +
				"select g.group_code from s_user s, s_group_list gl, s_group g " +
				"where  s.id = gl.s_user_id and g.id = gl.s_group_id and " +
				"	s.login like UPPER(?) and s.password like ? ";
	
		List<GrantedAuthority> returnList = this.jdbcTemplate.query(
				sql,
				authorityRowMapper,
				auth.getName(),
				auth.getCredentials()
				);

		if(logger.isDebugEnabled()) {
			logger.debug("user attempt login : " + auth.getName());
			for(GrantedAuthority grantedAuth : returnList) {
				logger.debug("Granted : " + grantedAuth.getAuthority());
			}
		}
		
		return returnList;
	}

}
