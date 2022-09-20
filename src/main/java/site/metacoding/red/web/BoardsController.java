package site.metacoding.red.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.loves.Loves;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.service.BoardsService;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.CMRespDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {

	private final HttpSession session;
	private final BoardsService boardsService;

	
	/***
	 * 
	 *     인증과 권한 체크는 지금 하지 마세요!!
	 */
	
	// 인증 필요: 로그인을 했는지
	// 어떤 게시글을 누가 좋아하는지 (boardsId, usersId)
	@PostMapping("/s/api/boards/{id}/loves")
	public @ResponseBody CMRespDto<?> insertLoves(@PathVariable Integer id){
		Users principal = (Users) session.getAttribute("principal");
		Loves loves = new Loves(principal.getId(), id);
		boardsService.좋아요(loves);
		return new CMRespDto<>(1, "좋아요 성공", loves);
	}
	
	// 인증 필요
	@DeleteMapping("/s/api/boards/{id}/loves/{lovesId}")
	public @ResponseBody CMRespDto<?> deleteLoves(@PathVariable Integer id, @PathVariable Integer lovesId){
		boardsService.좋아요취소(lovesId);
		return new CMRespDto<>(1, "좋아요 취소 성공", null);
	}
	
	// 인증 필요
	@PutMapping("/s/api/boards/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable Integer id, @RequestBody UpdateDto updateDto) {
		boardsService.게시글수정하기(id, updateDto);
		return new CMRespDto<>(1, "글수정성공", null);
	}

	// 인증 필요
	@GetMapping("/s/boards/{id}/updateForm")
	public String updateForm(@PathVariable Integer id, Model model) {
		Boards boardsPS = boardsService.게시글수정화면데이터가져오기(id);
		model.addAttribute("boards", boardsPS);
		return "boards/updateForm";
	}

	// 인증 필요
	@DeleteMapping("/s/api/boards/{id}")
	public @ResponseBody CMRespDto<?> deleteBoards(@PathVariable Integer id) {
		boardsService.게시글삭제하기(id);
		return new CMRespDto<>(1, "게시글삭제", null);
	}

	// 인증 필요
	@PostMapping("/s/api/boards")
	public @ResponseBody CMRespDto<?> writeBoards(@RequestBody WriteDto writeDto) {
		Users principal = (Users) session.getAttribute("principal");
		boardsService.게시글쓰기(writeDto, principal);
		return new CMRespDto<>(1, "글쓰기성공", null);
	}

	@GetMapping({ "/", "/boards" })
	public String getBoardList(Model model, Integer page, String keyword) { // 0 -> 0, 1->10, 2->20
		PagingDto pagingDto = boardsService.게시글목록보기(page, keyword);
		model.addAttribute("pagingDto", pagingDto);
		
		Map<String, Object> referer = new HashMap<>();
		referer.put("page", pagingDto.getCurrentPage());
		referer.put("keyword", pagingDto.getKeyword());
		session.setAttribute("referer", referer);
		return "boards/main";
	}

	@GetMapping("/boards/{id}")
	public String getBoardDetail(@PathVariable Integer id, Model model) {
		Users principal = (Users) session.getAttribute("principal");
		if(principal == null) {
			model.addAttribute("detailDto", boardsService.게시글상세보기(id, 0));
		}else {
			model.addAttribute("detailDto", boardsService.게시글상세보기(id, principal.getId()));
		}
		
		return "boards/detail";
	}

	@GetMapping("/s/boards/writeForm")
	public String writeForm() {
		return "boards/writeForm";
	}
}









