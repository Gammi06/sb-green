package site.metacoding.red.domain.boards;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.red.web.dto.request.boards.UpdateDto;

@NoArgsConstructor
@Setter
@Getter
public class Boards {
	private Integer id;
	private String title;
	private String content;
	private Integer usersId;
	private Timestamp createdAt; // At 시분초 다 표현할때, Dt 년원일
	
	public Boards(String title, String content, Integer usersId) {
		this.title = title;
		this.content = content;
		this.usersId = usersId;
	}
	
	public void update(UpdateDto updateDto) {
		this.title = updateDto.getTitle();
		this.content = updateDto.getContent();
	}
}
