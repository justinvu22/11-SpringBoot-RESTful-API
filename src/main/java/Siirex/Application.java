package Siirex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* Create API no Database */

/** Restful API trong Spring Boot?
- Hiện tại xu hướng hiện nay là sẽ để các frontend framework take care nhiều việc hơn, còn 
phía server chỉ nên cung cấp API cho frontend framework là đủ.
- Spring boot thì lại quá mạnh cho việc tạo Restful API !!!
*/

/** @RestController
- @Controller sẽ trả về một template (view). @RestController trả về dữ liệu dưới dạng JSON.
- Trong @RestController, các đối tượng trả về dưới dạng Object sẽ được Spring Boot chuyển 
thành JSON.

	@RestController
	@RequestMapping("/api/v1")
	public class RestApiController{
	
	    @GetMapping("/todo")
	    public List<Todo> getTodoList() {
	        return todoList;
	    }
	}

- Các đối tượng trả về rất đa dạng, có thể trả về List, Map, v.v.. Spring Boot sẽ convert hết 
chúng thành JSON, mặc định sẽ dùng Jackson converter để làm điều đó.
- Nếu muốn API tùy biến được kiểu dữ liệu trả về, có thể trả về đối tượng 'ResponseEntity' 
của Spring cung cấp. Đây là đối tượng cha của mọi response và sẽ wrapper các object trả về.
*/

/** @RequestBody
- Vì bây giờ chúng ta xây dựng API, nên các thông tin từ phía Client gửi lên Server sẽ nằm 
trong Body, và cũng dưới dạng JSON luôn.

	@RestController
	@RequestMapping("/api/v1")
	public class RestApiController {
	
	    List<Todo> todoList = new CopyOnWriteArrayList<>();
	
	    @PostMapping("/todo")
	    public ResponseEntity addTodo(@RequestBody Todo todo) {
	        
	        todoList.add(todo);
	        
	         * Trả về response với STATUS CODE = 200 (OK)
	         * Body sẽ chứa thông tin về đối tượng todo vừa được tạo.
	         * 
	        return ResponseEntity.ok().body(todo);
	    }
	}

- Tất nhiên là Spring Boot sẽ làm giúp chúng ta các phần nặng nhọc, nó chuyển chuỗi JSON 
trong request thành một Object Java. Chỉ cần cho nó biết cần chuyển JSON thành Object nào bằng 
Annotation @RequestBody.
*/

/** @PathVariable
- RESTful API là một tiêu chuẩn dùng trong việc thết kế các thiết kế API cho các ứng dụng web 
để quản lý các resource.
- Và với cách thống nhất này, thì sẽ có một phần thông tin quan trọng sẽ nằm ngay trong chính 
URL của api.
	- URL tạo Todo: https://loda.me/todo. Tương ứng với HTTP method là POST
	- URL lấy thông tin Todo số 12: https://loda.me/todo/12. Tương ứng với HTTP method là GET
	- URL sửa thông tin Todo số 12: https://loda.me/todo/12. Tương ứng với HTTP method là PUT
	- URL xoá Todo số 12: https://loda.me/todo/12. Tương ứng với HTTP method là DELETE
- Ngoài thông tin trong Body của request @RequestBody, thì cái chúng ta cần chính là cái con 
số 12 nằm trong URL. Phải lấy được con số đó thì mới biết được đối tượng Todo cần thao tác là gì.
- Lúc đó cần sử dụng @PathVariable :

	@RestController
	@RequestMapping("/api/v1")
	public class RestApiController {
	
	    @GetMapping("/todo/{todoId}")	    
	    public Todo getTodo(@PathVariable(name = "todoId") Integer todoId){
	        // @PathVariable lấy ra thông tin trong URL
	        // dựa vào tên của thuộc tính đã định nghĩa trong ngoặc kép /todo/{todoId}
	        return todoList.get(todoId);
	    }
	}
*/

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
}
