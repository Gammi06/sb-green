package site.metacoding.red.domain.boards;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.red.web.dto.response.boards.DetailDto;
import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

public interface BoardsDao {
	public void insert(Boards boards);
	public List<MainDto> findAll(@Param("startNum") int startNum,@Param("keyword") String keyword, @Param("row") int row);
	public PagingDto paging(@Param("page") Integer page, @Param("keyword") String keyword, @Param("row") int row);
	public Boards findById(Integer id);
	public void update(Boards boards);
	public void deleteById(Integer id);
	public void updateByUsersId(Integer usersId);
	public DetailDto findByDetail(@Param("boardsId") Integer boardsId, @Param("principalId") Integer principalId);
}
