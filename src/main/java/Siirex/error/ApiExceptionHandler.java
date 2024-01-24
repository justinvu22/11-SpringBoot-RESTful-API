package Siirex.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Xử lý vấn đề nếu Client request lên một id không hề tồn tại?
 * */

/** @RestControllerAdvice
- Là một Annotation gắn trên Class
- Có tác dụng xen vào quá trình xử lý của các @RestController
- Tương tự với @ControllerAdvice
- Nó thường được kết hợp với @ExceptionHandler để cắt ngang quá trình xử lý của Controller, 
và xử lý các ngoại lệ xảy ra.

	@RestControllerAdvice
	public class ApiExceptionHandler {
	
	    @ExceptionHandler(IndexOutOfBoundsException.class)
	    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	    public ErrorMessage TodoException(Exception ex,  WebRequest request) {
	        return new ErrorMessage(10100, "Đối tượng không tồn tại");
	    }
	}

- Hiểu đơn giản là @Controller đang hoạt động bình thường, chẳng may có một Exception được 
ném ra, thì thay vì báo lỗi hệ thống, thì nó sẽ được @RestControllerAdvice và @ExceptionHandler 
bắt lấy và được xử lý (handling). Sau đó trả về cho Client thông tin hữu ích hơn.

*/

/** @ResponseStatus
- Là một cách định nghĩa Http Status trả về cho người dùng.
- Nếu không muốn sử dụng 'ResponseEntity' thì có thể dùng @ResponseStatus đánh dấu trên 
Object trả về.
*/

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây!
     */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleAllException(Exception ex, WebRequest request) {
		
		// Quá trình kiểm soát lỗi diễn ra ở đây!
		return new ErrorMessage(10000, ex.getLocalizedMessage());
	}
	
    /**
     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây!
     */
	@ExceptionHandler(IndexOutOfBoundsException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage TodoException(Exception ex, WebRequest request) {
		return new ErrorMessage(10100, "Đối tượng không tồn tại!");
	}
}
