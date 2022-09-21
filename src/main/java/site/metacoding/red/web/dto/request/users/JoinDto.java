package site.metacoding.red.web.dto.request.users;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import site.metacoding.red.domain.users.Users;

@Setter
@Getter
public class JoinDto {
	@Size(min = 2, max = 20, message = "username 길이가 부적합합니다.")
	@NotBlank(message = "username이 null이거나 공백일 수 없습니다.") // null과 공백 검사
	private String username;

	@Size(min = 2, max = 20, message = "password의 길이가 부적합합니다.")
	@NotBlank(message = "password가 null이거나 공백일 수 없습니다.")
	private String password;

	@Size(min = 4, max = 50, message = "email의 길이가 부적합합니다.")
	@NotBlank(message = "email이 null이거나 공백일 수 없습니다.")
	private String email;

	public Users toEntity() {
		Users users = new Users(this.username, this.password, this.email);
		return users;
	}
}
