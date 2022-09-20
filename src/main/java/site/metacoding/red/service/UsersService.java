package site.metacoding.red.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor
@Service
public class UsersService {
	
	private final UsersDao usersDao;
	private final BoardsDao boardsDao;

	public void 회원가입(JoinDto joinDto) {
		Users users = joinDto.toEntity();
		usersDao.insert(users);
	}
	
	public Users 로그인(LoginDto loginDto) {
		Users usersPS = usersDao.findByUsername(loginDto.getUsername());

		if(usersPS == null) {
			return null;
		}
		
		if(usersPS.getPassword().equals(loginDto.getPassword())) {
			return usersPS;
		}else {
			return null;
		}
	}
	
	public Users 회원수정(Integer id, UpdateDto updateDto) {
		// 1. 영속화
		Users usersPS = usersDao.findById(id);
		
		// 2. 영속화된 객체 변경
		usersPS.update(updateDto);
		
		// 3. 디비 수행
		usersDao.update(usersPS);
		
		return usersPS;
	}
	
	@Transactional(rollbackFor = RuntimeException.class)
	public void 회원탈퇴(Integer id) {
		usersDao.deleteById(id);
		boardsDao.updateByUsersId(id);
	}
	
	public boolean 유저네임중복확인(String username) {
		Users usersPS = usersDao.findByUsername(username);
		
		if(usersPS == null) { // 아이디가 중복 안됨
			return false;
		}else { // 아이디가 중복됨
			return true;
		}
	}
	
	public Users 회원정보보기(Integer id) {
		Users usersPS = usersDao.findById(id);
		return usersPS;
	}
}
