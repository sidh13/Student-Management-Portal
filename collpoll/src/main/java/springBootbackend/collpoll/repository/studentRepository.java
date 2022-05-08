package springBootbackend.collpoll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import springBootbackend.collpoll.model.Student;


@Repository
public interface studentRepository extends JpaRepository<Student, Long>{
	 boolean existsByPhoneno(String phoneno);
	 boolean existsByEmailid(String emailid);

}
