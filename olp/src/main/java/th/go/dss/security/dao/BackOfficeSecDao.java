package th.go.dss.security.dao;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public interface BackOfficeSecDao {
	List<GrantedAuthority> getGrantedAuthority(Authentication auth);
}
