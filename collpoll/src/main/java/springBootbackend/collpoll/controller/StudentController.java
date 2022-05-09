package springBootbackend.collpoll.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springBootbackend.collpoll.repository.studentRepository;
import springBootbackend.collpoll.exception.ExceptionFound;
import springBootbackend.collpoll.model.Student;

@RestController
@RequestMapping("/api/v1/")
public class StudentController {

	@Autowired
	private studentRepository studentRepository;
	
	//get all students details;
	
	@GetMapping("/students")
	public List<Student> getAllStudents (){
	
		return studentRepository.findAll();
	}
	
	
	//create student rest api
	
	@PostMapping("/newstudent")
	public ResponseEntity createEmployee(@RequestBody Student student)
	{
		//check if the phone no is valid or not
		if((student.getPhoneno()).length()!=10)
		{
			new ExceptionFound("please enter a valid mobile number");
			return new ResponseEntity("please enter a valid mobile number",HttpStatus.BAD_REQUEST);
			
		}
		//check if the phone no already exists
		if(studentRepository.existsByPhoneno(student.getPhoneno()))
		{
			new ExceptionFound("the phone nuber already exists for a student");
			return new ResponseEntity("the phone nuber already exists for a student",HttpStatus.BAD_REQUEST);
			
		}
		//check if the e-mail already exists
		if(studentRepository.existsByEmailid(student.getEmailid()))
		{
			new ExceptionFound("the e-mail already exists for a student");
			return new ResponseEntity("the e-mail already exists for a student",HttpStatus.BAD_REQUEST);
			
		}
	
		 studentRepository.save(student); //store student details
		
		 return new ResponseEntity("Student Details added successfully",HttpStatus.CREATED);
	}
	
	
	// get student by id  rest API
	@GetMapping("/student/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Long id)
	{
		Student student = studentRepository.findById(id)
				.orElseThrow(()-> new ExceptionFound("employee does not exist with given id:" + id));
		
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	//update the students details rest API
	
	@PutMapping("/student/{id}")
	public ResponseEntity<Student> updateStudentDetails(@PathVariable Long id,@RequestBody Student studentDetails)
	{
		if((studentDetails.getPhoneno()).length()!=10)
		{
			new ExceptionFound("please enter a valid mobile number");
			return new ResponseEntity("please enter a valid mobile number",HttpStatus.BAD_REQUEST);
			
		}
		
		Student updateStudentDetails = studentRepository.findById(id)
				.orElseThrow(()-> new ExceptionFound("employee does not exist with given id:" + id));
		
		updateStudentDetails.setFirstname(studentDetails.getFirstname());
		updateStudentDetails.setLastname(studentDetails.getLastname());
		updateStudentDetails.setEmailid(studentDetails.getEmailid());
		updateStudentDetails.setPhoneno(studentDetails.getPhoneno());
		
		Student updatedStudent = studentRepository.save(updateStudentDetails);
		
		return ResponseEntity.ok(updatedStudent);
	}
	
	//delete details of a student
	@DeleteMapping("/student/{id}")
	public ResponseEntity<Map<String,Boolean>> deleteStudent(@PathVariable long id)
	{
		Student student = studentRepository.findById(id)
				.orElseThrow(()-> new ExceptionFound("employee does not exist with given id:" + id));
		
		studentRepository.delete(student);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted",Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
